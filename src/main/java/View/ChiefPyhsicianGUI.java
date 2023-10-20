package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel; 

import Helper.Helper;
import Helper.Item;
import Model.ChiefPhysician;
import Model.Clinic;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class ChiefPyhsicianGUI extends JFrame {

	static ChiefPhysician bashekim = new ChiefPhysician();
	static Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTCno;
	private JTextField fld_dID;
	private JPasswordField fld_dPass;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doktorData = null;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JComboBox select_doctor;
	private JPopupMenu clinicMenu;
	private JTable table_worker;
	private ImageIcon appIcon;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChiefPyhsicianGUI frame = new ChiefPyhsicianGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ChiefPyhsicianGUI(ChiefPhysician ChiefPhysician) throws SQLException {
		
		final ChiefPhysician chiefPhysician = ChiefPhysician;
		
		//DoktorModel
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Name";
		colDoctorName[2] = "ID No";
		colDoctorName[3] = "Password";
		doctorModel.setColumnIdentifiers(colDoctorName);
		
		doktorData = new Object[4];
		for(int i=0; i<chiefPhysician.getDoctorList().size(); i++) {
			doktorData[0] = chiefPhysician.getDoctorList().get(i).getId();
			doktorData[1] = chiefPhysician.getDoctorList().get(i).getName();
			doktorData[2] = chiefPhysician.getDoctorList().get(i).getTcno();
			doktorData[3] = chiefPhysician.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doktorData);
		}
		
		//ClinicModel
		clinicModel = new DefaultTableModel();
		Object[] colClinicName = new Object[2];
		colClinicName[0] = "ID";
		colClinicName[1] = "Name";
		clinicModel.setColumnIdentifiers(colClinicName);
		
		clinicData = new Object[2];
		for(int i=0; i<clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		//WorkerModel
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Name";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];
		
		setTitle("Hospital System");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(210, 225, 238));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		appIcon = new ImageIcon(LoginGUI.class.getResource("/images/hospitalIcon.png"));
		setIconImage(appIcon.getImage());
		
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_info = new JLabel("Welcome, Dear Chief " + ChiefPhysician.getName() );
		lbl_info.setBounds(10, 11, 258, 34);
		lbl_info.setFont(new Font("Tahoma", Font.BOLD, 14));
		w_pane.add(lbl_info);
		
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
		w_pane.add(btn_exit);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setFont(new Font("Tahoma", Font.BOLD, 12));
		w_tab.setBounds(20, 56, 704, 394);
		w_pane.add(w_tab);
		
		JPanel w_doktor = new JPanel();
		w_doktor.setBackground(new Color(255, 255, 255));
		w_tab.addTab("Doctor Management", null, w_doktor, null);
		w_doktor.setLayout(null);
		
		JLabel lbl_name = new JLabel("Name:");
		lbl_name.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_name.setBounds(566, 11, 123, 25);
		w_doktor.add(lbl_name);
		
		fld_dName = new JTextField();
		fld_dName.setBounds(566, 38, 123, 25);
		w_doktor.add(fld_dName);
		fld_dName.setColumns(10);
		
		JLabel lbl_tc = new JLabel("ID No:");
		lbl_tc.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_tc.setBounds(566, 80, 123, 25);
		w_doktor.add(lbl_tc);
		
		fld_dTCno = new JTextField();
		fld_dTCno.setColumns(10);
		fld_dTCno.setBounds(566, 107, 123, 25);
		w_doktor.add(fld_dTCno);
		
		JLabel lbl_pass = new JLabel("Password:");
		lbl_pass.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_pass.setBounds(566, 150, 123, 25);
		w_doktor.add(lbl_pass);
		
		JButton btn_ekle = new JButton("Add");
		btn_ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( fld_dName.getText().length() == 0 || fld_dTCno.getText().length() == 0 ||  Helper.getPass(fld_dPass.getPassword()).length() == 0 ) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = chiefPhysician.addDoctor(fld_dTCno.getText(), new String(fld_dPass.getPassword()), fld_dName.getText() );
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null); fld_dTCno.setText(null); fld_dPass.setText(null); fld_dID.setText(null);
							updateDoctorModel();
							select_doctor.addItem( new Item( chiefPhysician.getDoctorList().get(chiefPhysician.getDoctorList().size() - 1).getId(), chiefPhysician.getDoctorList().get(chiefPhysician.getDoctorList().size() - 1).getName()) );
						}
					} catch ( Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		
		btn_ekle.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_ekle.setBounds(566, 210, 123, 25);
		w_doktor.add(btn_ekle);
		
		JLabel lbl_id = new JLabel("User ID:");
		lbl_id.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_id.setBounds(566, 270, 123, 25);
		w_doktor.add(lbl_id);
		
		fld_dID = new JTextField();
		fld_dID.setColumns(10);
		fld_dID.setBounds(566, 297, 123, 25);
		w_doktor.add(fld_dID);
		
		JButton btn_sil = new JButton("Delete");
		btn_sil.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_sil.setBounds(566, 331, 123, 23);
		btn_sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dID.getText().length() == 0) {
					Helper.showMsg("Please select a valid doctor.");

				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_dID.getText());
						try {
							boolean control = chiefPhysician.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("succes");
								fld_dID.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}

				}
			}
		});
		w_doktor.add(btn_sil);
		
		fld_dPass = new JPasswordField();
		fld_dPass.setBounds(566, 175, 123, 25);
		w_doktor.add(fld_dPass);
		
		JScrollPane w_scrollDoktor = new JScrollPane();
		w_scrollDoktor.setBounds(10, 11, 546, 343);
		w_doktor.add(w_scrollDoktor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoktor.setViewportView(table_doctor);
		
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_dID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}
			}
		});
		
		table_doctor.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE ) {
					int selectID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString() );
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTCno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
					
					try {
						chiefPhysician.updateDoctor(selectID, selectTCno,  selectPass, selectName);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		w_tab.addTab("Clinic Management", null, w_clinic, null);
		w_clinic.setLayout(null);
		
		JScrollPane w_scroll_clinic = new JScrollPane();
		w_scroll_clinic.setBounds(10, 16, 255, 343);
		w_clinic.add(w_scroll_clinic);
		
		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Update");
		JMenuItem deleteMenu = new JMenuItem("Delete");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
		
		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFech(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		
		deleteMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();

						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		}); 
		
		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
			
		});
		w_scroll_clinic.setViewportView(table_clinic);
		
		
		table_clinic.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE ) {
					int selectID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString() );
					String selectName = table_clinic.getValueAt(table_clinic.getSelectedRow(), 1).toString();
					
					try {
						clinic.updateClinic(selectID, selectName);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		
		JLabel lblNewLabel_1_3 = new JLabel("Clinic Name:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3.setBounds(273, 11, 123, 25);
		w_clinic.add(lblNewLabel_1_3);
		
		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(273, 38, 155, 25);
		w_clinic.add(fld_clinicName);
		
		JButton btn_addClinic = new JButton("Add");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( fld_clinicName.getText().length() == 0 ) {
					Helper.showMsg("fill");
				} else {
					try {
						if ( clinic.addClinic(fld_clinicName.getText()) ) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e4) {
						e4.printStackTrace();
					}
					
				}
			}
		});
		btn_addClinic.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_addClinic.setBounds(273, 67, 155, 32);
		w_clinic.add(btn_addClinic);
		
		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(438, 16, 255, 343);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);
		
		select_doctor = new JComboBox();
		for(int i=0; i<chiefPhysician.getDoctorList().size(); i++) {
			select_doctor.addItem(new Item(chiefPhysician.getDoctorList().get(i).getId(), chiefPhysician.getDoctorList().get(i).getName()));
		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());
		});
		select_doctor.setBounds(275, 279, 153, 32);
		w_clinic.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Add");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					int selClinicID = Integer.parseInt(table_clinic.getModel().getValueAt(selRow, 0).toString());
					Item doctorItem = (Item) select_doctor.getSelectedItem();
					try {
						boolean control = chiefPhysician.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Please select a clinic!");
				}
			}
		});
		btn_addWorker.setBounds(275, 322, 153, 32);
		w_clinic.add(btn_addWorker);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Clinic Name:");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3_1.setBounds(275, 147, 123, 25);
		w_clinic.add(lblNewLabel_1_3_1);
		
		JButton btn_workerSelect = new JButton("Select");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					int selClinicID = (int) table_clinic.getModel().getValueAt(selRow, 0);
					try {
						Helper.showMsg("success");
						DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
						clearModel.setRowCount(0);
						for (int i = 0; i < chiefPhysician.getClinicDoctorList(selClinicID).size(); i++) {
							workerData[0] = chiefPhysician.getClinicDoctorList(selClinicID).get(i).getId();
							workerData[1] = chiefPhysician.getClinicDoctorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);
						}
						table_worker.setModel(workerModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
				} else {
					Helper.showMsg("Please, select a clinic!");
				}
			}
		});
		btn_workerSelect.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_workerSelect.setBounds(275, 172, 155, 32);
		w_clinic.add(btn_workerSelect);
	}
	
	
	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for(int i=0; i<bashekim.getDoctorList().size(); i++) {
			doktorData[0] = bashekim.getDoctorList().get(i).getId();
			doktorData[1] = bashekim.getDoctorList().get(i).getName();
			doktorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doktorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doktorData);
		}
	}
	
	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for(int i=0; i<clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}