package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
//	public static ReservationDao getInstance() {
//		if(instance == null) {
//			instance = new ReservationDao();
//		}
//		return instance;
//	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(*) FROM Reservation";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?;";
	
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_RESERVATION_QUERY);

			pstmt.setInt(1, reservation.getIdClient());
			pstmt.setInt(2, reservation.getIdVehicle());
			pstmt.setDate(3, Date.valueOf(reservation.getDateStart()));
			pstmt.setDate(4, Date.valueOf(reservation.getDateEnd()));
			pstmt.execute();
			
			return 0;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY);
			
			pstmt.setInt(1, reservation.getId());
			pstmt.execute();
			return 0;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	
	public long delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY);
			
			pstmt.setInt(1, id);
			pstmt.execute();
			return 0;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		List<Reservation> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			ps.setInt(1, clientId);
			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt("id"));
				reservation.setIdClient(clientId);
				reservation.setIdVehicle(resultSet.getInt("vehicle_id"));
				reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
				reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());
				result.add(reservation);
			}
			resultSet.close();
			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}

		return result;
	}
	
	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {
		List<Reservation> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			ps.setInt(1, vehicleId);
			ps.execute();

			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt("id"));
				reservation.setIdClient(resultSet.getInt("client_id"));
				reservation.setIdVehicle(vehicleId);
				reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
				reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());
				result.add(reservation);
			}
			resultSet.close();
			ps.close();
			connection.close();
			
		} catch (SQLException e) {
			throw new DaoException();
		}
		return result;
	}
	
	public Reservation findResaById(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_QUERY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			rs.next();
			
			Reservation reservation = new Reservation();
			reservation.setId(rs.getInt(id));
			reservation.setIdClient(rs.getInt("client_id"));
			reservation.setIdVehicle(rs.getInt("vehicle_id"));
			reservation.setDateStart(rs.getDate("debut").toLocalDate());
			reservation.setDateEnd(rs.getDate("fin").toLocalDate());
			
			ps.close();
			connection.close();
			return reservation;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> result = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Reservation reservation = new Reservation();
				reservation.setId(resultSet.getInt("id"));
				reservation.setIdClient(resultSet.getInt("client_id"));
				reservation.setIdVehicle(resultSet.getInt("vehicle_id"));
				reservation.setDateStart(resultSet.getDate("debut").toLocalDate());
				reservation.setDateEnd(resultSet.getDate("fin").toLocalDate());
				result.add(reservation);
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}
		return result;
	}
	
	public int count() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_RESERVATIONS_QUERY);
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
	
	public void update(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
			ps.setInt(1, reservation.getIdClient());
			ps.setInt(2, reservation.getIdVehicle());
			ps.setDate(3, Date.valueOf(reservation.getDateStart()));
			ps.setDate(4, Date.valueOf(reservation.getDateEnd()));
			ps.setInt(5, reservation.getId());

			ps.execute();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
