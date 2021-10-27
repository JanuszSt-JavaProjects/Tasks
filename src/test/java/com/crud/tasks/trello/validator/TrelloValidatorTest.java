package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrelloValidatorTest {

    TrelloValidator trelloValidator = new TrelloValidator();


    @Test
    void validateCardLoggerTest1() {

        //Given
        Logger testingLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        testingLogger.addAppender(listAppender);

        TrelloCard trelloCard1 = new TrelloCard("test", "desc", "pos", "1");

        //When
        trelloValidator.validateCard(trelloCard1);
        List<ILoggingEvent> logsList = listAppender.list;

        //Then
        assertTrue(logsList.get(0)
                .getMessage().contains("testing"));

    }

    @Test
    void validateCardLoggerTest2() {

        //Given
        Logger testingLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);

        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        testingLogger.addAppender(listAppender);

        TrelloValidator trelloValidator = new TrelloValidator();
        TrelloCard trelloCard1 = new TrelloCard("AnyName", "desc", "pos", "100");

        //When
        trelloValidator.validateCard(trelloCard1);
        List<ILoggingEvent> logsList = listAppender.list;

        //Then
        assertEquals("Seems that my application is used in proper way.", logsList.get(0)
                .getMessage());

    }

    @Test
    void validateTrelloBoards() {
        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "test", List.of());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "test", List.of());
        TrelloBoard trelloBoard3 = new TrelloBoard("3", "ProperLIstName", List.of());

        //When
        List<TrelloBoard> resultList = trelloValidator.validateTrelloBoards(List.of(trelloBoard1, trelloBoard2, trelloBoard3));

        //Then
        assertEquals(1, resultList.size());
    }
}