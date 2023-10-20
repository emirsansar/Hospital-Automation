package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Patient;
import Model.WHour;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Color;

public class PatientGUI extends JFrame {

	private JPanel w_pane;
	private static Patient hasta = new Patient();
	private Clinic clinic = new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private WHour whour = new WHour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();
	private ImageIcon appIcon;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientGUI frame = new PatientGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PatientGUI(Patient hasta) throws SQLException {
		
		final Patient f_hasta = hasta;
		
		//Doctor Model
		doctorModel = new DefaultTableModel();
		Object[] colDoktor = new Object[2];
		colDoktor[0] = "ID";
		colDoktor[1] = "Name";
		doctorModel.setColumnIdentifiers(colDoktor);
		doctorData = new Object[2];
		
		//Whour Model
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		
		//Apppoint Model
		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doctor";
		colAppoint[2] = "Date";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];

		loadAppointments(hasta.getId());
		

		
		setTitle("Hospital Management");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(197, 220, 235));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		appIcon = new ImageIcon(LoginGUI.class.getResource("/images/hospitalIcon.png"));
		setIconImage(appIcon.getImage());

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome, Dear " + f_hasta.getName());
		lblNewLabel.setBounds(10, 11, 258, 34);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		w_pane.add(lblNewLabel);
		
		JButton btn_exit = new JButton("EXIT");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_exit.setBounds(635, 19, 89, 23);
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 11));
		w_pane.add(btn_exit);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 56, 704, 394);
		w_tab.setFont(new Font("Tahoma", Font.BOLD, 12));
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Book Appointment", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 33, 260, 321);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable();
		table_doctor.setModel(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		table_doctor.getColumnModel().getColumn(0).setPreferredWidth(5);
		
		JLabel lblNewLabel_1 = new JLabel("Doctor List:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 8, 100, 25);
		w_appointment.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_3 = new JLabel("Clinic Name:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3.setBounds(276, 33, 123, 25);
		w_appointment.add(lblNewLabel_1_3);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setFont(new Font("Tahoma", Font.BOLD, 12));
		select_clinic.setBounds(275, 57, 148, 30);
		select_clinic.addItem("--Select Clinic--");
		for(int i=0; i<clinic.getList().size(); i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});;
		w_appointment.add(select_clinic);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Select Doctor:");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3_1.setBounds(275, 125, 123, 25);
		w_appointment.add(lblNewLabel_1_3_1);
		
		JButton btn_selDoctor = new JButton("Select");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row>=0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					
					try {
						for (int i=0; i<whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWork_date();
							whourModel.addRow(whourData);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					table_whour.setModel(whourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
				} else {
					Helper.showMsg("Lütfen bir doktor seçiniz!");
				}
				
			}
		});
		btn_selDoctor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_selDoctor.setBounds(275, 150, 148, 30);
		w_appointment.add(btn_selDoctor);
		
		JLabel lblNewLabel_1_1 = new JLabel("Available Dates:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(433, 8, 123, 25);
		w_appointment.add(lblNewLabel_1_1);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(428, 33, 260, 321);
		w_appointment.add(w_scrollWhour);
		
		table_whour = new JTable();
		w_scrollWhour.setViewportView(table_whour);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("Appointment:");
		lblNewLabel_1_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3_1_1.setBounds(276, 211, 123, 25);
		w_appointment.add(lblNewLabel_1_3_1_1);
		
		JButton btn_addAppoint = new JButton("Confirm");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if ( selRow>=0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, selectDoctorName, hasta.getId(), hasta.getName(), date);
						if(control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} else {
					Helper.showMsg("error");
				}
			}
		});
		btn_addAppoint.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_addAppoint.setBounds(275, 236, 148, 30);
		w_appointment.add(btn_addAppoint);
		
		
		JPanel w_appoint = new JPanel();
		w_appoint.setBackground(Color.WHITE);
		w_tab.addTab("My Appointments", null, w_appoint, null);
		w_appoint.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 679, 313);
		w_appoint.add(w_scrollAppoint);
		
		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
		
		JButton btn_deleteAppoint = new JButton("Cancel");
		btn_deleteAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_appoint.getSelectedRow();
				if ( selRow>=0) {
					String selDate = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 2);
					String selDoctorName = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 1);
					try {
						boolean control = hasta.deleteAppoint(selDate, selDoctorName);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} else {
					Helper.showMsg("error");
				}
			}
		});
		btn_deleteAppoint.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_deleteAppoint.setBounds(600, 329, 89, 26);
		w_appoint.add(btn_deleteAppoint);
		// table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);
	}
	
	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWork_date();
			whourModel.addRow(whourData);
		}
	}
	
	public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getPatientList(hasta_id).size(); i++) {
			appointData[0] = appoint.getPatientList(hasta_id).get(i).getId();
			appointData[1] = appoint.getPatientList(hasta_id).get(i).getDoctorName();
			appointData[2] = appoint.getPatientList(hasta_id).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
		table_appoint.setModel(appointModel);
	}
	
	public void loadAppointments(int hasta_id) throws SQLException {
	    appointModel.setRowCount(0);

	    for (int i = 0; i < appoint.getPatientList(hasta_id).size(); i++) {
	        appointData[0] = appoint.getPatientList(hasta_id).get(i).getId();
	        appointData[1] = appoint.getPatientList(hasta_id).get(i).getDoctorName();
	        appointData[2] = appoint.getPatientList(hasta_id).get(i).getAppDate();
	        appointModel.addRow(appointData);
	    }
	}
}
