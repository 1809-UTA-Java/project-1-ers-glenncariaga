package com.revature.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import javax.imageio.ImageIO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.BlobProxy;

import com.revature.models.Reciept;
import com.revature.util.HibernateUtil;

@WebServlet("/file")
@MultipartConfig(maxFileSize = 1216584)
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
		System.out.println(reciept.toString());
		try {
			System.out.println("inside the try block");
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "PNG", byteOutStream);
			reciept.setReciept(new SerialBlob(byteOutStream.toByteArray()));
			reciept.setId("1234");
//			System.out.println(reciept.toString());	
			session.save(reciept);
			tx.commit();
			System.out.println(reciept.toString());	
			
		}catch(Exception ex) {
			ex.getStackTrace();
		}
	}
}
