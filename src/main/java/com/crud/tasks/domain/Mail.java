package com.crud.tasks.domain;

import lombok.*;

//@Builder
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private  String toCc;
}