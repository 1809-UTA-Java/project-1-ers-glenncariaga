package com.revature.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "ers_reimbursements", schema ="ersadmin")
public class Reimbursement {
	@Id
	@Column(name = "r_id")
	private String id;
	@Column (name = "r_description")
	private String description;
	@Column (name = "u_id_resolver")
	private String resolver;
	@Column (name = "r_type")
	private String type;
	@Column (name = "r_status")
	private String status;
	@Column (name = "r_amount")
	private Double amount;
	@Column (name = "u_id_author")
	private String submittedBy;
	@Column (name = "r_resolved")
	private Timestamp resolvedOn;
	@Column (name ="r_submitted")
	private Timestamp submittedOn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getResolver() {
		return resolver;
	}
	public void setResolver(String resolver) {
		this.resolver = resolver;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public Timestamp getResolvedOn() {
		return resolvedOn;
	}
	public void setResolvedOn(Timestamp resolvedOn) {
		this.resolvedOn = resolvedOn;
	}
	public Timestamp getSubmittedOn() {
		return submittedOn;
	}
	public void setSubmittedOn(Timestamp submittedOn) {
		this.submittedOn = submittedOn;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", description=" + description + ", resolver=" + resolver + ", type=" + type
				+ ", status=" + status + ", amount=" + amount + ", submittedBy=" + submittedBy + ", resolvedOn="
				+ resolvedOn + ", submittedOn=" + submittedOn + "]";
	}
	
	
}
