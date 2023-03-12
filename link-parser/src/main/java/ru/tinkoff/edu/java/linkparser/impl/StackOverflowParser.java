package ru.tinkoff.edu.java.linkparser.impl;

import ru.tinkoff.edu.java.linkparser.AbstractParser;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

public class StackOverflowParser extends AbstractParser {

    private static final String LINK = "stackoverflow.com";
    public static final String PATH = "questions";

    @Override
    protected ResponseContainer<ILinkDTO> parseInternal(URL url) {
        if (LINK.equals(url.getHost())) {
            int id = getQuestionId(getArrayFromURL(url));
            return new ResponseContainer<>(new StackOverflowDTO(id));
        } else {
            return null;
        }
    }

    private int getQuestionId(List<String> path) {
        if (path.isEmpty() || path.size() < 2) {
            throw new NoSuchElementException("Url has no elements in path");
        }
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).equals(PATH)) {
                return Integer.parseInt(path.get(i + 1));
            }
        }
        throw new NoSuchElementException("Url has no 'questions' in path");
    }

}