package com.tomkat.flashboot.entity;

public class ScoreResponse {
    private Card card;
    private int score;

    public ScoreResponse(Card card, int score) {
        this.card = card;
        this.score = score;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
