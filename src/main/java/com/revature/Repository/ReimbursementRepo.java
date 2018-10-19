package com.revature.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.Reimbursement;

@Repository
public interface ReimbursementRepo extends JpaRepository<Reimbursement, String> {
	
}
