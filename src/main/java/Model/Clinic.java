package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Clinic {
	
	private int id;
	private String name;
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Clinic() {}	
	
	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public ArrayList<Clinic> getList() throws SQLException {
		
		ArrayList<Clinic> list = new ArrayList<>();
		Clinic obj;

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic");
			
			while (rs.next()) {
				obj = new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public boolean addClinic(String name) throws SQLException {
		boolean control = false;
		
		String query = "INSERT INTO clinic" + "(name) VALUES" + "(?)";
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate(); control = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	public boolean deleteClinic(int id) throws SQLException {
		boolean control = false;
		
		String query = "DELETE FROM clinic WHERE id = ?";
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
	
	public boolean updateClinic(int id, String name) throws SQLException {
		boolean control = false;
		
		String query = "UPDATE clinic SET name=? WHERE id=?";
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate(); control = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
		
		ArrayList<User> list = new ArrayList<>();
		User obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT k.id,k.tcno,k.type,k.password,k.name FROM worker w LEFT JOIN kullanici k ON w.user_id = k.id WHERE clinic_id = " + clinic_id);
			
			while (rs.next()) {
				obj = new User(rs.getInt("k.id"), rs.getString("k.tcno"), rs.getString("k.name"), rs.getString("k.password"), rs.getString("k.type") );
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public Clinic getFech(int id) {
		Clinic c = new Clinic();
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id=" + id);
			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
