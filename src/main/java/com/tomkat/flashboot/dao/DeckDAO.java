package com.tomkat.flashboot.dao;

import com.tomkat.flashboot.entity.Deck;

import java.util.ArrayList;

public interface DeckDAO {
    public ArrayList<Deck> getAllByUserId(Long userId);
    public ArrayList<Deck> getAllByUserEmail(String email);
    public Deck addDeck(Deck deck, String userEmail);
}
