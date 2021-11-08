package com.tomkat.flashboot.entity;

import java.util.ArrayList;

public class BulkInsertPayload {
    private ArrayList<Card> cards;
    private Long deck;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Long getDeck() {
        return deck;
    }

    public void setDeck(Long deck) {
        this.deck = deck;
    }
}