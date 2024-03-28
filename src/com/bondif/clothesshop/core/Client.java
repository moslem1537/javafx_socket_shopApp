package com.bondif.clothesshop.core;

import java.net.*;

import applicationserver.Request;
import applicationserver.Response;

import java.io.*;


public class Client { 
	
	private static final int SPORT = 4444;
	private static final String SHOST = "localhost";
 

	public Response run( String s2) 
	{ 
		try {
		
			 Socket client = new Socket(SHOST,SPORT);
			ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream  is = null;
		 
			while(true)
			{
				Request rq = new Request(s2);
				os.writeObject(rq);
				os.flush();
				
		 
		 
				if(is == null) {is = new ObjectInputStream(new BufferedInputStream((client.getInputStream()))); }
				Object o = is.readObject();
				if(o instanceof Response)
				{  
					Response rs = (Response) o;
					client.close();
					return rs;
				}							
			}			
		 	}
		catch (Exception e) {e.printStackTrace();
		
	}
		
		return null;
	  }  
		

}