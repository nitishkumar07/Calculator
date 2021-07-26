import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Calculator implements ActionListener{
	JFrame frame;
	JTextField textfield;
	JTextField txt;
	JButton[] numButton=new JButton[10];
	JButton[] funButton= new JButton[8];
	JButton addbtn,subbtn,mulbtn,divbtn,dotbtn,eqbtn,clrbtn,delbtn,his;
	JPanel p;
	Font font= new Font("arial",Font.BOLD,20);
	double num1=0,num2=0,result=0;
	char fun;
	String string;
	
	Calculator() {
		frame= new JFrame("Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,550);
		frame.setLayout(null);
		textfield= new JTextField();
		textfield.setBounds(50, 25, 300, 50);
		textfield.setEditable(false);
		textfield.setFont(font);
		//txt.setBounds(50,60,300,10);
		//txt.setEditable(false);
		//txt.setFont(font);
		
		addbtn= new JButton("+");
		subbtn= new JButton("-");
		mulbtn= new JButton("X");
		divbtn= new JButton("/");
		dotbtn= new JButton(".");
		eqbtn= new JButton("=");
		delbtn= new JButton("delete");
		clrbtn= new JButton("clear");
		his= new JButton("H");
		funButton[0]=addbtn;
		funButton[1]=subbtn;
		funButton[2]=mulbtn;
		funButton[3]=divbtn;
		funButton[4]=dotbtn;
		funButton[5]=eqbtn;
		funButton[6]=delbtn;
		funButton[7]=clrbtn;
		for(int i=0;i<8;i++) {
			funButton[i].addActionListener(this);
			funButton[i].setFocusable(false);
			funButton[i].setFont(font);
		}
		for(int i=0;i<10;i++)
		{
			numButton[i]=new JButton(String.valueOf(i));
			numButton[i].addActionListener(this);
			numButton[i].setFocusable(false);
			numButton[i].setFont(font);
		}
		his.addActionListener(this);
		his.setFocusable(false);
		his.setFont(font);
		p= new JPanel();
		p.setBounds(50, 100, 300, 300);
		p.setLayout(new GridLayout(4,4,10,10));
		frame.add(p);
		p.add(numButton[1]);
		p.add(numButton[2]);
		p.add(numButton[3]);
		p.add(addbtn);
		p.add(numButton[4]);
		p.add(numButton[5]);
		p.add(numButton[6]);
		p.add(subbtn);
		p.add(numButton[7]);
		p.add(numButton[8]);
		p.add(numButton[9]);
		p.add(mulbtn);
		p.add(dotbtn);
		p.add(numButton[0]);
		p.add(divbtn);
		p.add(eqbtn);
		
		
		delbtn.setBounds(100, 430, 130, 40);
		clrbtn.setBounds(260, 430, 130, 40);
		his.setBounds(5, 430, 70, 40);
		
		frame.add(his);
		frame.add(clrbtn);
		frame.add(delbtn);
		frame.add(textfield);
		//frame.add(txt);
		frame.setVisible(true);
		
		
	}
	private Connection connect() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator","root","nitishkumar");
			
		}
		catch(SQLException e) {
			System.out.println(e);
			return null;
		}
	}
	public static void main(String[] args) {
		
		Calculator calc=new Calculator();
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<10;i++) {
			if(e.getSource()== numButton[i]) {
				textfield.setText(textfield.getText().concat(String.valueOf(i)));
				
			}
		}
		
		if(e.getSource()==dotbtn) {
			textfield.setText(textfield.getText().concat("."));
		}
		if(e.getSource()==addbtn) {
			num1= Double.parseDouble(textfield.getText());
			fun='+';
			textfield.setText("");
			
		}
		if(e.getSource()==subbtn) {
			num1= Double.parseDouble(textfield.getText());
			fun='-';
			textfield.setText("");
		}
		if(e.getSource()==mulbtn) {
			num1= Double.parseDouble(textfield.getText());
			
			fun='X';
			textfield.setText("");
		}
		if(e.getSource()==divbtn) {
			num1= Double.parseDouble(textfield.getText());
			fun='/';
			textfield.setText("");
		}
		if(e.getSource()==eqbtn) {
			string="";
			num2= Double.parseDouble(textfield.getText());
			switch(fun) {
			case'+':
				result=num1+num2;
				break;
			case'-':
				result=num1-num2;
				break;
			case'X':
				result=num1*num2;
				break;
			case'/':
				result=num1/num2;
				break;
				
			}
			textfield.setText(String.valueOf(result));
			string+=String.valueOf(num1);
			
			string+=fun;
			string+=String.valueOf(num2);
			string+='=';
			string+=String.valueOf(result);
			try(Connection con= connect();
				PreparedStatement patat=con.prepareStatement("INSERT INTO calculator VALUES(?)")){
				patat.setString(1,string);
				patat.executeUpdate();
				}
			catch(SQLException p) {
				System.out.println(p);
			}
			string="";
			num1=result;
		}
		if(e.getSource()==his) {
			window w= new window();
		}
			
		if(e.getSource()==clrbtn) {
			textfield.setText("");
			string="";
		}
		if(e.getSource()==delbtn) {
			String string=textfield.getText();
			textfield.setText("");
			for(int i=0;i<string.length()-1;i++) {
				textfield.setText(textfield.getText()+string.charAt(i));
			}
		}
		
	}

}
