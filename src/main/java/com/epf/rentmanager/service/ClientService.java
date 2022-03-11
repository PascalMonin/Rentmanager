package com.epf.rentmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;

@Service
public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
//	private ClientService() {
//		this.clientDao = ClientDao.getInstance();
//	}
	
	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}
	
//	public static ClientService getInstance() {
//		if (instance == null) {
//			instance = new ClientService();
//		}
//		
//		return instance;
//	}
	
	
	
	public long create(Client client) throws ServiceException {
		if (!client.isLegal()) {
			throw new ServiceException();
		} else {
			try {
				return this.clientDao.create(client);
			} catch (DaoException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public void update(Client client) throws ServiceException {
		if (!client.isLegal()) {
			throw new ServiceException();
		} else {
			try {
				this.clientDao.update(client);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}
	
	public long delete(Client client) throws ServiceException {
		try {
			return this.clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long delete(int id) throws ServiceException {
		try {
			return this.clientDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Client findById(int id) throws ServiceException {
		try {
			return this.clientDao.findById(id).get();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.findAll();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int count() throws ServiceException {
		try {
			return this.clientDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
