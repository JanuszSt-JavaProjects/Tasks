package com.crud.tasks.terllo.client;

import com.crud.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


//    TrelloBoardDto[] boardsResponse = restTemplate.getForObject(          // wersja z konkatencją
//            trelloApiEndpoint + "/members/kodillaautor/boards" +          // zmiennych środowiskowych
//            "?key=" + trelloAppKey +
//            "&token=" + trelloToken,
//            TrelloBoardDto[].class
//    );


//    public List<TrelloBoardDto> getTrelloBoards() {
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
//                "https://api.trello.com/1/members/kodillaautor/boards",       // wer. z adresem
//                TrelloBoardDto[].class
//        );
//    }


@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUsername;


    public List<TrelloBoardDto> getTrelloBoards() {

        return getTrelloURL();
    }


    private List<TrelloBoardDto> getTrelloURL() {

        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);

     /*
            if (boardsResponse != null) {
                return Arrays.asList(boardsResponse);
            }
            return new ArrayList<>();
       */                                                               //To samo tyle, że jako Optional:

        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());

    }
}
