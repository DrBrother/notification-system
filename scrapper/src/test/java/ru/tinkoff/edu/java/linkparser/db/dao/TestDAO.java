package ru.tinkoff.edu.java.linkparser.db.dao;

public interface TestDAO {

    void init();

    void addAndFindLinkTest();

    void findAllLinksByChatTest();

    void subscriptionTest();

    void updateLinkTest();

    void findOlderThanLinkTest();

}