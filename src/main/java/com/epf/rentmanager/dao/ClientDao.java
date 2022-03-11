package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;


@Repository
public class ClientDao {

	private static ClientDao instance = null;

	private ClientDao() {
	}

//	public static ClientDao getInstance() {
//		if (instance == null) {
//			instance = new ClientDao();
//		}
//		return instance;
//	}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(*) FROM Client";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id= ?;";
	
	public long create(Client client) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_CLIENT_QUERY);

			pstmt.setString(1, client.getLastname());
			pstmt.setString(2, client.getFirstname());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthDate()));
			pstmt.execute();
			
			pstmt.close();
			conn.close();
			return 0;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Client client) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY);
			
			pstmt.setInt(1, client.getId());
			pstmt.execute();
			
			pstmt.close();
			conn.close();
			return 0;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public long delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY);
			
			pstmt.setInt(1, id);
			pstmt.execute();

			pstmt.close();
			conn.close();
			return 0;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Optional<Client> findById(int id) throws DaoException {

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_QUERY);

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			rs.next();

			String clientLastName = rs.getString("nom");
			String clientFirstName = rs.getString("prenom");
			String clientEmail = rs.getString("email");
			LocalDate clientBirthdate = rs.getDate("naissance").toLocalDate();

			Client client = new Client(id, clientLastName, clientFirstName, clientEmail, clientBirthdate);

			pstmt.close();
			conn.close();
			return Optional.of(client);

		} catch (SQLException e) {
			throw new DaoException();
		}

	}

	public List<Client> findAll() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENTS_QUERY);

			ResultSet rs = pstmt.executeQuery();
			List<Client> liste = new ArrayList<>();
			int clientId;
			String clientLastName;
			String clientFirstName;
			String clientEmail;
			LocalDate clientBirthdate;

			while (rs.next()) {
				clientLastName = rs.getString("nom");
				clientFirstName = rs.getString("prenom");
				clientEmail = rs.getString("email");
				clientBirthdate = rs.getDate("naissance").toLocalDate();
				clientId = rs.getInt("id");

				Client client = new Client(clientId, clientLastName, clientFirstName, clientEmail, clientBirthdate);
				liste.add(client);
			}
			pstmt.close();
			conn.close();
			return liste;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int count() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			pstmt.close();
			conn.close();
			return count;
		} catch (SQLException e) {
			throw new DaoException();
		}
		
	}
	
	public void update(Client client) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_CLIENT_QUERY);
			
			pstmt.setString(1, client.getLastname());
			pstmt.setString(2, client.getFirstname());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getBirthDate()));
			pstmt.setInt(5, client.getId());
			
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
