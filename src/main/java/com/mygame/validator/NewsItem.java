package com.mygame.validator;

/**
 * Base class of everything the player reads (ABSTRACTION + INHERITANCE).
 * The text is private (ENCAPSULATION) and can only be read through getters.
 */
public abstract class NewsItem {
    private final String headline;
    private final String body;

    protected NewsItem(String headline, String body) {
        if (headline == null || body == null) {
            throw new IllegalArgumentException("A news item needs a headline and a body.");
        }
        this.headline = headline;
        this.body = body;
    }

    public String getHeadline() {
        return headline;
    }

    public String getBody() {
        return body;
    }

    /** Short label shown on the card, e.g. "MAIN NEWS". Every subclass decides its own label. */
    public abstract String getKindLabel();

    @Override
    public String toString() {
        return getKindLabel() + ": " + headline;
    }
}
