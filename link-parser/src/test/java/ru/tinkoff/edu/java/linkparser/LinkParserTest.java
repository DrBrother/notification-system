package ru.tinkoff.edu.java.linkparser;

import org.junit.Assert;
import org.junit.Test;
import ru.tinkoff.edu.java.linkparser.dto.GitHubDTO;
import ru.tinkoff.edu.java.linkparser.dto.ILinkDTO;
import ru.tinkoff.edu.java.linkparser.dto.ResponseContainer;
import ru.tinkoff.edu.java.linkparser.dto.StackOverflowDTO;
import ru.tinkoff.edu.java.linkparser.impl.GitHubParser;
import ru.tinkoff.edu.java.linkparser.impl.StackOverflowParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

public class LinkParserTest {

    public static final Parser PARSER = AbstractParser.of(new GitHubParser(), new StackOverflowParser());

    @Test
    public void testGitHub() throws MalformedURLException {
        URL url = new URL("https://github.com/user/repos");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assert.assertEquals(new GitHubDTO("user", "repos"), response.response());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGitHubNoSuchElementException() throws MalformedURLException {
        URL url = new URL("https://github.com/user");
        PARSER.parseChain(url);
    }

    @Test
    public void testStackOverflow() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com/questions/111/some-question");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assert.assertEquals(new StackOverflowDTO(111), response.response());
    }

    @Test(expected = NoSuchElementException.class)
    public void testStackOverflowEmptyPath() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com");
        PARSER.parseChain(url);
    }

    @Test(expected = NoSuchElementException.class)
    public void testStackOverflowNoSuchElementException() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com/questions");
        PARSER.parseChain(url);
    }

    @Test(expected = NumberFormatException.class)
    public void testStackOverflowNumberFormatException() throws MalformedURLException {
        URL url = new URL("https://stackoverflow.com/questions/aaa");
        PARSER.parseChain(url);
    }

    @Test
    public void testUnsupportedURL() throws MalformedURLException {
        URL url = new URL("https://some.com/questions/111/some-question");
        ResponseContainer<ILinkDTO> response = PARSER.parseChain(url);
        Assert.assertNull(response);
    }

}