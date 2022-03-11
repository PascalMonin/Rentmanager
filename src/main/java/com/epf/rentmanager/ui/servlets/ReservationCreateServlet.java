package com.epf.rentmanager.ui.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
	
	
	@Autowired
	ReservationService reservationService;
	@Autowired
	VehicleService vehicleService;
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
		List<Vehicle> vehicles = new ArrayList<>();
        try {
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
        }
        request.setAttribute("vehicles", vehicles);

        List<Client> clients = new ArrayList<>();
        try {
            clients = clientService.findAll();
        } catch (ServiceException e) {
        }
        request.setAttribute("clients", clients);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int clientId = Integer.valueOf(request.getParameter("client"));
		int vehicleId = Integer.valueOf(request.getParameter("car"));
		String dateStartText = request.getParameter("begin");
		String dateEndText = request.getParameter("end");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			LocalDate dateStart =  new java.sql.Date(sdf.parse(dateStartText).getTime()).toLocalDate();
			LocalDate dateEnd =  new java.sql.Date(sdf.parse(dateEndText).getTime()).toLocalDate();
			Reservation reservation = new Reservation(1,clientId,vehicleId,dateStart,dateEnd);
			reservationService.create(reservation);
			response.sendRedirect("/rentmanager/rents");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request,response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request,response);
		}
	}
}
