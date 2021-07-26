import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class window implements ActionListener{
	JFrame frame=new JFrame("HISTORY");
	JButton view= new JButton("view");
	JTextArea textarea= new JTextArea();
	window(){
		
		textarea.setBounds(50,50,400,400);
		view.setBounds(50, 500, 100, 50);
		view.addActionListener(this);
		frame.add(view);
		frame.add(textarea);
		frame.setSize(500, 600);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==view) {
			try(Connection conn = connect();
					Statement stat = conn.createStatement()){
				boolean hasResultSet = stat.execute("SELECT * FROM calculator");
					
				if(hasResultSet) {
					ResultSet result=stat.getResultSet();
					ResultSetMetaData metaData= result.getMetaData();
					int columnCount = metaData.getColumnCount();
//					for(int i=1;i<=columnCount;i++) {
//						System.out.print(metaData.getColumnLabel(i)+"\t");
//						
//					}
					
					while(result.next()) {
						textarea.setText(result.getString("history"));
						//System.out.printf("%s\n",result.getString("history"));
						
					}
				}
						
			}
			catch(SQLException u) {
				System.out.println(u);
			}
	}
		}
		
	}
