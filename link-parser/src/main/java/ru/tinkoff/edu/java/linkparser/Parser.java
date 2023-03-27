package ru.tinkoff.edu.java.linkparser;

import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;

import java.net.URL;

public sealed interface Parser permits AbstractParser {
    ResponseContainer<ILinkDTO> parseChain(URL url);
}