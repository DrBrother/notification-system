package ru.tinkoff.edu.java.scrapper.service.linkService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.Link;
import ru.tinkoff.edu.java.scrapper.service.MessageService;

import java.util.List;

@AllArgsConstructor
@Service
public class StackOverflowServiceImpl implements StackOverflowService {

    private final StackOverflowClient stackOverflowClient;
    private final LinkDAO linkDao;
    private final ChatDAO chatDao;
    private final MessageService messageService;

    @Override
    public void processStackOverflow(ResponseContainer<ILinkDTO> response, Link link) {
        checkStackOverflowResponse(response, link);
    }

    protected void checkStackOverflowResponse(ResponseContainer<ILinkDTO> response, Link link) {
        long questionId = ((StackOverflowDTO) response.response()).questionId();
        StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(questionId).block();
        if (link.getUpdateTime().isBefore(stackOverflowResponse.items()[0].lastEditTime())) {
            List<Chat> ids = chatDao.findAllByLink(link.getId());
            link.setUpdateTime(stackOverflowResponse.items()[0].lastEditTime());
            linkDao.update(link);
            messageService.sendMessage(new LinkUpdate(link.getId(), link.getUrl().toString(), "",
                    ids.stream().map(Chat::getId).toArray(Long[]::new)));
        }
        linkDao.update(link);
    }

}