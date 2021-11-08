package com.tomkat.flashboot.dao;

import com.tomkat.flashboot.entity.Card;
import com.tomkat.flashboot.entity.Deck;
import com.tomkat.flashboot.entity.ScoreResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CardDAOHibernateImpl implements CardDAO {

    private final EntityManager entityManager;

    @Autowired
    public CardDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Card> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Card> query = currentSession.createQuery("from Card", Card.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Card findCard(Card card) {
        Session session = entityManager.unwrap(Session.class);
        return session.find(Card.class, card);
    }

    @Override
    @Transactional
    public Card findCard(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.find(Card.class, id);
    }

    @Override
    @Transactional
    public Card insertCard(Card card) {
        Session session = entityManager.unwrap(Session.class);
        System.out.println("Card id: " + card.getId());
        session.saveOrUpdate(card);
        System.out.println("Card id: " + card.getId());
        return card;
    }

    @Override
    @Transactional
    public ArrayList<Card> bulkInsertCard(ArrayList<Card> cardArrayList, Long deckId) {
        Session session = entityManager.unwrap(Session.class);
        for (int i = 0; i < cardArrayList.size(); i++) {
            Card card = cardArrayList.get(i);
            Deck deck = entityManager.getReference(Deck.class, deckId);
            card.setDeck(deck);
            session.save(cardArrayList.get(i));
            if (i % 50 == 0) {
                flushAndClear();
            }
        }
        return cardArrayList;
    }

    @Override
    @Transactional
    public Card updateCard(Long id, int score) {
        Session session = entityManager.unwrap(Session.class);
        Card card = session.find(Card.class, id);
        card.updateCard(score);
        session.saveOrUpdate(card);
        return card;
    }

    @Override
    @Transactional
    public ArrayList<Card> getLesson(Long deck, Integer size) {
        if (size == null) {
            size = 10;
        }

        Session session = entityManager.unwrap(Session.class);
        String hql = "from Card where deck.id = :deckId order by nextDueDate";
        TypedQuery<Card> typedQuery = session.createQuery(hql, Card.class).setMaxResults(size);
        typedQuery.setParameter("deckId", deck);
        return (ArrayList<Card>) typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void updateLesson(ArrayList<ScoreResponse> scoreResponses) {
        for (ScoreResponse response : scoreResponses) {
            updateCard(response.getCard().getId(), response.getScore());
        }
    }

    @Override
    @Transactional
    public void deleteCard(Long id) {
        Card foundCard = entityManager.find(Card.class, id);
        entityManager.remove(foundCard);
        flushAndClear();
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
