package com.mygame.validator;

/** Anything that changes the player's credibility when it is kept (ABSTRACTION: an interface). */
public interface Scorable {
    /** Credibility points gained (positive) or lost (negative) when this item is the retained news. */
    int getCredibilityEffect();
}
