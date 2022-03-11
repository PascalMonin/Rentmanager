package com.epf.rentmanager.ui.servlets;

import java.io.IOException;

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
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/cars/create")
public class CarCreateServlet extends HttpServlet {
	
	
	@Autowired
	VehicleService vehicleService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.getWriter().print(Integer.valueOf(request.getParameter("seats")));
		String constructor = request.getParameter("manufacturer");
		int seats = Integer.valueOf(request.getParameter("seats"));
		Vehicle vehicle = new Vehicle(1, constructor, seats);
		try {
			vehicleService.create(vehicle);
			response.sendRedirect("/rentmanager/cars");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request,response);
		}
	}
}
