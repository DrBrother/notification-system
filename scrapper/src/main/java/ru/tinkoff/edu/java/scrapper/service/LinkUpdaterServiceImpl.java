package ru.tinkoff.edu.java.scrapper.service;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.linkparser.Parser;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationProperties;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.linkService.GitHubService;
import ru.tinkoff.edu.java.scrapper.service.linkService.StackOverflowService;

@AllArgsConstructor
@Service
public class LinkUpdaterServiceImpl implements LinkUpdaterService {

    private final LinkDAO linkDao;
    private final Parser linkParser;
    private final GitHubService gitHubService;
    private final StackOverflowService stackOverflowService;
    private final ApplicationProperties properties;

    @Transactional
    @Override
    public int update() {
        List<Link> links = linkDao.findOlderThan(OffsetDateTime.now().minusMinutes(properties.linkCheckPeriodMinutes()));
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
