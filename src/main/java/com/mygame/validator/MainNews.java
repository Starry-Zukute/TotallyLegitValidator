package com.mygame.validator;

/** The briefing of a level: the story both articles are compared against. */
public class MainNews extends NewsItem {
    public MainNews(String headline, String body) {
        super(headline, body);
    }

    @Override
    public String getKindLabel() {
        return "MAIN NEWS";
    }
}
