package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Patient extends User {

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Patient() {
	}

	public Patient(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}

	public boolean register(String tcno, String password, String name) throws SQLException {
		boolean control = false;
		int count = 0;

		String query = "INSERT INTO `hastane`.`kullanici` (tcno,password,name) VALUES (?,?,?)";

		try {
			st = con.createStatement();
			boolean duplicate = false;

			rs = st.executeQuery("SELECT * FROM `hastane`.`kullanici` WHERE tcno = '" + tcno + "'");
			while (rs.next()) {
				duplicate = true;
			}

			if (!duplicate) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, "hasta");
				preparedStatement.executeUpdate();
				control = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}

	public boolean addAppointment(int doctor_id, String doctor_name, int hasta_id,  String hasta_name, String appDate) throws SQLException {
		boolean control = false;

		String query = "INSERT INTO `hastane`.`appointment` (doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES (?,?,?,?,?)";

		try {
			st = con.createStatement();

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setInt(3, hasta_id);
			preparedStatement.setString(4, hasta_name);
			preparedStatement.setString(5, appDate);
			preparedStatement.executeUpdate();
			control = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	public boolean updateWhourStatus(int doctor_id, String wDate) throws SQLException {
		boolean control = false;

		String query = "UPDATE `hastane`.`work_hour` SET status = ? WHERE doctor_id = ? AND work_date = ?";

		try {
			st = con.createStatement();

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctor_id);
			preparedStatement.setString(3, wDate);
			preparedStatement.executeUpdate();
			control = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	public boolean deleteAppoint(String date, String name) {
		boolean control = false;
		
		try {
			st = con.createStatement();
			String query1 = "DELETE FROM `hastane`.`appointment` WHERE app_date='" + date + "'";
			String query2 = "UPDATE `hastane`.`work_hour` SET status='a' WHERE doctor_name='" + name + "' AND work_date='" + date + "' ";

			preparedStatement = con.prepareStatement(query1);
			preparedStatement.executeUpdate();

			preparedStatement = con.prepareStatement(query2);
			preparedStatement.executeUpdate();
			
			control = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return control;
	}
}
