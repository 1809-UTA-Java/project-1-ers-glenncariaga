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
		return found;
	}
	
	public ERS_Users saveUser(ERS_Users user) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		ERS_Users _user =  (ERS_Users) session.save(user);
		tx.commit();
		return _user;
	}
	
	public ERS_Users updateUser(String id, ERS_Users user) {
		ERS_Users _user = getUserById(id);
		if(_user != null) {
			_user.setEmail(user.getEmail()!=null?user.getEmail():_user.getEmail());
			_user.setFirstName(user.getFirstName()!=null?user.getFirstName():_user.getFirstName());
			_user.setId(user.getId()!=null?user.getId():_user.getId());
			_user.setLastName(user.getLastName()!=null?user.getLastName():_user.getLastName());
			_user.setPassword(user.getPassword()!=null?user.getPassword():_user.getPassword());
			_user.setUsername(user.getUsername()!=null?user.getUsername():_user.getUsername());
			_user.setUserRole(user.getUserRole()!=null?user.getUserRole():_user.getUserRole());
			
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			ERS_Users txUser =  (ERS_Users) session.save(_user);
			tx.commit();
			return txUser;
		}
		return null;
	}
}
