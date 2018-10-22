package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.ERS_Users;
import com.revature.util.HibernateUtil;

public class UserDao {
	@SuppressWarnings("unchecked")
	public List<ERS_Users> getUsers(){
		Session session = HibernateUtil.getSession();
		return session.createQuery("from ERS_Users").list();
	}
	
	@SuppressWarnings("unchecked")
	public ERS_Users getUserById(String id) {
		ERS_Users found = null;
		List<ERS_Users> users = new ArrayList<>();
		Session session = HibernateUtil.getSession();
		
		users = session.createQuery(
				"from ERS_Users where id =:idVar"
				).setString("idVar", id).list();
		if(!users.isEmpty()) {
			found = users.get(0);
		}
		session.close();
		return found;
	}
	
	public ERS_Users getUserByUsername(String username) {
		ERS_Users found = null;
		List<ERS_Users> users = new ArrayList<>();
		Session session = HibernateUtil.getSession();
		
		users = session.createQuery(
				"from ERS_Users where username = :idVar"
				).setString("idVar", username).list();
		if(!users.isEmpty()) {
			found =users.get(0);
		}
		session.close();
		return found;
	}
	
	public void saveUser(ERS_Users user) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		session.close();
	}
	
	public void updateUser(String id, ERS_Users user) {
		System.out.println("Updating Id: " +id);
		ERS_Users _user = getUserById(id);
		if(_user != null) {
			_user = user;
			System.out.println("Updating Id: " +id);
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(_user);
			tx.commit();
			session.close();
		}
	}
}
