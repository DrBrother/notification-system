package ru.tinkoff.edu.java.linkparser.dto;

public record GitHubDTO(String userName, String repositoryName) implements ILinkDTO {
}