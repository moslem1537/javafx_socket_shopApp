package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Admin;

import applicationserver.Response;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;

public class AdminDaoImpl  {

    Client cl = new Client();





    public void createAdmin(Admin admin){
    	if (findAdmin(admin.getEmail())) {
            System.out.println("Admin already exists");
        }else{
          

        
 
            String query = "INSERT INTO Admins VALUES (NULL, '" + admin.getFirstName() + "', '" + admin.getLastName() + "', '" + admin.getEmail() + "', '" + admin.getPassword() + "', '" + admin.getPicture() + "')";
            cl.run(query);
}
        }

  

    public boolean findAdmin (String email) {
        String query = "SELECT * FROM Admins WHERE email LIKE '" +email+ "'";
        
        boolean found = false;
        	Response r = cl.run(query);
    		if(!r.getValue().isEmpty()) 	
    		{
             found = true;
    		}
    		
          
 

        return found;
    }

    public long findAdminId (String email) {
        String query = "SELECT id FROM Admins WHERE email LIKE '" +email+ "'";
        Response r = cl.run(query);

		if(!r.getValue().isEmpty()) 	
		{
			 return Long.parseLong(r.getValue().get(0));
		}



               


        return -1;
    }

    public Admin findAdmin (long id) {
        String query = "SELECT * FROM Admins WHERE id = '" +id+ "'";
        Response r = cl.run(query);

            if (!r.getValue().isEmpty()){
                Admin admin = new Admin(id, r.getValue().get(1), r.getValue().get(2), r.getValue().get(3),
                		r.getValue().get(4), r.getValue().get(5));

                return admin;
            }

        return null;
    }

    public Image getAdminImage (String email) {
        String query = "SELECT picture FROM Admins WHERE email LIKE '" +email+ "'";
        Response r = cl.run(query);
        javafx.scene.image.Image img = null;

                try {
                     
                	byte[] imageData = Base64.getDecoder().decode(r.getValue().get(0));

           
                	InputStream inputStream = new ByteArrayInputStream(imageData);

                    InputStream is = new BufferedInputStream(inputStream);
                    BufferedImage imBuff = ImageIO.read(is);
                   
                    img = SwingFXUtils.toFXImage(imBuff, null);
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
         
   
        

        return img;
    }



    public String getPassword(String email) {
        String query = "SELECT password FROM Admins WHERE email LIKE '" +email+ "'";

        	Response r = cl.run(query);
    		if(!r.getValue().isEmpty()) {	


                return r.getValue().get(0);
    		}

        return null;
    }
}
