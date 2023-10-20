package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChiefPhysician extends User {
	
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public ChiefPhysician(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}
	
	public ChiefPhysician() {}
	
	
	public ArrayList<User> getDoctorList() throws SQLException {
		
		ArrayList<User> list = new ArrayList<>();
		User obj;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM kullanici WHERE TYPE = 'doktor'");
			
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"), rs.getString("type") );
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	public boolean addDoctor(String tcno, String password, String name) throws SQLException {
		boolean control = false;
		
		String query = "INSERT INTO kullanici" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "doktor");
			preparedStatement.executeUpdate(); control = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	public boolean deleteDoctor(int id) throws SQLException {
		boolean control = false;
		
		String query = "DELETE FROM kullanici WHERE id = ?";
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
	
	public boolean updateDoctor(int id, String tcno, String password, String name) throws SQLException {
		boolean control = false;
		
		String query = "UPDATE kullanici SET name=?, tcno=?, password=? WHERE id=?";
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, tcno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate(); control = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return control;
	}
	
	public boolean addWorker(int user_id, int clinic_id) throws SQLException {
		boolean control = false, duplicate = false;
		
		String query = "INSERT INTO `hastane`.`worker`" + "(user_id,clinic_id) VALUES" + "(?,?)";
		
		rs = st.executeQuery("SELECT * FROM `hastane`.`worker` WHERE user_id = '" + user_id + "' AND clinic_id = '" + clinic_id + "'")  ;
		while(rs.next()) {
			duplicate=true;
		}
		
		if(!duplicate) {
			try {
				st = con.createStatement();
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, user_id);
				preparedStatement.setInt(2, clinic_id);
				preparedStatement.executeUpdate(); control = true;
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
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
}
