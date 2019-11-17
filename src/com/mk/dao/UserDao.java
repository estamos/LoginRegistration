package com.mk.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import com.mk.bean.User;

public class UserDao {
	
	private boolean flag=true;
	private Session session;
	
	private Session Start() {
		if(flag) {
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
			Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			SessionFactory sessionFactory = metaData.getSessionFactoryBuilder().build();
	
	        session = sessionFactory.getCurrentSession();
	        session.beginTransaction();

	        flag=false;
		}
	        
        return session;
	}
	
	public void registerUser(String firstname,String lastname,String username,String password) throws Exception {
		Session session = Start();

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setPassword(password);

        session.save(user);
        session.getTransaction().commit();
		
	}
	
	public boolean authenticate(String username, String password) throws Exception {
        Session session = Start();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query<User> q = session.createQuery(query);
        List<User> list = q.getResultList();

        for(User user: list) {
        	if(user.getUsername().equals(username))
        		return user.getPassword().equals(password);
        }
        
		return false;
	}
	
	public boolean isExist(String username) throws Exception {		
        Session session = Start();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query<User> q = session.createQuery(query);
        List<User> list = q.getResultList();

        for(User user: list) {
        	if(user.getUsername().equals(username))
        		return true;
        }
        
		return false;
	}
}
