package ru.tinkoff.edu.java.scrapper.entity;

import java.net.URL;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Link {

    private Long id;
    private URL url;
    private OffsetDateTime checkTime;
    private OffsetDateTime updateTime;

}
