package com.tomkat.flashboot.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class CardTests {

    @BeforeTest
    void setup() {
        Card card = new Card();
    }

    @Test
    public void defaultEasinessShouldBeTwoPointFive() {
        Card card = new Card();
        Assert.assertEquals(card.getEasiness(), 2.5);
    }

    @Test
    public void easinessShouldNotExceedTwoPointFive() {
        Card card = new Card();
        card.setEasiness(5);
        card.setEasiness(5);
        card.setEasiness(5);
        Assert.assertEquals(card.getEasiness(), 2.5);
    }

    @Test
    public void easinessShouldUpdateMultipleTimesInRange() {
        Card card = new Card();
        card.setEasiness(4);
        card.setEasiness(3);
        card.setEasiness(2);
        Assert.assertEquals(card.getEasiness(), 2.1);
    }

    @Test
    public void easinessShouldNotGoBelow1point3() {
        Card card = new Card();
        card.setEasiness(0);
        card.setEasiness(0);
        Assert.assertEquals(card.getEasiness(), 1.3);
    }

    @Test
    public void nextReviewDateShouldCalculateCorrectlyForZeroDayStreak() {
        Card card = new Card();
        card.setNextDueDate(LocalDate.parse("2021-09-11"));
        card.setEasiness(1);
        LocalDate nextDate = card.calculateNextReviewDate(card.getNextDueDate());
        LocalDate date = LocalDate.parse("2021-09-12");
        Assert.assertEquals(nextDate, date);
    }

    @Test
    public void nextReviewDateShouldCalculateCorrectlyForOneDayStreak() {
        Card card = new Card();
        card.setNextDueDate(LocalDate.parse("2021-09-11"));
        card.setEasiness(1);
        card.setCorrectStreak(1);
        LocalDate nextDate = card.calculateNextReviewDate(card.getNextDueDate());
        LocalDate date = LocalDate.parse("2021-09-17");
        Assert.assertEquals(nextDate, date);
    }

    @Test
    public void nextReviewDateShouldCalculateCorrectlyForTwoDayStreak() {
        Card card = new Card();
        card.setNextDueDate(LocalDate.parse("2021-09-11"));
        card.setEasiness(5);
        card.setEasiness(5);
        card.setCorrectStreak(2);
        LocalDate nextDate = card.calculateNextReviewDate(card.getNextDueDate());
        LocalDate date = LocalDate.parse("2021-09-26");
        Assert.assertEquals(nextDate, date);
    }

    @Test
    public void failedCardShouldResetStreak() {
        Card card = new Card();
        card.setCorrectStreak(10);
        card.updateCard(0);
        Assert.assertEquals(card.getCorrectStreak(), 0);
    }

    @Test
    public void failedCardShouldReviewTheNextDay() {
        Card card = new Card();
        card.setNextDueDate(LocalDate.parse("2021-10-01"));
        card.updateCard(0);
        Assert.assertEquals(card.getNextDueDate(), LocalDate.parse("2021-10-02"));
    }

    @Test
    public void successfulCardShouldIncrementStreak() {
        Card card = new Card();
        card.setNextDueDate(LocalDate.parse("2021-10-01"));
        card.updateCard(3);
        Assert.assertEquals(card.getCorrectStreak(), 1);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void updateCardShouldThrowWithOutOfRangeInput() {
        Card card = new Card();
        card.updateCard(17);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void updateCardShouldThrowWithNegativeInput() {
        Card card = new Card();
        card.updateCard(-5);
    }

    @Test
    public void toStringOverrideShouldWork() {
        Card card = new Card();
        card.setId(5L);
        card.setQuestion("TestQuestion");
        card.setAnswer("TestAnswer");
        String expected = "Card{id=5, question='TestQuestion', answer='TestAnswer'}";
        Assert.assertEquals(card.toString(), expected);
    }
}
