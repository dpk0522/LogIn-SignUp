package com.registration.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class registrationServlet
 */
@WebServlet("/register")
public class registrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		  String uname = request.getParameter("name");
		  String uemail = request.getParameter("email");
		  String upass = request.getParameter("pass");
		  String ucontact = request.getParameter("contact");
		  
		  if(upass!=request.getParameter("re_pass")){
	         
        //			  request.getSession().setAttribute("msg", "YOUR PASSWORD AND CONFIRM PASSWORD IS NOT SAME");
	          response.sendRedirect("registration.jsp");
	      }else {
	
		  RequestDispatcher dispacher = null;
		  Connection con =null;
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","root", "");
			 PreparedStatement ps = con.prepareStatement("insert into newU(ucontact,uname,upwd,uemail) values(?,?,?,?)");
			 ps.setString(2,uname);
			 ps.setString(3, upass);
			 ps.setString(4, uemail);
			 ps.setString(1, ucontact);
			 
			 int rowcount = ps.executeUpdate();
			 dispacher = request.getRequestDispatcher("registration.jsp");
			 
			 if(rowcount > 0) {
				 request.setAttribute("status", "success");
			 }else {
				 request.setAttribute("status", "failed");
			 }
			 
			 dispacher.forward(request, response);
		
		 }catch(Exception e){
		
			 e.getStackTrace();
		 }finally {
			 try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	}
		 
}
