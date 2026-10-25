package com.mygame.validator;

import java.util.EventObject;

/** Event fired by a CollapsibleCard when the player presses its toggle or remove button. */
public class CardEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    private final boolean expanded;

    public CardEvent(CollapsibleCard source, boolean expanded) {
        super(source);
        this.expanded = expanded;
    }

    @Override
    public CollapsibleCard getSource() {
        return (CollapsibleCard) super.getSource();
    }

    /** True when the card is enlarged after the event. */
    public boolean isExpanded() {
        return expanded;
    }
}
