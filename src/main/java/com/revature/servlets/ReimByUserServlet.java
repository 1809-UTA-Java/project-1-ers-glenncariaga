package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.repository.ReimDao;

@WebServlet("/reimbyuser/*")
public class ReimByUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ReimDao dao=new ReimDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo();
		ObjectMapper om = new ObjectMapper();
		String obj;
		id = id.substring(1, id.length());
		System.out.println("reimbyuser id: "+id);
		obj = om.writeValueAsString(dao.getReimByUser(id));

		response.setContentType("application/json");

		PrintWriter pw = response.getWriter();
		pw.print(obj);
		pw.close();
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
