package com.revature.models;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name ="ers_reciepts")
public class Reciept {
	@Id
	@Column(name="e_id")
	private String id;
	@Lob
	@Column(name="e_reciept")
	private byte[] reciept;
	@Column(name = "e_r_id")
	private String reimId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getReciept() {
		return reciept;
	}
	public void setReciept(byte[] reciept) {
		this.reciept = reciept;
	}
	public String getReimId() {
		return reimId;
	}
	public void setReimId(String reimId) {
		this.reimId = reimId;
	}
	@Override
	public String toString() {
		return "Reciept [id=" + id + ", reciept=" + reciept + ", reimId=" + reimId + "]";
	}
	public Reciept(String id, byte[] reciept, String reimId) {
		super();
		this.id = id;
		this.reciept = reciept;
		this.reimId = reimId;
	}
	public Reciept() {
		super();
		// TODO Auto-generated constructor stub
	}

		
	
}
