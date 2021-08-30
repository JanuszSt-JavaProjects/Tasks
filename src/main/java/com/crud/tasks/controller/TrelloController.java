package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloService trelloService;


    @PostMapping("createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloService.createTrelloCard(trelloCardDto);
    }


    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloService.fetchTrelloBoards();

 /*
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " - " + trelloBoardDto.getName());
            System.out.println("This board contains lists: ");

            trelloBoardDto.getLists().forEach(trelloList -> {
                System.out.println(
                        trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
            });
        });*/
    }
}
/*
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
        trelloBoards
                .stream().filter(x -> x.getId() != null && x.getName() != null && x.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                });
*/
/*
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
        trelloBoards.stream()
                .filter(x -> Optional.ofNullable(x.getId()).isPresent())
                .filter(x -> Optional.ofNullable(x.getName()).isPresent())   // jeśli z założenia wyszukujemy tylko "Kodillę" to tę linię można pominąć.
                .filter(x -> x.getName().contains("Kodilla"))
                .forEach(x -> System.out.println(
                        x.getId().concat("   ").concat(x.getName()
                )));*/