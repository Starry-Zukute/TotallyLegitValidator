package com.mygame.validator;

/**
 * One article the player must evaluate. It is abstract: an article is always either a
 * CredibleArticle (keep it) or a FakeArticle (remove it), and each decides its own scoring.
 */
public abstract class Article extends NewsItem implements Scorable {
    private final String explanation;

    protected Article(String headline, String body, String explanation) {
        super(headline, body);
        this.explanation = explanation == null ? "" : explanation;
    }

    /** Factory: builds the right subclass so callers never need to know which one. */
    public static Article of(String headline, String body, boolean credible, String explanation) {
        return credible
                ? new CredibleArticle(headline, body, explanation)
                : new FakeArticle(headline, body, explanation);
    }

    public String getExplanation() {
        return explanation;
    }

    public abstract boolean isCredible();

    /** KEEP for credible articles, REMOVE for fake ones. */
    public String getCorrectAction() {
        return isCredible() ? "KEEP" : "REMOVE";
    }

    @Override
    public String getKindLabel() {
        return "ARTICLE";
    }
}
