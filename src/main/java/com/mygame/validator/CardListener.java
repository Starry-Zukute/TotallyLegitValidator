package com.mygame.validator;

import java.util.EventListener;

/**
 * Listener for a CollapsibleCard. Because CollapsibleCard has addCardListener/removeCardListener,
 * the NetBeans designer lists these two methods in the Events tab of every card on a form, and
 * can generate handler methods for them (e.g. articleCardARemoveRequested).
 */
public interface CardListener extends EventListener {
    /** The player pressed the card's REMOVE button. */
    void removeRequested(CardEvent evt);

    /** The player pressed Enlarge / Minimize (the card has already changed state). */
    void toggled(CardEvent evt);
}
