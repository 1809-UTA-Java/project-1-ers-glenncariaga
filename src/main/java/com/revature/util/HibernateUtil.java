package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.revature.models.ERS_Users;
import com.revature.models.Reimbursement;

public class HibernateUtil {
	private static SessionFactory sf = createSessionFactory("hibernate.cfg.xml");
	
	private static SessionFactory createSessionFactory(String filename) {
		Configuration configuration = new Configuration();
	    configuration.configure(filename);
	    
	    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	            configuration.getProperties()).build();
	    
	    SessionFactory sessionFactory = configuration
	    		.addAnnotatedClass(ERS_Users.class)
	    		.addAnnotatedClass(Reimbursement.class)
	    		.buildSessionFactory(serviceRegistry);
	    
	    return sessionFactory;
	}
	
	public static Session getSession() {
		return sf.openSession();
	}
	
	public static void shutdown() {
		sf.close();
	}
}
