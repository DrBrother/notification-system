package ru.tinkoff.edu.java.linkparser.impl;

import ru.tinkoff.edu.java.linkparser.AbstractParser;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

public class GitHubParser extends AbstractParser {

    private static final String LINK = "github.com";

    @Override
    protected ResponseContainer<ILinkDTO> parseInternal(URL url) {
        if (LINK.equals(url.getHost())) {
            List<String> values = getArrayFromURL(url);
            if (values.size() < 2) {
                throw new NoSuchElementException("Url's path has no at least 2 elements");
            }
            return new ResponseContainer<>(new GitHubDTO(values.get(0), values.get(1)));
        } else {
            return null;
        }
    }

}