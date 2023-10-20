package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Patient;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JPasswordField fld_pass;
	private JTextField fld_tcno;
	private Patient hasta = new Patient();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 245, 295);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(197, 220, 235));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_name = new JLabel("Name:");
		lbl_name.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_name.setBounds(10, 11, 123, 25);
		w_pane.add(lbl_name);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 38, 203, 25);
		w_pane.add(fld_name);
		
		JLabel lbl_pass = new JLabel("Password:");
		lbl_pass.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_pass.setBounds(10, 137, 123, 25);
		w_pane.add(lbl_pass);
		
		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 165, 203, 25);
		w_pane.add(fld_pass);
		
		JLabel lbl_tc = new JLabel("ID No:");
		lbl_tc.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbl_tc.setBounds(10, 74, 123, 25);
		w_pane.add(lbl_tc);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 101, 203, 25);
		w_pane.add(fld_tcno);
		
		JButton btn_backto = new JButton("Back");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backto.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_backto.setBounds(10, 211, 89, 34);
		w_pane.add(btn_backto);
		
		JButton btn_register = new JButton("Confirm");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_tcno.getText().length() == 0 || Helper.getPass(fld_pass.getPassword()).length() == 0 || fld_name.getText().length()==0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = hasta.register(fld_tcno.getText(), Helper.getPass(fld_pass.getPassword()), fld_name.getText());
						if (control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_register.setBounds(124, 211, 89, 34);
		w_pane.add(btn_register);
	}
}
