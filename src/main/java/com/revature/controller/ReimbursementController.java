package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.Repository.ReimbursementRepo;
import com.revature.model.Reimbursement;

@RestController
@RequestMapping("/api")
public class ReimbursementController {
	
	@Autowired
	private ReimbursementRepo r_repo;
	
	@GetMapping("/reimbursements")
	public List<Reimbursement> getAllReimbursements() {
		return r_repo.findAll();
	}
	
	@PostMapping("/reimbursement/add")
	public Reimbursement addReimbursement(@RequestBody Reimbursement reimbursement) {
		return r_repo.save(reimbursement);
	}
	
	@GetMapping("/reimbursement/{id}")
	public Reimbursement getReimbursementById(@PathVariable(value = "id") String id) {
		return r_repo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Reimbursement","id",id));
	}
	
	@PutMapping("/reimbursement/{id}")
	public Reimbursement putReimbursement(@PathVariable(value ="id") String id, @RequestBody Reimbursement reimb) {
		Reimbursement _reimb = r_repo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Reimbursement","id",id));
		_reimb.setAmount(reimb.getAmount());
		_reimb.setDescription(reimb.getDescription());
		_reimb.setId(reimb.getId());
		_reimb.setResolvedOn(reimb.getResolvedOn());
		_reimb.setResolver(reimb.getResolver());
		_reimb.setStatus(reimb.getStatus());
		_reimb.setSubmittedBy(reimb.getSubmittedBy());
		_reimb.setSubmittedOn(reimb.getSubmittedOn());
		_reimb.setType(reimb.getType());
		
		Reimbursement updatedReimb = r_repo.save(_reimb);
		
		return updatedReimb;
	}
}
