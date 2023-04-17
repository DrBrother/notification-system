package ru.tinkoff.edu.java.scrapper.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.Parser;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.linkService.GitHubService;
import ru.tinkoff.edu.java.scrapper.service.linkService.StackOverflowService;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class LinkUpdaterServiceImpl implements LinkUpdaterService {

    private final LinkDAO linkDao;
    private final Parser linkParser;
    private final GitHubService gitHubService;
    private final StackOverflowService stackOverflowService;

    @Transactional
    @Override
    public int update() {
        List<Link> links = linkDao.findOlderThan(OffsetDateTime.now().minusMinutes(10));
        ResponseContainer<ILinkDTO> response;
        for (Link link : links) {
            response = linkParser.parseChain(link.getUrl());
            checkUpdates(response, link);
        }
        return 0;
    }

    protected void checkUpdates(ResponseContainer<ILinkDTO> response, Link link) {
        if (response.response() instanceof GitHubDTO) {
            gitHubService.processGitHub(response, link);
        } else if (response.response() instanceof StackOverflowDTO) {
            stackOverflowService.processStackOverflow(response, link);
        }
    }

}