package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.terllo.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("getTrelloBoards")
    public void getTrelloBoards() {


        /* Zadanie ppkt -> .3   */

/*
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards
                .stream().filter(x -> x.getId() != null && x.getName() != null && x.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {
                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                });

*/


        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(x -> Optional.ofNullable(x.getId()).isPresent())
                .filter(x -> Optional.ofNullable(x.getName()).isPresent())   // jeśli z założenia wyszukujemy tylko "Kodillę" to tę linię można pominąć.
                .filter(x -> x.getName().contains("Kodilla"))
                .forEach(x -> System.out.println(x.getId().concat("   ").concat(x.getName())));

    }
}