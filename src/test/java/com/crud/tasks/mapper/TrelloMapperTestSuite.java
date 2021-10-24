package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper mapper;

    TrelloList trelloList1 = new TrelloList("1", "List1", false);
    TrelloList trelloList2 = new TrelloList("2", "List2", true);

    TrelloCard trelloCard = new TrelloCard("Card1", "Card1Descr", "pos1", "1");
    TrelloBoard trelloBoard1 = new TrelloBoard("500", "BoardName500", List.of(trelloList1));
    TrelloBoard trelloBoard2 = new TrelloBoard("600", "BoardName600", Arrays.asList(trelloList1, trelloList2));


    TrelloListDto trelloListDto1 = new TrelloListDto("1", "trelloList1", false);
    TrelloListDto trelloListDto2 = new TrelloListDto("2", "trelloList2", true);

    TrelloCardDto trelloCardDto = new TrelloCardDto("Card1", "Card1Descr", "pos1", "1");
    TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("100", "trelloBoard1", Arrays.asList(trelloListDto1, trelloListDto2));
    TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("200", "trelloBoard2", Arrays.asList(trelloListDto1, trelloListDto2));


    @Test
    void mapToCard() {
        //Given & When
        TrelloCard trelloCardReceived = mapper.mapToCard(trelloCardDto);

        //Then
        Assertions.assertEquals(trelloCardReceived.getName(), trelloCard.getName());
        Assertions.assertEquals(trelloCardReceived.getListId(), trelloCard.getListId());
        Assertions.assertEquals(trelloCardReceived.getDescription(), trelloCard.getDescription());
        Assertions.assertEquals(trelloCardReceived.getPos(), trelloCard.getPos());
    }

    @Test
    void mapToCardDto() {

        //Given & When
        TrelloCardDto trelloCardDtoReceived = mapper.mapToCardDto(trelloCard);

        //Then
        Assertions.assertEquals(trelloCardDtoReceived.getListId(), trelloCardDto.getListId());
        Assertions.assertNotEquals("2", trelloCardDtoReceived.getListId());

        Assertions.assertEquals(trelloCardDtoReceived.getName(), trelloCardDto.getName());
        Assertions.assertEquals(trelloCardDtoReceived.getDescription(), trelloCardDto.getDescription());
        Assertions.assertEquals(trelloCardDtoReceived.getPos(), trelloCardDto.getPos());
    }

    @Test
    void mapToList() {
        //Given & When
        List<TrelloList> receivedDtoList = mapper.mapToList(Arrays.asList(trelloListDto1, trelloListDto2));

        Set<String> resultSet =
                receivedDtoList.stream()
                        .map(TrelloList::getName)
                        .collect(Collectors.toSet());

        //Then
        Assertions.assertTrue(resultSet.containsAll(Arrays.asList("trelloList1", "trelloList2")));
    }

    @Test
    void mapToListDto() {
        //Given & When
        List<TrelloListDto> receivedListDto = mapper.mapToListDto(Arrays.asList(trelloList1, trelloList2));

        //Then
        Assertions.assertEquals(2, receivedListDto.size());
        Assertions.assertTrue(receivedListDto.stream().anyMatch(x -> x.getName().equals("List1")));
        Assertions.assertTrue(receivedListDto.stream().anyMatch(x -> x.getName().equals("List2")));
        Assertions.assertFalse(receivedListDto.stream().anyMatch(x -> x.getName().equals("List3")));
    }

    @Test
    void mapToBoards() {
        //Given & When
        List<TrelloBoard> receivedBoards = mapper.mapToBoards(Arrays.asList(trelloBoardDto1, trelloBoardDto2));

        //Then
        Assertions.assertNotNull(receivedBoards);
        Assertions.assertEquals(2, receivedBoards.size());

        for (TrelloBoard tB : receivedBoards) {
            Assertions.assertTrue(tB.getId().equals(trelloBoardDto1.getId()) || tB.getId().equals(trelloBoardDto2.getId()));
            Assertions.assertTrue(tB.getName().equals(trelloBoardDto1.getName()) || tB.getName().equals(trelloBoardDto2.getName()));
        }
    }

    @Test
    void mapToBoardsDto() {
        //Given & When
        List<TrelloBoardDto> receivedBoardsDto = mapper.mapToBoardsDto(List.of(trelloBoard1, trelloBoard2));

        //Then
        Assertions.assertEquals(2, receivedBoardsDto.size());
        Assertions.assertTrue(receivedBoardsDto.stream()
                .map(x -> x.getName())
                .collect(Collectors.toSet()).containsAll(List.of("BoardName500", "BoardName600")));

        Assertions.assertFalse(receivedBoardsDto.stream()
                .map(TrelloBoardDto::getName)
                .collect(Collectors.toSet()).contains("BoardName"));
    }
}