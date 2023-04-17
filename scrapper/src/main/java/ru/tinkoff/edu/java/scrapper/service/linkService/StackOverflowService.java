package ru.tinkoff.edu.java.scrapper.service.linkService;

import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.scrapper.entity.Link;

public interface StackOverflowService {

    void processStackOverflow(ResponseContainer<ILinkDTO> response, Link link);

}