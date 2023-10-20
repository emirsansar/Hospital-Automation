package View;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import Helper.DBConnection;
import Helper.Helper;
import Model.ChiefPhysician;
import Model.Doctor;
import Model.Patient;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private ImageIcon hospIcon, appIcon;
	private JLabel lbl_logo;
	private JTabbedPane w_tabPane;
	private JLabel lbl_NoTC;
	private JLabel lbl_patPass;
	private JTextField fld_patientID, fld_doctorID;
	private JPasswordField fld_doctorPass, fld_patientPass;
	private JButton btn_exit;
	private JButton btn_exit2;
	
	private DBConnection conn = new DBConnection();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public LoginGUI() {
		setTitle("Hospital System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 337);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(197, 220, 235));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		appIcon = new ImageIcon(LoginGUI.class.getResource("/images/hospitalIcon.png"));
		setIconImage(appIcon.getImage());

		setContentPane(w_pane);
		
		hospIcon = new ImageIcon( getClass().getResource("/images/hospitalIcon.png") );
		w_pane.setLayout(null);
		
		lbl_logo = new JLabel( hospIcon );
		lbl_logo.setBounds(95, 20, 35, 35);
		w_pane.add(lbl_logo);
		
		JLabel lbl_welcome = new JLabel("Hospital Management System");
		lbl_welcome.setBounds(95, 20, 340, 35);
		lbl_welcome.setFont(new Font("Tahoma", Font.BOLD, 17));
		lbl_welcome.setHorizontalAlignment(SwingConstants.CENTER);
		w_pane.add(lbl_welcome);
		
		w_tabPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabPane.setBounds(10, 69, 464, 216);
		w_tabPane.setFont(new Font("Tahoma", Font.BOLD, 12));
		w_pane.add(w_tabPane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(new Color(255, 255, 255));
		w_tabPane.addTab("User Login", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		lbl_NoTC = new JLabel("ID No:");
		lbl_NoTC.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_NoTC.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_NoTC.setBounds(20, 31, 143, 35);
		w_hastaLogin.add(lbl_NoTC);
		
		lbl_patPass = new JLabel("Password:");
		lbl_patPass.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_patPass.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_patPass.setBounds(20, 74, 122, 35);
		w_hastaLogin.add(lbl_patPass);
		
		fld_patientID = new JTextField();
		fld_patientID.setBounds(174, 28, 261, 34);
		w_hastaLogin.add(fld_patientID);
		fld_patientID.setColumns(10);
		
		JButton btn_hastaKayıt = new JButton("SIGN UP");
		btn_hastaKayıt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
				
			}
		});
		btn_hastaKayıt.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_hastaKayıt.setBounds(174, 130, 120, 35);
		w_hastaLogin.add(btn_hastaKayıt);
		
		JButton btn_hastaGiris = new JButton("LOG IN");
		btn_hastaGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( fld_patientID.getText().isEmpty() || String.valueOf(fld_patientPass.getPassword()).isEmpty() ) {
		            showErrorFldHasta();
		            Helper.showMsg("fill");
		        } else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM kullanici");
						boolean check = false;
						
						while ( rs.next() ) {
							if ( fld_patientID.getText().equals( rs.getString("tcno"))  && Helper.getPass(fld_patientPass.getPassword()).equals( rs.getString("password")) ) {
								if (rs.getString("type").equals("hasta")) {
									Patient hasta = new Patient();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									PatientGUI hGUI = new PatientGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									check = true;
								}
							} 
						}
						
						if (!check) {
							Helper.showMsg("User not found. Please register!");
							showErrorFldHasta();
						}
					}
					catch (SQLException e1){
						e1.printStackTrace();
					}
				}
			}

		});
		btn_hastaGiris.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_hastaGiris.setBounds(315, 130, 120, 35);
		w_hastaLogin.add(btn_hastaGiris);
		
		fld_patientPass = new JPasswordField();
		fld_patientPass.setBounds(174, 74, 261, 35);
		w_hastaLogin.add(fld_patientPass);
		
		btn_exit = new JButton("EXIT");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_exit.setBounds(20, 140, 90, 25);
		w_hastaLogin.add(btn_exit);
		
		JPanel w_doktorLogin = new JPanel();
		w_doktorLogin.setBackground(new Color(255, 255, 255));
		w_tabPane.addTab("Doctor Login", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);
		
		JLabel lblTcNumaranz_1 = new JLabel("ID No:");
		lblTcNumaranz_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTcNumaranz_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTcNumaranz_1.setBounds(20, 31, 140, 35);
		w_doktorLogin.add(lblTcNumaranz_1);
		
		fld_doctorID = new JTextField();
		fld_doctorID.setColumns(10);
		fld_doctorID.setBounds(174, 28, 261, 34);
		w_doktorLogin.add(fld_doctorID);
		
		JLabel lbl_doktorSifre = new JLabel("Password:");
		lbl_doktorSifre.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_doktorSifre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_doktorSifre.setBounds(20, 74, 140, 35);
		w_doktorLogin.add(lbl_doktorSifre);
		
		JButton btn_doktorGiris = new JButton("LOG IN");
		btn_doktorGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if ( fld_doctorID.getText().length() == 0 || Helper.getPass(fld_doctorPass.getPassword()).length() == 0 ) {
					showErrorFldDoctor();
					Helper.showMsg("fill");
				} 
				else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM kullanici");
						
						while ( rs.next() ) {
							if ( fld_doctorID.getText().equals( rs.getString("tcno"))  && Helper.getPass(fld_doctorPass.getPassword()).equals( rs.getString("password")) ) {
								if (rs.getString("type").equals("bashekim")) {
									ChiefPhysician bhekim = new ChiefPhysician();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									ChiefPyhsicianGUI bGUI = new ChiefPyhsicianGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
								}
								if (rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							} else {
								showErrorFldDoctor();
							}
						}
					} catch (SQLException e1){
						e1.printStackTrace();
					}
				}	
			}
		});
		btn_doktorGiris.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_doktorGiris.setBounds(315, 130, 120, 35);
		w_doktorLogin.add(btn_doktorGiris);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(174, 74, 261, 35);
		w_doktorLogin.add(fld_doctorPass);
		
		btn_exit2 = new JButton("EXIT");
		btn_exit2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_exit2.setBounds(20, 140, 90, 25);
		w_doktorLogin.add(btn_exit2);

	}
	
	public void showErrorFldHasta() {
		fld_patientID.setBorder(BorderFactory.createLineBorder(Color.RED)); 
        fld_patientPass.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
	public void showErrorFldDoctor() {
		fld_doctorID.setBorder(BorderFactory.createLineBorder(Color.RED)); 
        fld_doctorPass.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
}
