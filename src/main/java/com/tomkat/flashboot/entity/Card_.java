package com.tomkat.flashboot.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(Card.class)
public class Card_ {
    public static volatile SingularAttribute<Card, Long> id;
    public static volatile SingularAttribute<Card, String> question;
    public static volatile SingularAttribute<Card, String> answer;
    public static volatile SingularAttribute<Card, Double> easiness;
    public static volatile SingularAttribute<Card, Integer> correctStreak;
    public static volatile SingularAttribute<Card, LocalDate> nextDueDate;
}
