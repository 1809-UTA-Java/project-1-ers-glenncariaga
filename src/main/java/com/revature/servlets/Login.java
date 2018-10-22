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
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.models.ERS_Users;
import com.revature.models.LoginUser;
import com.revature.repository.UserDao;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<ERS_Users> users;
	UserDao dao = new UserDao();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		ObjectMapper om = new ObjectMapper();
		String obj = null;
		
		LoginUser user = om.readValue(request.getInputStream(), LoginUser.class);
		if (user == null) {
			response.setStatus(500);
			return;
		}
		ERS_Users found = dao.getUserByUsername(user.getUsername());
		if(found != null) {
			if(user.getPassword().equals(found.getPassword())) {
				response.setHeader("loggedIn", "true");
				response.setHeader("role", found.getUserRole());
				response.setStatus(HttpServletResponse.SC_OK);
				
				obj = om.writeValueAsString(found);
				
				
			
			}else {
				obj = "Incorrect Password";
				response.setStatus(401);
			}
			}else {
				obj = "Incorrect Username";
				response.setStatus(401);
			}
		
		PrintWriter pw = response.getWriter();
		pw.print(obj);
		pw.close();
		}catch (MismatchedInputException ex){
			System.out.println(ex);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	
	}
}
