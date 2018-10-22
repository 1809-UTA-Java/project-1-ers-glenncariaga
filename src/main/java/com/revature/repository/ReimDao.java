package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimbursement;
import com.revature.util.HibernateUtil;

public class ReimDao {
	@SuppressWarnings("unchecked")
	public List<Reimbursement> getAllReim(){
		Session session = HibernateUtil.getSession();
		return session.createQuery("from Reimbursement").list();
	}
	
	@SuppressWarnings("unchecked")
	public Reimbursement getReimById(String id) {
		Reimbursement found = null;
		List<Reimbursement> reim = new ArrayList<>();
		Session session = HibernateUtil.getSession();
		
		reim = session.createQuery(
				"from Reimbursement where id =:idVar"
				).setString("idVar", id).list();
		if(!reim.isEmpty()) {
			found = reim.get(0);
		}
		session.close();
		return found;
	}
	
	public void saveUser(Reimbursement reim) {
		System.out.println(reim.toString());
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		session.save(reim);
		tx.commit();
		session.close();
	}
	
	public void updateReim(String id, Reimbursement reim) {
		Reimbursement _reim = getReimById(id);
		if(_reim != null) {
			_reim.setAmount(reim.getAmount()!=null?reim.getAmount():_reim.getAmount());
			_reim.setDescription(reim.getDescription()!=null?reim.getDescription():_reim.getDescription());
			_reim.setId(reim.getId()!=null?reim.getId():_reim.getId());
			_reim.setResolvedOn(reim.getResolvedOn()!=null?reim.getResolvedOn():_reim.getResolvedOn());
			_reim.setResolver(reim.getResolver()!=null?reim.getResolver():_reim.getResolver());
			_reim.setStatus(reim.getStatus()!=null?reim.getStatus():_reim.getStatus());
			_reim.setSubmittedBy(reim.getSubmittedBy()!=null?reim.getSubmittedBy():_reim.getSubmittedBy());
			_reim.setSubmittedOn(reim.getSubmittedOn()!=null?reim.getSubmittedOn():_reim.getSubmittedOn());
			_reim.setType(reim.getType()!=null?reim.getType():_reim.getType());
			
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(_reim);
			tx.commit();
			session.close();
		}
	}
	
	public List<Reimbursement> getReimByUser(String id){
		System.out.println("reimbyuser: "+id);
		List<Reimbursement> reims = new ArrayList<>();
		Session session = HibernateUtil.getSession();
		reims = session.createQuery(
				"from Reimbursement where submittedBy =:idVar"
				).setString("idVar", id).list();
		session.close();
		return reims;
	}
	
}
