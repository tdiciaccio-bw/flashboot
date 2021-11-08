package com.tomkat.flashboot.dao;

import com.tomkat.flashboot.entity.Card;
import com.tomkat.flashboot.entity.Deck;
import com.tomkat.flashboot.entity.ScoreResponse;

import java.util.ArrayList;
import java.util.List;

public interface CardDAO {
    public List<Card> findAll();
    public Card findCard(Card card);
    public Card findCard(Long id);
    public Card insertCard(Card card);
    public ArrayList<Card> bulkInsertCard(ArrayList<Card> cardArrayList, Long deckId);
    public void deleteCard(Long id);
    public Card updateCard(Long id, int score);
    public ArrayList<Card> getLesson(Long deck, Integer size);
    public void updateLesson(ArrayList<ScoreResponse> scoreResponses);
}
