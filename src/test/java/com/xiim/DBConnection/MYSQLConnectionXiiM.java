package com.xiim.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class MYSQLConnectionXiiM {
       
       public WebDriver driver;
       
       @Test
         public void database() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
                
                /*Connection is an interface which helps you establish connection with database.*/
                     Connection conn = null;
                     //Defining the SQL URL
                     String url = "jdbc:mysql://192.168.0.51:3306/";
                     //Defining the database name
                     String dbName = "xiimqc";
                     //Defining the driver that is being used
                     String driver = "com.mysql.jdbc.Driver";
                     //Defining username and password
                     String userName = "xiimqc";
                     String password = "xiimqc@123";
                     try {
                           //Loading the driver and creating its instance
                           Class.forName(driver).newInstance();
                           //Establishing the connection with the database
                           conn = DriverManager.getConnection(url+dbName, userName,password);
                           /*createStatement() method creates a Statement object for sending SQL to the database.
                            *It executes the SQL and returns the result it produces
                            */    
                           Statement stmt = conn.createStatement();
                             /*executeQuery() method executes the SQL statement which returns a single ResultSet type object.*/
  ResultSet rs = stmt.executeQuery("select ID,Name,Summary,Description,GroupUniqueIdentifier,groupTypeID,OwnerID,StatusID from `xiimqc`.`Group`;");
                           /*next() returns true if next row is present otherwise it returns false. */
                           while(rs.next()){
                           //printing tge result
                           String Id = rs.getString("ID");
                        
                           System.out.print("ID " + rs.getString("ID"));
                           System.out.print("\t");
                           System.out.print("Name " +rs.getString("Name"));
                           System.out.print("\n");
                           System.out.print("Summary " +rs.getString("Summary"));
//                           System.out.print("\t");
                           System.out.print("Description " +rs.getString("Description"));
//                           System.out.print("\n");
                           System.out.print("groupTypeID " +rs.getString("groupTypeID"));
//                           System.out.print("\t");
                           }
                     } catch(Exception e){
                           System.out.println("Exception Encountered");
                     }
              }
       
       @Test(enabled=false)
       public void CreateDB() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
       //Prepare connection
       String url1 ="jdbc:mysql://192.168.0.51:3306/xiimqc";
       // Load Microsoft SQL Server JDBC driver
       String dbClass = "com.mysql.jdbc.Driver";
       Class.forName(dbClass).newInstance();
       //Get connection to DB
       Connection con = DriverManager.getConnection(url1, "qcdb", "qc@123");
       //Create Statement
       Statement stmt = (Statement) con.createStatement();
       // method which returns the requested information as rows of data
       ResultSet rs = (ResultSet) stmt.executeQuery("select ID,Name,Summary,Description,GroupUniqueIdentifier,groupTypeID,OwnerID,StatusID from `xiimqc`.`Group`;");
         
}
       
       /*@BeforeClass
       
       public void beforeClass(){
       driver = new FirefoxDriver();
       driver.get("192.168.0.51:3306/xiimqc");
       }*/
}
