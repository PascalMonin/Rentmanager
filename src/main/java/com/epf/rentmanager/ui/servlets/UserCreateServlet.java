package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.config.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/users/create")
public class UserCreateServlet extends HttpServlet {
	
	
	@Autowired
	ClientService clientService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lastName = request.getParameter("last_name");
		String firstName = request.getParameter("first_name");
		String email = request.getParameter("email");
		String birthDateTxt = request.getParameter("birthdate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//Date birthDate = sdf.parse(birthDateTxt);
			LocalDate birthDate =  new java.sql.Date(sdf.parse(birthDateTxt).getTime()).toLocalDate();
			Client client = new Client(1, lastName, firstName, email, birthDate);
			clientService.create(client);
			response.sendRedirect("/rentmanager/users");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request,response);
		}
	}
}
