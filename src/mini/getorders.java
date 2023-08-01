package mini;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getorders extends GetAllOrders {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/miniconsole";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "subash@123";
    
    
    ///polymorphism

	@Override
	void getOrders() {
   	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        String deleteSql = "Select * from orders";
	        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
	       ResultSet rs = pstmt.executeQuery();
	      System.out.println("Welcome,! Your orders:");
	     System.out.println("-------------------------------------------------");
	       while(rs.next()) {
	    	   System.out.println(rs.getString(2)+" - Quantity: "+rs.getInt(3)+" - Price: $" +rs.getInt(4));
	       }
	      System.out.println("-------------------------------------------------");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
   }
	
	@Override
	 void getOrders(String customer_name) {
    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
  	        String getsql = "Select * from orders where product_name=?";
  	        
  	        PreparedStatement pstmt = conn.prepareStatement(getsql);
  	        pstmt.setString(0, customer_name);
  	       ResultSet rs = pstmt.executeQuery();
  	       
  	       while(rs.next()) {
  	    	   System.out.println(rs.getInt(0+" "+rs.getString(1)+" "+rs.getInt(3)+" " +rs.getInt(4)));
  	       }
  	    } catch (SQLException e) {
  	        e.printStackTrace();
  	    }
   }
}
