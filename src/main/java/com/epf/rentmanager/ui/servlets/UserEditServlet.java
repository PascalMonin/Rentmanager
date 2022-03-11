package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

@WebServlet("/users/edit")
public class UserEditServlet extends HttpServlet {
	
	private int id;
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
		
		id = Integer.valueOf(request.getQueryString().substring(3));
		
		Client client = new Client();
		
		try {
			client = clientService.findById(id);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("id", id);
		request.setAttribute("first_name", client.getFirstname());
		request.setAttribute("last_name", client.getLastname());
		request.setAttribute("email", client.getEmail());
		request.setAttribute("birthdate", client.getBirthDate());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lastName = request.getParameter("last_name");
		String firstName = request.getParameter("first_name");
		String email = request.getParameter("email");
		String birthDateTxt = request.getParameter("birthdate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			LocalDate birthDate =  new java.sql.Date(sdf.parse(birthDateTxt).getTime()).toLocalDate();
			Client client = new Client(id, lastName, firstName, email, birthDate);
			clientService.update(client);
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
