document.addEventListener('DOMContentLoaded', () => {
    const boardSizeSelect = document.getElementById('board-size');
    const firstPlayerRadios = document.querySelectorAll('input[name="first-player"]');
    const startGameBtn = document.getElementById('start-game');
    const gameBoard = document.getElementById('game-board');
    const gameInfo = document.getElementById('game-info');
    const resetGameBtn = document.getElementById('reset-game');
    const configurationSection = document.querySelector('.configuration');

    let boardSize = 3;
    let currentPlayer = 'X';

    function validateBoardSize(size){
        const boardSize = parseInt(size);

        if (isNaN(boardSize) || boardSize < 3 || boardSize > 30) {
            alert('Board size must between 3 and 30!');

            boardSizeSelect.value = '3';
            firstPlayerRadios[0].checked = true;

            gameBoard.style.display = 'none';
            resetGameBtn.style.display = 'none';
            gameInfo.textContent = '';

            configurationSection.style.display = 'block';

            return false;
        }
        return true;
    }

    startGameBtn.addEventListener('click', () => {
        const boardSize = parseInt(boardSizeSelect.value);

        if(!validateBoardSize(boardSize)) {
            return;
        }

        currentPlayer = document.querySelector('input[name="first-player"]:checked').value;

        // Reset game via backend with selected board size and first player
        fetch(`/api/game/reset?boardSize=${boardSize}&firstPlayer=${currentPlayer}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                const actualBoardSize = data.boardSize;

                gameBoard.innerHTML = '';
                gameBoard.style.gridTemplateColumns = `repeat(${actualBoardSize}, 1fr)`;

                for (let row = 0; row < actualBoardSize; row++) {
                    for (let col = 0; col < actualBoardSize; col++) {
                        const cell = document.createElement('div');
                        cell.classList.add('cell');
                        cell.dataset.row = row;
                        cell.dataset.col = col;
                        cell.addEventListener('click', handleCellClick);
                        gameBoard.appendChild(cell);
                    }
                }

                configurationSection.style.display = 'none';
                gameBoard.style.display = 'grid';
                resetGameBtn.style.display = 'block';
                gameInfo.textContent = `Current Player: ${data.currentPlayer}`;
            })
            .catch(error => {
                console.error('Error resetting game:', error);
            });
    });

    function handleCellClick(event) {
        const cell = event.target;
        const row = cell.dataset.row;
        const col = cell.dataset.col;

        if (cell.textContent === '') {
            fetch(`/api/game/move?row=${row}&col=${col}`, { method: 'POST' })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        cell.textContent = currentPlayer;
                        currentPlayer = data.currentPlayer;
                        gameInfo.textContent = `Current Player: ${currentPlayer}`;

                        if (data.gameOver) {
                            if (data.winner) {
                                gameInfo.textContent = `Player ${data.winner} wins!`;
                            } else {
                                gameInfo.textContent = 'Draw!';
                            }
                            disableBoard();
                        }
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }

    function disableBoard() {
        const cells = document.querySelectorAll('.cell');
        cells.forEach(cell => {
            cell.style.pointerEvents = 'none';
        });
    }

    resetGameBtn.addEventListener('click', () => {
        gameBoard.innerHTML = '';
        gameBoard.style.display = 'none';
        resetGameBtn.style.display = 'none';
        gameInfo.textContent = '';
        configurationSection.style.display = 'block';
    });
});