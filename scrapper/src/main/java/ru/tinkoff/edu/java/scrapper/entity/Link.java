package ru.tinkoff.edu.java.scrapper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Link {

    private Long id;
    private URL url;
    private OffsetDateTime checkTime;
    private OffsetDateTime updateTime;

}