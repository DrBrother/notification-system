package ru.tinkoff.edu.java.linkparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;
import ru.tinkoff.edu.java.linkparser.impl.GitHubParser;
import ru.tinkoff.edu.java.linkparser.impl.StackOverflowParser;

import java.net.MalformedURLException;
import java.net.URL;

public class LinkParserTest {

    public static final Parser PARSER = AbstractParser.of(new GitHubParser(), new StackOverflowParser());

    @Test
    public void testGitHub() throws MalformedURLException {
        URL url = new URL("https://github.com/user/repos");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertEquals(new GitHubDTO("user", "repos"), response.response());
    }

    @Test
    public void testGitHubInvalidPath() throws MalformedURLException {
        URL url = new URL("https://github.com/user");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertNull(response.response());
    }

    @Test
    public void testStackOverflow() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com/questions/111/some-question");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertEquals(new StackOverflowDTO(111), response.response());
    }

    @Test
    public void testStackOverflowEmptyPath() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertNull(response.response());
    }

    @Test
    public void testStackOverflowInvalidPath() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com/questions");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertNull(response.response());
    }

    @Test
    public void testStackOverflowInvalidPathWithStringId() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com/questions/aaa");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertNull(response.response());
    }

    @Test
    public void testUnsupportedHost() throws MalformedURLException {
        URL url = new URL("https://some.com/questions/111/some-question");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assertions.assertNull(response.response());
    }

}