package com.mygame.validator;

/** A fake or unsupported article. Keeping it is punished. */
public final class FakeArticle extends Article {
    public FakeArticle(String headline, String body, String explanation) {
        super(headline, body, explanation);
    }

    @Override
    public boolean isCredible() {
        return false;
    }

    @Override
    public int getCredibilityEffect() {
        return -GameManager.CREDIBILITY_CHANGE;      // -10
    }

    @Override
    public String toString() {
        return super.toString() + " [fake]";
    }
}
