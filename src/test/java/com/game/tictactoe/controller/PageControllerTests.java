package com.game.tictactoe.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PageControllerTests {
    @InjectMocks
    private PageController pageController;

    @Test
    void testResetGame(){
        String response = pageController.homePage();

        Assertions.assertNotNull(response);
        Assertions.assertEquals("game", response);
    }
}
