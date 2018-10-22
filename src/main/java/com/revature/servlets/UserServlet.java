package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.revature.models.ERS_Users;
import com.revature.repository.UserDao;
import com.revature.util.HibernateUtil;

/**
 * Servlet implementation class UserServelet
 */
@WebServlet("/employee/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<ERS_Users> users;
	UserDao dao = new UserDao();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
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
		System.out.println("pathinfo: " + id);
		if (id == null) {
			System.out.println("USERS:" + id);
			users = dao.getUsers();
			obj = om.writeValueAsString(users);
		} else {
			id = id.substring(1, id.length());
			System.out.println("getting by ID" + id);
			obj = om.writeValueAsString(dao.getUserById(id));
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
		try {
		ObjectMapper om = new ObjectMapper();
		ERS_Users postUser = (ERS_Users) om.readValue(request.getInputStream(), ERS_Users.class);
		PrintWriter pw = response.getWriter();
		dao.saveUser(postUser);
		pw.close();
		response.setStatus(HttpServletResponse.SC_OK);
		}catch(InvalidFormatException ex) {
			
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo();
		id = id.substring(1, id.length());
		ObjectMapper om = new ObjectMapper();
		ERS_Users user = (ERS_Users) om.readValue(request.getInputStream(), ERS_Users.class);
		PrintWriter pw = response.getWriter();
		pw.write("Updated User");
		pw.close();
		dao.updateUser(id, user);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		HibernateUtil.shutdown();
	}
	
	//for Preflight
	  @Override
	  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	      setAccessControlHeaders(req,resp);
	      resp.setStatus(HttpServletResponse.SC_OK);
	  }

	  private void setAccessControlHeaders(HttpServletRequest req,HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
	      resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	      resp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,Accept,Authorization,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
	  }

}
