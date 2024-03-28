package applicationserver;
import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.*;

public class ServerThread implements Runnable
{ 
	private static final long SLEEPTIME =200;
	int count = 0;
	Connection con = null;
	private Server  server;
	private Socket socket;
	ArrayList<String> ar = new ArrayList<String>();

	

	
	public ServerThread(final Server s,final Socket socket) {
		this.server = s;
		this.socket = socket;
	}
	

	public void run() {
		try {
			  String url = "jdbc:mysql://127.0.0.1:3306/shop";
			  String admin = "root";
			  String password ="" ;
			  con = DriverManager.getConnection(url,admin,password);
			  if(con !=null) {System.out.print("Connessione avvenuta con successo\n");}
			}catch(SQLException e) {}
				  try {
					  Statement st = null;		
					  ResultSet rs = null;	    
					  ObjectInputStream is  = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
					  ObjectOutputStream os = null;	
					  try {
						  st = con.createStatement();       
					  	} 	catch (SQLException ex) {}	
		
					  while(true) {
						  try { 							
							  Object i = is.readObject();
							  if (i instanceof Request)
							  { Request rq =(Request) i;	
							  Thread.sleep(SLEEPTIME);
							  if(os  == null) {os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));}
							  Boolean x  = st.execute(rq.getValue());
							  if(x)
							  {  int column_count=0;
								  try {
									  rs= st.getResultSet();
									  ResultSetMetaData rsmd = rs.getMetaData();
									  column_count = rsmd.getColumnCount();								  
									  while(rs.next())
									  {   for(int c =1;c<=column_count;c++) 
									  {
										  ar.add(rs.getString(c));
			
									  }            
									  }
									                             
								  }catch(Exception e) {e.printStackTrace();}
									  
								  Response r = new Response(ar,column_count);
								  os.writeObject(r);
								  os.flush();
								  							         
							  } 
							  else if(!x) 
							  	{   
								  try {
								  	 //st.executeUpdate(rq.getValue());							  		
								  		int n = st.getUpdateCount();
								  		String s = Integer.toString(n);
								  		ar.add(s);
		
							  	}catch(Exception e) {}
							  	 Response r = new Response(ar,0);
								  os.writeObject(r);
								  os.flush();
							  	}
						
							  this.socket.close();
							  
							  return;						  	
							  }
							  }catch (Exception e) {
								  e.printStackTrace();
								  System.exit(0);
							  }					
					  	}
					  }catch (Exception e) {e.printStackTrace();}	
	}	
}
