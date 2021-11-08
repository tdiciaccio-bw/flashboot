package com.tomkat.flashboot.rest;

import com.tomkat.flashboot.dao.CardDAO;
import com.tomkat.flashboot.entity.BulkInsertPayload;
import com.tomkat.flashboot.entity.Card;
import com.tomkat.flashboot.entity.ScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardRestController {
    private final CardDAO cardDAO;

    @Autowired
    public CardRestController(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @GetMapping()
    public List<Card> findAll() {
        return cardDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findCard(@PathVariable Long id) {
        Card card = cardDAO.findCard(id);
        return ResponseEntity.created(URI.create(String.format("/cards/%s", card.getId()))).body(card);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void scoreCard(@PathVariable Long id, @RequestBody ScoreResponse scoreResponse) {
        cardDAO.updateCard(scoreResponse.getCard().getId(), scoreResponse.getScore());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ArrayList<Card>> insertCards(@RequestBody BulkInsertPayload payload) {
        ArrayList<Card> cards = cardDAO.bulkInsertCard(payload.getCards(), payload.getDeck());
        return ResponseEntity.status(HttpStatus.CREATED).body(cards);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardDAO.deleteCard(id);
    }
}
