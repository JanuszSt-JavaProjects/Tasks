package com.crud.tasks.trello.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TrelloConfigTest {

    @Autowired
    TrelloConfig trelloConfig;

    @Test
    void getTrelloApiEndpoint() {

        //Given & When
        String trelloApiEndpoint = trelloConfig.getTrelloApiEndpoint();

        //Then
        assertEquals("https://api.trello.com/1", trelloApiEndpoint);
    }

    @Test
    void getTrelloAppKey() {

        //Given & When
        String trelloApiKey = trelloConfig.getTrelloAppKey();

        //Then
        assertEquals("df496064cd8af98df13fcebb940d17d2", trelloApiKey);
    }

    @Test
    void getTrelloToken() {

        //Given & When
        String trelloApiToken = trelloConfig.getTrelloToken();

        //Then
        assertEquals("4af817d360a850c8661cad93f5f51ff56a7c8f81000744180065250d96bc67f4", trelloApiToken);
    }

    @Test
    void getTrelloUser() {

        //Given & When
        String trelloUser = trelloConfig.getTrelloUser();

        //Then
        assertEquals("januszs2", trelloUser);
    }
}