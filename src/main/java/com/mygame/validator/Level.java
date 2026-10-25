package com.mygame.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One level: a main news briefing plus two competing articles.
 * Exactly one article is credible; the other is fake/unsupported.
 */
public class Level {
    private final MainNews mainNews;
    private final List<Article> articles;

    public Level(MainNews mainNews, List<Article> articles) {
        if (mainNews == null || articles == null || articles.size() != 2) {
            throw new IllegalArgumentException("Each level needs main news and exactly two articles.");
        }
        if (articles.get(0).isCredible() == articles.get(1).isCredible()) {
            throw new IllegalArgumentException("Each level needs exactly one credible article.");
        }
        this.mainNews = mainNews;
        this.articles = List.copyOf(articles);
    }

    /** Convenience overload (POLYMORPHISM: overloading): builds the MainNews from plain text. */
    public Level(String mainNewsHeadline, String mainNewsBody, List<Article> articles) {
        this(new MainNews(mainNewsHeadline, mainNewsBody), articles);
    }

    public MainNews getMainNews() {
        return mainNews;
    }

    public String getMainNewsHeadline() {
        return mainNews.getHeadline();
    }

    public String getMainNewsBody() {
        return mainNews.getBody();
    }

    public List<Article> getArticles() {
        return articles;
    }

    /** Returns the two articles in random order so the credible one is not always first. */
    public List<Article> shuffledArticles() {
        List<Article> copy = new ArrayList<>(articles);
        Collections.shuffle(copy);
        return copy;
    }
}
