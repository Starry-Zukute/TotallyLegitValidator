package com.mygame.validator;

import java.util.List;

/**
 * Game state. Each level the player REMOVES one of two articles; the other one is retained.
 * The retained article decides the score by itself (polymorphism): a CredibleArticle gives +10,
 * a FakeArticle gives -10, so this class contains no if/else on the article type.
 */
public class GameManager {
    public static final int TOTAL_LEVELS = 10;
    public static final int STARTING_CREDIBILITY = 0;
    public static final int CREDIBILITY_CHANGE = 10;
    public static final int MAX_CREDIBILITY = 100;

    private List<Level> levels;
    private List<Article> displayedArticles;
    private int levelIndex;
    private int credibility;
    private int correctDecisions;
    private int incorrectDecisions;
    private boolean levelResolved;

    public GameManager() {
        restartGame();
    }

    public void restartGame() {
        levels = ArticleDatabase.shuffledLevels();
        levelIndex = 0;
        credibility = STARTING_CREDIBILITY;
        correctDecisions = 0;
        incorrectDecisions = 0;
        prepareLevel();
    }

    private void prepareLevel() {
        levelResolved = false;
        displayedArticles = isGameOver() ? null : getCurrentLevel().shuffledArticles();
    }

    // ---- getters
    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    /** The two articles of the current level, in the order they are displayed. */
    public List<Article> getDisplayedArticles() {
        return displayedArticles;
    }

    public int getLevelNumber() {
        return levelIndex + 1;
    }

    public int getCredibility() {
        return credibility;
    }

    public int getCorrectDecisions() {
        return correctDecisions;
    }

    public int getIncorrectDecisions() {
        return incorrectDecisions;
    }

    public boolean isLastLevel() {
        return levelIndex == TOTAL_LEVELS - 1;
    }

    public boolean isLevelResolved() {
        return levelResolved;
    }

    public boolean isGameOver() {
        return levelIndex >= TOTAL_LEVELS;
    }

    // ---- the only way credibility can change (ENCAPSULATION: the setter validates)
    private void setCredibility(int value) {
        credibility = Math.max(0, Math.min(MAX_CREDIBILITY, value));
    }

    // ---- decisions (OVERLOADED: by position or by the article itself)

    /**
     * The player removes the article at removedIndex (0 or 1). The other article is retained and
     * scores itself. Does NOT advance the level; call nextLevel() for that.
     * @return true when the retained article is the correct (credible) one
     */
    public boolean removeArticle(int removedIndex) {
        if (levelResolved || isGameOver()) {
            throw new IllegalStateException("This level has already been resolved.");
        }
        Article retained = displayedArticles.get(1 - removedIndex);
        setCredibility(credibility + retained.getCredibilityEffect());   // polymorphic call
        boolean correct = retained.isCredible();
        if (correct) {
            correctDecisions++;
        } else {
            incorrectDecisions++;
        }
        levelResolved = true;
        return correct;
    }

    /** Same decision, but naming the article that is removed. */
    public boolean removeArticle(Article removed) {
        int index = displayedArticles.indexOf(removed);
        if (index < 0) {
            throw new IllegalArgumentException("That article is not part of the current level.");
        }
        return removeArticle(index);
    }

    /** Moves on to the next level (only after the current one has been resolved). */
    public void nextLevel() {
        if (!levelResolved) {
            return;
        }
        levelIndex++;
        prepareLevel();
    }
}
