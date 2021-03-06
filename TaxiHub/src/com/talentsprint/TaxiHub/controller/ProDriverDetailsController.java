package com.talentsprint.TaxiHub.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talentsprint.TaxiHub.dao.DriverDetailsBasicDAO;
import com.talentsprint.TaxiHub.dao.DriverDetailsProDAO;

@WebServlet("/ProDriverDetailsController")
public class ProDriverDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProDriverDetailsController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = "available";
		PrintWriter out = response.getWriter();
		String phone_num = (String) request.getSession(false).getAttribute("number");
		String source = request.getParameter("source");
		String destination = request.getParameter("destination");
		DriverDetailsProDAO dedao = new DriverDetailsProDAO();
		try {
			//String email = dedao.getDriverEmailBasic(status);
			String driver = dedao.retrieveCabDriver(status);
			String phone = dedao.retrieveDriverPhone(status);
			String cabNumber = dedao.retrieveCabNumber(status);
			int did = dedao.retrieveDriverId(status);
			dedao.insertIntoBookings(phone_num,did,cabNumber,source,destination);
			//String cabName = dedao.retrieveCabName(status);
			//System.out.println(email);
			//request.setAttribute("email", email);
			request.setAttribute("driver", driver);
			request.setAttribute("phone", phone);
			request.setAttribute("cabNumber", cabNumber);
			//request.setAttribute("cabName", cabName);
			RequestDispatcher rd = request.getRequestDispatcher("pro.jsp");
		    rd.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
