package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
//	private VehicleService() {
//		this.vehicleDao = VehicleDao.getInstance();
//	}
	
	private VehicleService(VehicleDao VehicleDao){
		this.vehicleDao = VehicleDao;
	}
	
//	public static VehicleService getInstance() {
//		if (instance == null) {
//			instance = new VehicleService();
//		}
//		
//		return instance;
//	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void update(Vehicle vehicle) throws ServiceException {
		try {
			this.vehicleDao.update(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public long delete(int id) throws ServiceException {
		try {
			return this.vehicleDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Vehicle findById(int id) throws ServiceException {
		try {
			return this.vehicleDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int count() throws ServiceException {
		try {
			return this.vehicleDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
