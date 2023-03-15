import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class AcpCheck extends JFrame {

	private JPanel contentPane;
	private JTextField u1;
	private JTextField rel;
	private JTextField acp_result;
	private JTextPane txtpnUser;
	private JTextPane txtpnUser_1;
	private JTextPane txtpnRelation;
	private JTextPane txtpnPermissionGrantedOr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AcpCheck frame = new AcpCheck();
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
	public AcpCheck() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		u1 = new JTextField();
		u1.setBounds(139, 47, 86, 20);
		contentPane.add(u1);
		u1.setColumns(10);
		
		JTextField u2 = new JTextField();
		u2.setText("");
		u2.setBounds(139, 90, 86, 20);
		contentPane.add(u2);
		u2.setColumns(10);
		
		rel = new JTextField();
		rel.setBounds(139, 143, 86, 20);
		contentPane.add(rel);
		rel.setColumns(10);
		
			
		acp_result = new JTextField();
		acp_result.setBounds(284, 95, 129, 35);
		contentPane.add(acp_result);
		acp_result.setColumns(10);
		
		JButton btnSubmit = new JButton("submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String user1=u1.getText();
				String user2=u2.getText();
				String relation=rel.getText();
				
				if(relation.equals("friend")){
					acp_result.setText("access to all");
				}
				else {
					if(relation.equals("default")){
					acp_result.setText("access to either N1 or N2 on permission");
					}
					else{
						if(relation.equals("miner"))
						{
						acp_result.setText("access to all");
						}
						else{
						acp_result.setText("access nothing");
						}
					}
				}
				
			}
		});
		btnSubmit.setBounds(139, 206, 89, 23);
		contentPane.add(btnSubmit);
		
		txtpnUser = new JTextPane();
		txtpnUser.setText("USER1");
		txtpnUser.setBounds(33, 47, 71, 20);
		contentPane.add(txtpnUser);
		
		txtpnUser_1 = new JTextPane();
		txtpnUser_1.setText("USER2");
		txtpnUser_1.setBounds(33, 90, 71, 20);
		contentPane.add(txtpnUser_1);
		
		txtpnRelation = new JTextPane();
		txtpnRelation.setText("Relation");
		txtpnRelation.setBounds(33, 143, 71, 20);
		contentPane.add(txtpnRelation);
		
		txtpnPermissionGrantedOr = new JTextPane();
		txtpnPermissionGrantedOr.setText("Permission Granted or Not");
		txtpnPermissionGrantedOr.setBounds(284, 47, 140, 20);
		contentPane.add(txtpnPermissionGrantedOr);
		
	}
}
