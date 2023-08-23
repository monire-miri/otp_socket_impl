package org.example.dao;

import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger("UserDapImp");

    @Override
    public void save(User u, int otp) {
        try (Connection connection = ConnectDb.getConnection()){
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into otpuser (phonenumber,email,otp) values (?,?,?)")) {
            preparedStatement.setString(1, u.getPhonenumber());
            preparedStatement.setString(2, u.getEmail());
            preparedStatement.setInt(3, otp);
            int i = preparedStatement.executeUpdate();
        }
    }
          catch (SQLException e) {
            logger.log(Level.SEVERE,"Error",e);
        }


    }

    @Override
    public void remove(User u) {
        try (Connection connection = ConnectDb.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement("delete from otpuser where phonenumber=? or email=? ")) {
                preparedStatement.setString(1, u.getPhonenumber());
                preparedStatement.setString(2, u.getEmail());
                int i = preparedStatement.executeUpdate();

            }
        }
         catch (SQLException e) {
            logger.log(Level.SEVERE,"Error ",e);
        }

    }

    @Override
    public int get(User u) {
        int otp=-1;
        try (Connection connection = ConnectDb.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select otp from otpuser where phonenumber=? or email=? ")) {
                preparedStatement.setString(1, u.getPhonenumber());
                preparedStatement.setString(2, u.getEmail());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    otp = resultSet.getInt("otp");

                }

            }
        }
         catch (SQLException e) {
            logger.log(Level.SEVERE,"Error",e);
        }


        return otp;
    }
}
