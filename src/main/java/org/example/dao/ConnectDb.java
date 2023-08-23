package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectDb {

  //  private static Connection connection=null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    private ConnectDb(){

    }

    public static Connection getConnection(){
        Connection connection;

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/otpapi", "root", "root");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return connection;

    }



}
