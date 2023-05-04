package ru.tinkoff.edu.java.linkparser;

import java.net.URL;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;

public sealed interface Parser permits AbstractParser {
    ResponseContainer<ILinkDTO> parseChain(URL url);
}
