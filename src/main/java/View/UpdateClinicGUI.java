package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fld_clinicName;
	private ImageIcon appIcon;
	private static Clinic clinic = new Clinic();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UpdateClinicGUI(Clinic Clinic) {
		setTitle("Edit Clinic");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 204, 150);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(197, 220, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		appIcon = new ImageIcon(LoginGUI.class.getResource("/images/hospitalIcon.png"));
		setIconImage(appIcon.getImage());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_namePolicinic = new JLabel("Clinic Name:");
		lbl_namePolicinic.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_namePolicinic.setBounds(10, 11, 123, 25);
		contentPane.add(lbl_namePolicinic);
		
		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(10, 38, 168, 25);
		fld_clinicName.setText(Clinic.getName());
		contentPane.add(fld_clinicName);
		
		final int cilID = Clinic.getId();
		JButton btn_updateClinic = new JButton("Update");
		btn_updateClinic.setFont(new Font("Tahoma", Font.BOLD, 11));
		btn_updateClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					try {
						clinic.updateClinic(cilID, fld_clinicName.getText());
						Helper.showMsg("success");
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updateClinic.setBounds(10, 74, 168, 26);
		contentPane.add(btn_updateClinic);
	}

}
