package com.mygame.validator;

/** A trustworthy article. Keeping it is rewarded. */
public final class CredibleArticle extends Article {
    public CredibleArticle(String headline, String body, String explanation) {
        super(headline, body, explanation);
    }

    @Override
    public boolean isCredible() {
        return true;
    }

    @Override
    public int getCredibilityEffect() {
        return GameManager.CREDIBILITY_CHANGE;       // +10
    }

    @Override
    public String toString() {
        return super.toString() + " [credible]";
    }
}
