package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Appointment {
	
	private int id, doctorID, hastaID;
	private String doctorName, hastaName, appDate;
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Appointment(int id, int doctorID, int hastaID, String doctorName, String hastaName, String appDate) {
		this.id = id;
		this.doctorID = doctorID;
		this.hastaID = hastaID;
		this.doctorName = doctorName;
		this.hastaName = hastaName;
		this.appDate = appDate;
	}
	
	public ArrayList<Appointment> getPatientList(int hasta_id) throws SQLException {
		
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM `hastane`.`appointment` WHERE hasta_id =" + hasta_id );
			
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("doctor_name"));
				obj.setAppDate(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public ArrayList<Appointment> getDoctorList(int doctor_id) throws SQLException {
		
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM `hastane`.`appointment` WHERE hasta_id =" + doctor_id );
			
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("doctor_name"));
				obj.setAppDate(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public Appointment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getHastaID() {
		return hastaID;
	}

	public void setHastaID(int hastaID) {
		this.hastaID = hastaID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHastaName() {
		return hastaName;
	}

	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	
	
}
