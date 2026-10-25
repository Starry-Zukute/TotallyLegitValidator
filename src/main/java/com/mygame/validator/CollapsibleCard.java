package com.mygame.validator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;


public class CollapsibleCard extends javax.swing.JPanel {
    private String paper = "paper_card";
    private boolean expanded;
    private int bodyHeight;
    private java.awt.Color accent;
    
    public CollapsibleCard() {
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        PixelUI.nineSlice(g2, Assets.image(paper), 0, 0, getWidth(), getHeight(), 12, true);
        g2.dispose();
    }

    // ------------------------------------------------------------------ content (overloaded)

    public void setContent(String headline, String body) {
        headlineArea.setText(headline);
        headlineArea.setCaretPosition(0);
        setBody(body);
    }

    /** Overload: fills the card straight from a news item (a MainNews or any Article). */
    public void setContent(NewsItem item) {
        setContent(item.getHeadline(), item.getBody());
    }

    public void setBody(String body) {
        bodyArea.setText(body);
        bodyArea.setCaretPosition(0);
    }

    // ------------------------------------------------------------------ bean properties (shown in the Properties window)

    /** Small label above the headline, e.g. "MAIN NEWS" or "ARTICLE A". */
    public String getTagText() {
        return tagLabel.getText();
    }

    public void setTagText(String tag) {
        tagLabel.setText(tag);
    }

    /** Rows reserved for the headline (the main news needs more than an article). */
    public int getHeadlineRows() {
        return headlineArea.getRows();
    }

    public void setHeadlineRows(int rows) {
        headlineArea.setRows(rows);
        revalidate();
    }

    /** Height of the text area when enlarged (0 = fill all available space). */
    public int getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(int height) {
        this.bodyHeight = height;
        bodyScroll.setPreferredSize(height > 0 ? new Dimension(100, height) : null);
        revalidate();
    }

    /** Name of the paper sprite: "paper_card" or "paper_card_alt". */
    public String getPaper() {
        return paper;
    }

    public void setPaper(String spriteName) {
        this.paper = spriteName;
        repaint();
    }

    /** Colour of the tag and of the card border (e.g. green = correct, red = wrong, gray = removed). */
    public Color getAccent() {
        return accent;
    }

    public void setAccent(Color color) {
        this.accent = color;
        if (color == null) {
            setBorder(null);
            return;
        }
        tagLabel.setForeground(color);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    public boolean isRemoveVisible() {
        return removeButton.isVisible();
    }

    public void setRemoveVisible(boolean visible) {
        removeButton.setVisible(visible);
    }

    public String getRemoveText() {
        return removeButton.getText();
    }

    public void setRemoveText(String text) {
        removeButton.setText(text);
    }

    // ------------------------------------------------------------------ run-time state (changed while the game runs)

    public void setRemoveEnabled(boolean enabled) {
        removeButton.setEnabled(enabled);
    }

    public void setToggleEnabled(boolean enabled) {
        toggleButton.setEnabled(enabled);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        bodyScroll.setVisible(expanded);
        toggleButton.setText(expanded ? "Minimize" : "Enlarge");
        // red arrow down = open the card downward, blue arrow up = fold it away
        toggleButton.setIcon(Assets.icon(expanded ? "arrow_down" : "arrow_up", 20));
        revalidate();
        repaint();
    }
    
     // ------------------------------------------------------------------ events

    /** Registers a listener. Having this pair of methods is what makes "card" events appear in the designer. */
    public void addCardListener(CardListener listener) {
        listenerList.add(CardListener.class, listener);
    }

    public void removeCardListener(CardListener listener) {
        listenerList.remove(CardListener.class, listener);
    }

    private void fireToggled() {
        CardEvent evt = new CardEvent(this, expanded);
        for (CardListener l : getListeners(CardListener.class)) {
            l.toggled(evt);
        }
    }

    private void fireRemoveRequested() {
        CardEvent evt = new CardEvent(this, expanded);
        for (CardListener l : getListeners(CardListener.class)) {
            l.removeRequested(evt);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerPanel = new javax.swing.JPanel();
        tagLabel = new javax.swing.JLabel();
        toggleButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        headlineArea = new javax.swing.JTextArea();
        removeButton = new javax.swing.JButton();
        bodyScroll = new javax.swing.JScrollPane();
        bodyArea = new javax.swing.JTextArea();

        setMinimumSize(new java.awt.Dimension(0, 0));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(250, 200));
        setLayout(new java.awt.BorderLayout());

        headerPanel.setOpaque(false);
        headerPanel.setLayout(new java.awt.BorderLayout());

        tagLabel.setFont(new java.awt.Font("Footlight MT Light", 1, 12)); // NOI18N
        tagLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tagLabel.setText("CARD");
        headerPanel.add(tagLabel, java.awt.BorderLayout.PAGE_START);

        toggleButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        toggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/images/arrow_up_icon.png"))); // NOI18N
        toggleButton.setText("Enlarge");
        toggleButton.setIconTextGap(8);
        toggleButton.addActionListener(this::toggleButtonActionPerformed);
        headerPanel.add(toggleButton, java.awt.BorderLayout.LINE_END);

        headlineArea.setEditable(false);
        headlineArea.setColumns(20);
        headlineArea.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        headlineArea.setForeground(new java.awt.Color(40, 36, 56));
        headlineArea.setLineWrap(true);
        headlineArea.setRows(2);
        headlineArea.setWrapStyleWord(true);
        headlineArea.setFocusable(false);
        headlineArea.setOpaque(false);
        jScrollPane1.setViewportView(headlineArea);

        headerPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(headerPanel, java.awt.BorderLayout.PAGE_START);

        removeButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        removeButton.setText("Remove");
        removeButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        removeButton.setPreferredSize(new java.awt.Dimension(20, 40));
        removeButton.addActionListener(this::removeButtonActionPerformed);
        add(removeButton, java.awt.BorderLayout.PAGE_END);

        bodyScroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180)));
        bodyScroll.setOpaque(false);
        bodyScroll.getViewport().setOpaque(false);
        bodyScroll.getViewport().setScrollMode(javax.swing.JViewport.SIMPLE_SCROLL_MODE);
        bodyScroll.setVisible(false);

        bodyArea.setEditable(false);
        bodyArea.setColumns(20);
        bodyArea.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        bodyArea.setForeground(new java.awt.Color(40, 36, 56));
        bodyArea.setLineWrap(true);
        bodyArea.setRows(5);
        bodyArea.setWrapStyleWord(true);
        bodyArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        bodyArea.setOpaque(false);
        bodyScroll.setViewportView(bodyArea);

        add(bodyScroll, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        fireRemoveRequested();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void toggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonActionPerformed
        setExpanded(!expanded);
        fireToggled();
    }//GEN-LAST:event_toggleButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea bodyArea;
    private javax.swing.JScrollPane bodyScroll;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTextArea headlineArea;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JLabel tagLabel;
    private javax.swing.JButton toggleButton;
    // End of variables declaration//GEN-END:variables
}
