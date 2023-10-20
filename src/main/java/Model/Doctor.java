package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User {
	
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Doctor() {
		super();
	}
	public Doctor(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}
	
	
	public boolean addWhour(int doctor_id, String doctor_name, String work_date) throws SQLException {
		boolean control=false;
		int count=0;
		
		String query = "INSERT INTO `hastane`.`work_hour` ( `doctor_id`, `doctor_name`, `work_date`) VALUES ( ?, ?, ?)";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM  `hastane`.`work_hour` WHERE `status`='a' AND `doctor_id` ="+doctor_id+" AND `work_date`='" + work_date + "'");
			
			while(rs.next()) {
				count++; break;
			}
			
			if (count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setString(3, work_date);
				preparedStatement.executeUpdate(); control = true;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	
	public ArrayList<WHour> getWhourList(int doctor_id) throws SQLException {
		
		ArrayList<WHour> list = new ArrayList<>();
		WHour obj;

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM work_hour WHERE status = 'a' and doctor_id = " + doctor_id);
			
			while (rs.next()) {
				obj = new WHour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setWork_date(rs.getString("work_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	
	public boolean deleteWhour(int id) throws SQLException {
		boolean control = false;
		
		String query = "DELETE FROM work_hour WHERE id = ?";
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate(); control = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
}
