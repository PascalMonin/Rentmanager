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
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
//	public static VehicleDao getInstance() {
//		if(instance == null) {
//			instance = new VehicleDao();
//		}
//		return instance;
//	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, nb_places = ? WHERE id = ?;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_VEHICLE_QUERY);

			pstmt.setString(1, vehicle.getConstructor());
			pstmt.setInt(2, vehicle.getNumPlace());
			pstmt.execute();

			pstmt.close();
			conn.close();
			return 0;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY);
			
			pstmt.setInt(1, vehicle.getId());
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
			PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY);
			
			pstmt.setInt(1, id);
			pstmt.execute();

			pstmt.close();
			conn.close();
			return 0;

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Optional<Vehicle> findById(int id) throws DaoException {

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_QUERY);
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			String vehicleConstructor = rs.getString("constructeur");
			int vehicleNumPlace = rs.getInt("nb_places");
			
			//Vehicle vehicle = new Vehicle(id, vehicleConstructor, vehicleModel, vehicleNumPlace);
			Vehicle vehicle = new Vehicle(id, vehicleConstructor, vehicleNumPlace);

			pstmt.close();
			conn.close();
			return Optional.of(vehicle);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	public List<Vehicle> findAll() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLES_QUERY);

			ResultSet rs = pstmt.executeQuery();
			List<Vehicle> liste = new ArrayList<>();
			int vehicleId;
			String vehicleConstructor;
			int vehicleNumPlace;
			while (rs.next()) {
				vehicleConstructor = rs.getString("constructeur");
				vehicleNumPlace = rs.getInt("nb_places");
				vehicleId = rs.getInt("id");
				
				Vehicle vehicle = new Vehicle(vehicleId, vehicleConstructor, vehicleNumPlace);
				liste.add(vehicle);
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
			PreparedStatement pstmt = conn.prepareStatement(COUNT_VEHICLES_QUERY);
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
	
	public void update(Vehicle vehicle) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_VEHICLE_QUERY);
			
			pstmt.setString(1, vehicle.getConstructor());
			pstmt.setInt(2, vehicle.getNumPlace());
			pstmt.setInt(3, vehicle.getId());
			pstmt.execute();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
