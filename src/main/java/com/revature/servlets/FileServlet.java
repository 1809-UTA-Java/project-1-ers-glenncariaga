package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.revature.models.Reciept;
import com.revature.util.HibernateUtil;
import org.apache.commons.io.IOUtils;

@WebServlet("/file/*")
@MultipartConfig(maxFileSize = 12167584)
public class FileServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		
		InputStream inputStream = null;
		Part filePart = request.getPart("reciept");
		Reciept reciept = new Reciept();
		
		if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
		
		reciept.setReimId(request.getParameter("reimId"));
		
		try {
			System.out.println("inside the try block");
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			byte[] picture = IOUtils.toByteArray(inputStream);
			
			reciept.setReciept(picture);
			reciept.setId("1234");
			session.save(reciept);
			tx.commit();

			session.close();
			inputStream.close();
		}catch(Exception ex) {
			ex.getStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
		String id = request.getPathInfo();
		System.out.println("file server id:" +id);
		if(id == null) {
			response.setStatus(404);
			response.sendError(404);
			return;
		}
		
		id = id.substring(1,id.length());
		
		List<Reciept> reciepts = new ArrayList<>();
		Session session = HibernateUtil.getSession();
		
		reciepts = session.createQuery(
				"from Reciept where reimId =:idVar"
				).setString("idVar", id).list();
		if(!reciepts.isEmpty()) {
			byte[] picture = reciepts.get(0).getReciept();
			response.setContentType("image/png");
			IOUtils.write(picture, response.getOutputStream());
		}
		
		session.close();
		
	}
}
