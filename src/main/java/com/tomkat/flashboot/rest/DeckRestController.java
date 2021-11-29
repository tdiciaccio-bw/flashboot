package com.tomkat.flashboot.rest;

import com.tomkat.flashboot.dao.DeckDAO;
import com.tomkat.flashboot.entity.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/decks")
public class DeckRestController {
    private final DeckDAO deckDAO;

    @Autowired
    public DeckRestController(DeckDAO deckDAO) {
        this.deckDAO = deckDAO;
    }

    @GetMapping()
    public ResponseEntity<ArrayList<Deck>> getDecksByUserId(Principal principal) {
        ArrayList<Deck> decks = deckDAO.getAllByUserEmail(principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(decks);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Deck> addDeck(@RequestBody Deck deck, Principal principal) {
        Deck createdDeck = deckDAO.addDeck(deck, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeck);
    }
}
