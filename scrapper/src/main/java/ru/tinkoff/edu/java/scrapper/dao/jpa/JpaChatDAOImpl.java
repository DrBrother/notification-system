package ru.tinkoff.edu.java.scrapper.dao.jpa;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.jpa.ChatEntity;

@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "databaseAccessType", havingValue = "jpa")
@Repository
public class JpaChatDAOImpl implements ChatDAO {

    private final JpaChatRepository jpaChatRepository;

    @Transactional
    @Override
    public void add(long chatId) {
        jpaChatRepository.save(new ChatEntity(chatId, new HashSet<>()));
    }

    @Transactional
    @Override
    public void remove(long chatId) {
        jpaChatRepository.deleteById(chatId);
    }

    @Transactional
    @Override
    public List<Chat> findAllByLink(long linkId) {
        return jpaChatRepository.findAllByLink(linkId).stream().map(this::convertFromEntity).toList();
    }

    private Chat convertFromEntity(ChatEntity chatEntity) {
        Chat chat = new Chat();
        chat.setId(chatEntity.getId());
        return chat;
    }

}
