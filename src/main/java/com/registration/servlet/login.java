package com.registration.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class login
 */
@WebServlet("/Login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail = request.getParameter("username");
		String upwd = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null; 
		Connection con =null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","root", "");
			 PreparedStatement ps = con.prepareStatement("select *from newU WHERE uemail = ? AND upwd= ?");
			 ps.setString(1,uemail);
			 ps.setString(2, upwd);
			 
			 ResultSet rs =ps.executeQuery();
			 if(rs.next()){
				 session.setAttribute("name" , rs.getString("uname"));
				 dispatcher = request.getRequestDispatcher("index.jsp");
			 }else {
				 request.setAttribute("status", "failed");
				 dispatcher = request.getRequestDispatcher("login.jsp");
			 }
			 dispatcher.forward(request,response);
		
		}catch(Exception e) {
		     e.getStackTrace();
		}
	}

}
