package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class DoctorGUI extends JFrame {

	private JPanel contentPane;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private JDateChooser select_date; 
	private JComboBox select_time;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private ImageIcon appIcon;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		final Doctor f_Doctor = doctor;
		
		setResizable(false);
		setTitle("Hospital Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(197, 220, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Date";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for(int i=0; i< f_Doctor.getWhourList(f_Doctor.getId()).size(); i++) {
			whourData[0] = f_Doctor.getWhourList(f_Doctor.getId()).get(i).getId();
			whourData[1] = f_Doctor.getWhourList(f_Doctor.getId()).get(i).getWork_date();
			whourModel.addRow(whourData);
		}
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_info = new JLabel("Welcome, Dear " + doctor.getName());
		lbl_info.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl_info.setBounds(10, 11, 258, 34);
		contentPane.add(lbl_info);
		
		appIcon = new ImageIcon(LoginGUI.class.getResource("/images/hospitalIcon.png"));
		setIconImage(appIcon.getImage());
		
		JButton btn_exit = new JButton("EXIT");
		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_exit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_exit.setBounds(635, 19, 89, 23);
		contentPane.add(btn_exit);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setFont(new Font("Tahoma", Font.BOLD, 12));
		w_tab.setBounds(10, 53, 704, 394);
		contentPane.add(w_tab);
		
		JPanel w_workHour = new JPanel();
		w_workHour.setBackground(Color.WHITE);
		w_tab.addTab("Working Hours", null, w_workHour, null);
		w_workHour.setLayout(null);
		
		select_date = new JDateChooser();
		select_date.setBounds(10, 11, 130, 20);
		w_workHour.add(select_date);
		
		select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:30", "14:00", "14:30", "15:00", "15:30"}));
		select_time.setBounds(150, 9, 70, 22);
		w_workHour.add(select_time);
		
		JButton btn_addWhour = new JButton("Add");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if (date.length() == 0) {
					Helper.showMsg("Please enter a valid date.");

				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = f_Doctor.addWhour(f_Doctor.getId(), f_Doctor.getName(), selectDate);
						System.out.println(control);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(f_Doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addWhour.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_addWhour.setBounds(230, 9, 89, 22);
		w_workHour.add(btn_addWhour);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(10, 44, 679, 310);
		w_workHour.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btn_deleteWhour = new JButton("Delete");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String selectRow  = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectRow);
					try {
						boolean control = f_Doctor.deleteWhour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(f_Doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				} else {
					Helper.showMsg("Please enter a valid date.");
				}
			}
		});
		btn_deleteWhour.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_deleteWhour.setBounds(600, 9, 89, 23);
		w_workHour.add(btn_deleteWhour);
	}
	
	
	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for(int i=0; i< doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWork_date();
			whourModel.addRow(whourData);
		}
	}
	
}
