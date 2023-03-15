package funcs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import funcs.dbutil.Operation;
import net.proteanit.sql.DbUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import java.awt.TextField;
import java.awt.Button;

public class RunBlockchian extends JFrame implements ActionListener {
	
	private List<Block>chain =new ArrayList<Block>();
	private List<Block>chain2=new ArrayList<Block>();
	private List<Block>owner_chain=new ArrayList<Block>();
	
	private JPanel contentPane;
	private JButton btnSubmit;
	private JTextField seller_text;
	private JTextField date;
	private Connection con;
	private PreparedStatement ps,ps2,ps3,ps4;
	private ResultSet rs,rs2,rs3,rs4;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnIsChainValid;
	private String validation="  BLOCKCHAIN IS VALID";
	private JTextField validation_display;
	private JSpinner spinner_difficulty;
	private Integer d=2;
	private JButton btnTemperWithHash;
	private JSpinner temper_block_no;
	private JTextField buyer_text;
	private JTextField cur_own_name;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunBlockchian frame = new RunBlockchian();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

		
	public RunBlockchian() {
		setTitle("Registration");
		setIconImage(Toolkit.getDefaultToolkit().getImage(RunBlockchian.class.getResource("/images/logo.png")));
		
		con=Operation.createConnection();
		Block genesis=new Block("admin","reciever","01/01/2000");
		genesis.setPreviousHash("nun");
		genesis.mineblock(d);
		chain.add(genesis);
		fillchain();
		createGUI();
		}
	public void fillchain(){
		try{
			String query="select * from userlog";
			ps3=con.prepareStatement(query);
			rs3=ps3.executeQuery();
			
			while(rs3.next()){
				Block bk=new Block(rs3.getString(2),rs3.getString(3),rs3.getString(4));
				bk.setHash(rs3.getString(5));
				bk.setPreviousHash(rs3.getString(6));
				chain.add(bk);
			}
			rs3.close();
		}
		catch(Exception e3){
			System.out.println(e3);
			e3.printStackTrace();
		}
		chain2=chain;
		
	}
	public void createGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 911, 616);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.white);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSubmit = new JButton("MINE block");
		btnSubmit.addActionListener(this);
		btnSubmit.setBorder(null);
		btnSubmit.setBounds(123, 376, 97, 30);
		
		contentPane.add(btnSubmit);
		
		JLabel lblName = new JLabel("USER1");
		lblName.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 14));
		lblName.setBounds(31, 196, 89, 30);
		contentPane.add(lblName);
		
		JLabel lblLand = new JLabel("DATE");
		lblLand.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 14));
		lblLand.setBounds(31, 278, 72, 35);
		contentPane.add(lblLand);
		
		seller_text = new JTextField();
		seller_text.setBounds(113, 202, 170, 22);
		contentPane.add(seller_text);
		seller_text.setColumns(10);
		
		date = new JTextField();
		date.setBounds(113, 286, 170, 23);
		contentPane.add(date);
		date.setColumns(10);
		
		JButton btnViewData = new JButton("View Data !");
		btnViewData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select * from userlog";
					ps2=con.prepareStatement(query);
					rs2=ps2.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs2));
				}
				catch(Exception e2){
					e2.printStackTrace();
				}
			}
		});
		btnViewData.setBounds(510, 157, 114, 23);
		btnViewData.setBorder(null);
		contentPane.add(btnViewData);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(306, 205, 568, 195);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		/*btnIsChainValid = new JButton("is Chain Valid ?");
		btnIsChainValid.setForeground(new Color(0, 0, 0));
		btnIsChainValid.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 12));
		btnIsChainValid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d=(Integer) spinner_difficulty.getValue();
				
				for(int i=1;i<chain.size();i++)
				{
					
					if(!chain.get(i).getHash().equals(chain.get(i).mineblock(d)))
					{
					System.out.println("block chain is not valid");
					validation="BLOCKCHAIN IS NOT VALID";
					}
					else
					{
						System.out.println("chain is valid");
						validation="BLOCKCHAIN IS VALID";
						validation_display.setBackground(Color.green);
					}
					if(!chain.get(i).getPreviousHash().equals(chain.get(i-1).mineblock(d)))
					{
						System.out.println("blockchain is not valid");
						validation="CHAIN IS NOT VALID";
					}
					else{
						System.out.println("blockchain is valid");
						validation="BLOCKCHAIN IS VALID";
						validation_display.setBackground(Color.green);
					}
					validation_display.setText(validation);
				}
				
			}
		});
		btnIsChainValid.setBounds(306, 485, 125, 29);
		btnIsChainValid.setBorder(null);
		contentPane.add(btnIsChainValid);
		
		validation_display = new JTextField();
		validation_display.setForeground(Color.black);
		validation_display.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 14));
		validation_display.setBorder(null);
		validation_display.setBounds(475, 484, 217, 30);
		validation_display.setBackground(Color.white);
		contentPane.add(validation_display);
		validation_display.setColumns(10);
		
		spinner_difficulty = new JSpinner();
		spinner_difficulty.setBounds(160, 335, 46, 22);
		contentPane.add(spinner_difficulty);
		
		btnTemperWithHash = new JButton("TEMPER with Hash");
		btnTemperWithHash.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 11));
		btnTemperWithHash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				d=(Integer) spinner_difficulty.getValue();
				Integer o=(Integer) temper_block_no.getValue();
				String ph=null;
				String ch=null;
				String seller_name=seller_text.getText();
				String buyer_name=buyer_text.getText();
				String tmstamp=date.getText();	
				
				Block tempblock=new Block(seller_name,buyer_name,tmstamp);
				ph=chain2.get(o-1).getHash();
				tempblock.setPreviousHash(ph);
				ch=tempblock.mineblock(d);
				tempblock.setHash(ch);
				chain2.get(o).setSeller(tempblock.getSeller());
				chain2.get(o).setBuyer(tempblock.getBuyer());
				chain2.get(o).setHash(tempblock.getHash());
				chain2.get(o).setPreviousHash(tempblock.getPreviousHash());
				chain2.get(o).setTimestamp(tempblock.getTimestamp());
			
				for(int i=1;i<chain2.size();i++)
				{
					System.out.println("here NOW");
					if(!chain2.get(i).getHash().equals(chain2.get(i).mineblock(d)))
					{
						System.out.println("blockchain is not valid");
					validation="BLOCKCHAIN IS NOT VALID";
					validation_display.setBackground(Color.red);
					}
					else{
						System.out.println("blockchain is VALID");
						//textField_2.setBackground(Color.green);
					}
					if(!chain2.get(i).getPreviousHash().equals(chain2.get(i-1).mineblock(d))){
						System.out.println("chain not valid");
						validation="BLOCKCHAIN IS NOT VALID";
						validation_display.setBackground(Color.red);
					}
					else{
						System.out.println("chain valid");
						//textField_2.setBackground(Color.green);
					}
					validation_display.setText(validation);
				}
			}
		});
		
		btnTemperWithHash.setBounds(306, 539, 125, 27);
		btnTemperWithHash.setBorder(null);
		contentPane.add(btnTemperWithHash);
		
		temper_block_no = new JSpinner();
		temper_block_no.setBounds(475, 540, 40, 24);
		temper_block_no.setValue(2);
		contentPane.add(temper_block_no);*/
			
		
		JLabel lblBuyer = new JLabel("USER2");
		lblBuyer.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 14));
		lblBuyer.setBounds(31, 237, 72, 30);
		contentPane.add(lblBuyer);
		
		buyer_text = new JTextField();
		buyer_text.setColumns(10);
		buyer_text.setBounds(113, 243, 170, 23);
		contentPane.add(buyer_text);
		
		/*Label label = new Label("OWNERSHIP OF LAND - BLOCKCHAIN BASED PRODUCT ");
		label.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD | Font.ITALIC, 16));
		label.setBounds(323, 60, 464, 30);
		contentPane.add(label)*/;
		
		/*JButton btnCurrentOwner = new JButton("Current Owner");
		btnCurrentOwner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String query="select * from userlog";
					ps4=con.prepareStatement(query);
					rs4=ps4.executeQuery();
					
					while(rs4.next()){
						Block bk=new Block(rs4.getString(2),rs4.getString(3),rs4.getString(4));
						bk.setHash(rs4.getString(5));
						bk.setPreviousHash(rs4.getString(6));
						owner_chain.add(bk);
					}
					rs4.close();
				}
				catch(Exception e3){
					System.out.println(e3);
					e3.printStackTrace();
				}
				System.out.println(owner_chain.get(owner_chain.size()-1).getBuyer());
				String current_owner_name=owner_chain.get(owner_chain.size()-1).getBuyer();
				cur_own_name.setText(current_owner_name);
			}
		});
		btnCurrentOwner.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 11));
		btnCurrentOwner.setBounds(306, 431, 125, 32);
		btnCurrentOwner.setBorder(null);
		contentPane.add(btnCurrentOwner);
		
		cur_own_name = new JTextField();
		cur_own_name.setForeground(Color.black);
		cur_own_name.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 14));
		cur_own_name.setColumns(10);
		cur_own_name.setBorder(null);
		cur_own_name.setBackground(Color.WHITE);
		cur_own_name.setBounds(475, 432, 177, 30);
		contentPane.add(cur_own_name);*/
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		d=2;
		
		String ph=null;
		String ch=null;
		String seller_name=seller_text.getText();
		String buyer_name=buyer_text.getText();
		String tmstamp=date.getText();
		
		if(seller_name.isEmpty()||buyer_name.isEmpty()||tmstamp.length()==0)
		{
			JOptionPane.showMessageDialog(this,"data required");
		}
		else
		{
		if(seller_name.equals(chain.get(chain.size()-1).getBuyer()))
		{
		Block latestblock=new Block(seller_name,buyer_name,tmstamp);
		ph=chain.get(chain.size()-1).getHash();
		latestblock.setPreviousHash(ph);
		ch=latestblock.mineblock(d);
		latestblock.setHash(ch);
		chain.add(latestblock);
		chain2=chain;
						
		
			
			try
			{
				String strinsert ="insert into userlog values(?,?,?,?,?,?)";
				ps=con.prepareStatement(strinsert);
				ps.setInt(1, chain.size()-1);
				ps.setString(2, seller_name);
				ps.setString(3, buyer_name);
				ps.setString(4, tmstamp);
				ps.setString(5, latestblock.getHash());
				ps.setString(6, latestblock.getPreviousHash());
				int rw=ps.executeUpdate();
					if(rw>0){
						JOptionPane.showMessageDialog(this,"data added");

					}
			}
			catch(SQLException se)
			{
				System.out.println(se);
				System.out.println(se.getErrorCode());
				/*if(se.getErrorCode()==1062)
				{
				chain.remove(latestblock);
				}*/
			}					
		
	   }
		else{
			JOptionPane.showMessageDialog(this, "SORRY THIS IS NOT POSSIBLE - this person doesn't own this property");
		}
	}
 }
}
