package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.repository.ReimDao;
import com.revature.util.HibernateUtil;

@WebServlet("/reimbursement/*")
public class ReimServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Reimbursement> reims;
	ReimDao dao =new ReimDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReimServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo();
		ObjectMapper om = new ObjectMapper();
		String obj;
		if (id == null) {
			System.out.println("ReimId:" + id);
			reims = dao.getAllReim();
			obj = om.writeValueAsString(reims);
		} else {
			id = id.substring(1, id.length());
			System.out.println("getting by ReimID" + id);
			obj = om.writeValueAsString(dao.getReimById(id));
		}

		response.setContentType("application/json");

		PrintWriter pw = response.getWriter();
		pw.print(obj);
		pw.close();
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		InputStream inputStream = request.getInputStream();
		System.out.println(inputStream.toString());
		Reimbursement postReim = (Reimbursement) om.readValue(inputStream, Reimbursement.class);
		PrintWriter pw = response.getWriter();
		dao.saveUser(postReim);
		pw.write("Write Succeeded");
		pw.close();
		response.setStatus(HttpServletResponse.SC_OK);
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo();
		id = id.substring(1, id.length());
		ObjectMapper om = new ObjectMapper();
		Reimbursement reim = (Reimbursement) om.readValue(request.getInputStream(), Reimbursement.class);
		PrintWriter pw = response.getWriter();
		dao.updateReim(id, reim);
		pw.write("Update Succeeded");
		pw.close();
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
		HibernateUtil.shutdown();
	}
	
}
