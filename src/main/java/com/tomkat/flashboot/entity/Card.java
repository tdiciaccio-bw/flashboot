package com.tomkat.flashboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import java.time.LocalDate;

import static java.lang.Math.round;

@Entity
@Table(name="card")
public class Card {
    @Id
    @GeneratedValue
    private Long id;
    private String question;
    private String answer;
    private double easiness;
    private int correctStreak;
    private LocalDate nextDueDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="deck_id")
    private Deck deck;

    public Card() {
        this.easiness = 2.5;
        this.nextDueDate = LocalDate.now().plusDays(1);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public double getEasiness() {
        return easiness;
    }

    protected void setEasiness(int score) {
        double newScore = this.easiness + -.8 + (.28 * score)- (.02 * score * score);
        newScore = Precision.round(newScore, 1);
        if (newScore < 1.3) {
            this.easiness = 1.3;
        } else this.easiness = Math.min(newScore, 2.5);
    }

    public int getCorrectStreak() {
        return correctStreak;
    }

    public void setCorrectStreak(int correctStreak) {
        this.correctStreak = correctStreak;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    private void incrementCorrectStreak() {
        this.correctStreak += 1;
    }

    protected LocalDate calculateNextReviewDate(LocalDate currentDueDate) {
        if (correctStreak == 0) {
            return LocalDate.now().plusDays((1));
        } else {
            double daysToAdd = 6 * Math.pow(easiness, correctStreak - 1);
            return LocalDate.now().plusDays(round(daysToAdd));
        }
    }

    public void updateCard(int score) throws IllegalArgumentException {
        if (score > 5 || score < 0) {
            throw new IllegalArgumentException("Valid scores range between 0 and 5");
        }
        if (score >= 3) {
            incrementCorrectStreak();
        } else {
            setCorrectStreak(0);
        }
        setEasiness(score);
        setNextDueDate(calculateNextReviewDate(nextDueDate));
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
