package ru.tinkoff.edu.java.scrapper.service.linkService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.common.dto.LinkUpdate;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GithubClient;
import ru.tinkoff.edu.java.scrapper.dao.ChatDAO;
import ru.tinkoff.edu.java.scrapper.dao.LinkDAO;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEvent;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubEventType;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.entity.Chat;
import ru.tinkoff.edu.java.scrapper.entity.Link;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TimeZone;

@AllArgsConstructor
@Service
public class GitHubServiceImpl implements GitHubService {

    private final LinkDAO linkDao;
    private final ChatDAO chatDao;
    private final BotClient botClient;
    private final GithubClient gitHubClient;

    @Override
    public void processGitHub(ResponseContainer<ILinkDTO> response, Link link) {
        checkGutHub(response, link);
    }

    protected void checkGutHub(ResponseContainer<ILinkDTO> response, Link link) {
        String repoName = ((GitHubDTO) response.response()).repositoryName();
        String username = ((GitHubDTO) response.response()).userName();
        GitHubResponse gitHubResponse = gitHubClient.fetchRepository(username, repoName).block();
        OffsetDateTime localTime = getTimeWithTimeZone(gitHubResponse.updatedTime());
        if (link.getUpdateTime().isBefore(localTime)) {
            List<Chat> ids = chatDao.findAllByLink(link.getId());
            link.setUpdateTime(localTime);
            linkDao.update(link);

            String updateDescription = generateUpdateDescription(username, repoName, link);

            botClient.pullLinks(new LinkUpdate(link.getId(), link.getUrl().toString(), updateDescription,
                    ids.stream().map(Chat::getId).toArray(Long[]::new))).block();
            return;
        }
        linkDao.update(link);
    }

    protected String generateUpdateDescription(String username, String repoName, Link link) {
        StringBuilder stringBuilder = new StringBuilder();
        List<GitHubEvent> eventList = gitHubClient.fetchRepositoryEvents(username, repoName).collectList().block().stream()
                .filter(gitHubEvent -> gitHubEvent.createTime().isAfter(link.getUpdateTime())).toList();

        stringBuilder.append(checkPushEvents(eventList, link));
        stringBuilder.append(checkPullRequestReviewCommentEvents(eventList));
        stringBuilder.append(checkCreateEvents(eventList, link));

        return stringBuilder.toString();
    }

    protected String checkPushEvents(List<GitHubEvent> eventList, Link link) {
        StringBuilder stringBuilder = new StringBuilder();
        List<GitHubEvent> pushEvents = eventList.stream()
                .filter(gitHubEvent -> gitHubEvent.type().equals(GitHubEventType.PUSH_EVENT.getValue())).toList();

        if (!pushEvents.isEmpty()) {
            stringBuilder
                    .append("Новые коммиты в ")
                    .append(link.getUrl())
                    .append(" :");
            for (GitHubEvent gitHubEvent : pushEvents) {
                for (GitHubEvent.Payload.Commit commit : gitHubEvent.payload().commits()) {
                    stringBuilder
                            .append("\n ")
                            .append("автор ").append(gitHubEvent.actor().login())
                            .append("\n ")
                            .append(commit.commitHash())
                            .append(" — ")
                            .append(commit.message())
                            .append("\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    protected String checkPullRequestReviewCommentEvents(List<GitHubEvent> eventList) {
        StringBuilder stringBuilder = new StringBuilder();
        List<GitHubEvent> pullRequestEvents = eventList.stream()
                .filter(gitHubEvent -> gitHubEvent.type().equals(GitHubEventType.PULL_REQUEST_REVIEW_COMMENT_EVENT.getValue()))
                .toList();

        if (!pullRequestEvents.isEmpty()) {
            stringBuilder.append("Новые комментарии к пулл реквестам: ");
            for (GitHubEvent gitHubEvent : pullRequestEvents) {
                stringBuilder
                        .append("\n")
                        .append(gitHubEvent.actor().login())
                        .append(" написал: ")
                        .append(gitHubEvent.payload().comment().comment())
                        .append("\n")
                        .append(gitHubEvent.payload().pullRequest().title())
                        .append(" — ")
                        .append(gitHubEvent.payload().pullRequest().url())
                        .append("\n");

            }
        }
        return stringBuilder.toString();
    }

    protected String checkCreateEvents(List<GitHubEvent> eventList, Link link) {
        StringBuilder stringBuilder = new StringBuilder();
        List<GitHubEvent> pullRequestEvents = eventList.stream()
                .filter(gitHubEvent -> gitHubEvent.type().equals(GitHubEventType.CREATE_EVENT.getValue()))
                .toList();

        if (!pullRequestEvents.isEmpty()) {
            stringBuilder
                    .append("Новые branch/tag в ")
                    .append(link.getUrl())
                    .append(" :");
            for (GitHubEvent gitHubEvent : pullRequestEvents) {
                stringBuilder
                        .append("\n")
                        .append(gitHubEvent.actor().login())
                        .append(" создал ")
                        .append(gitHubEvent.payload().referenceType())
                        .append(" с названием ")
                        .append(gitHubEvent.payload().reference());
            }
        }
        return stringBuilder.toString();
    }

    private OffsetDateTime getTimeWithTimeZone(OffsetDateTime time) {
        return time.plusNanos(TimeZone.getDefault().getRawOffset() * 1_000_000L);
    }

}