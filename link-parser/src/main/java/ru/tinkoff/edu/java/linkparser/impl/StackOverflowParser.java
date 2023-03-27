package ru.tinkoff.edu.java.linkparser.impl;

import ru.tinkoff.edu.java.linkparser.AbstractParser;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;

import java.net.URL;
import java.util.List;

public class StackOverflowParser extends AbstractParser {

    private static final String LINK = "stackoverflow.com";
    private static final String PATH = "questions";

    @Override
    protected ResponseContainer<ILinkDTO> parseInternal(URL url) {
        if (LINK.equals(url.getHost())) {
            Integer id = getQuestionId(getArrayFromURL(url));
            if (id == null) {
                return new ResponseContainer<>(null);
            }
            return new ResponseContainer<>(new StackOverflowDTO(id));
        } else {
            return null;
        }
    }

    private Integer getQuestionId(List<String> path) {
        if (path.isEmpty() || path.size() < 2) {
            return null;
        }
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).equals(PATH)) {
                try {
                    return Integer.parseInt(path.get(i + 1));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

}