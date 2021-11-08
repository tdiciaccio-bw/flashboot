package com.tomkat.flashboot.rest;

import com.tomkat.flashboot.dao.CardDAO;
import com.tomkat.flashboot.entity.Card;
import com.tomkat.flashboot.entity.ScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/lesson")
public class LessonRestController {
    private final CardDAO cardDAO;

    @Autowired
    public LessonRestController(CardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping()
    public ResponseEntity<ArrayList<Card>> getLesson(@RequestParam(name = "size", required = false) Integer size,
                                                     @RequestParam(name = "deck", required = true) Long deck) {
        System.out.println("Received Get Request");
        ArrayList<Card> lesson = cardDAO.getLesson(deck, size);
        return ResponseEntity.status(HttpStatus.OK).body(lesson);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void completeLesson(@RequestBody ArrayList<ScoreResponse> scoredCards) {
        System.out.println("Updated lesson");
        cardDAO.updateLesson(scoredCards);
    }
}
