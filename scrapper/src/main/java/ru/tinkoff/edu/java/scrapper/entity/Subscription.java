package ru.tinkoff.edu.java.scrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subscription {

    private Long chatId;
    private Long linkId;

}