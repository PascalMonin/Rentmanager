package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

@Service
public class ReservationService {
	
	private ReservationDao reservationDao;
	public static ReservationService instance;

	private ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	public List<Reservation> findAll() throws ServiceException {
		try {
			return this.reservationDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long create(Reservation reservation) throws ServiceException {
		try {
			return this.reservationDao.create(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void update(Reservation reservation) throws ServiceException {
		try {
			this.reservationDao.update(reservation);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	public int count() throws ServiceException {
		try {
			return this.reservationDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long delete(int id) throws ServiceException {
		try {
			return this.reservationDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Reservation> findByClientId(int id) throws ServiceException {
		try {
			return this.reservationDao.findResaByClientId(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Reservation> findByVehiculeId(int id) throws ServiceException {
		try {
			return this.reservationDao.findResaByVehicleId(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Reservation findById(int id) throws ServiceException {
		try {
			return this.reservationDao.findResaById(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
}
