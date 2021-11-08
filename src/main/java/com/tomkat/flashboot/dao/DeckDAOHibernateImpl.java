package com.tomkat.flashboot.dao;

import com.tomkat.flashboot.entity.Deck;
import com.tomkat.flashboot.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeckDAOHibernateImpl implements DeckDAO{
    private final EntityManager entityManager;
    private final UserDAO userDAO;

    @Autowired
    public DeckDAOHibernateImpl(EntityManager entityManager, UserDAO userDAO) {
        this.entityManager = entityManager;
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public ArrayList<Deck> getAllByUserId(Long userId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Deck> query = session.createQuery("from Deck where userId = :userId" , Deck.class);
        return (ArrayList<Deck>) query.getResultList();
    }

    @Override
    @Transactional
    public ArrayList<Deck> getAllByUserEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "FROM Deck where user.email like :email";
        Query<Deck> query = session.createQuery(hql, Deck.class).setParameter("email", email);
        return (ArrayList<Deck>) query.getResultList();
    }

    @Override
    @Transactional
    public Deck addDeck(Deck deck, String userEmail) {
        User user = userDAO.findUser(userEmail);
        Session session = entityManager.unwrap(Session.class);
        deck.setUser(user);
        session.saveOrUpdate(deck);
        return deck;
    }
}
