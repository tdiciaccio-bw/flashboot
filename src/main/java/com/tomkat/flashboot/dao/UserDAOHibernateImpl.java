package com.tomkat.flashboot.dao;

import com.tomkat.flashboot.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class UserDAOHibernateImpl implements UserDAO {

    private final EntityManager entityManager;

    @Autowired
    public UserDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public User findUser(String email) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "from User where email= :email";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        query.setParameter("email", email);

        return query.getSingleResult();
    }
}
