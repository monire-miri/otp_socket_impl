package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.example.model.VerifyOtpRequest;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class OtpService {


   static UserDao userDao = new UserDaoImpl();
    public static void generateOtp(User u) {



        Random random = new Random();
        int otp = random.nextInt(9000) + 1000;

        userDao.save(u, otp);
        setTimeOtp(u);


    }
    public static boolean verifyOtp(VerifyOtpRequest vo){


        String phonenumber = vo.getPhonenumber();
        User u = new User();
      u.setPhonenumber(phonenumber);
      u.setEmail(vo.getEmail());
         int i = userDao.get(u);
          if(i==vo.getOtp()){

              return true;
        }
        return false;


    }

    public static void setTimeOtp(User u){

        TimerTask tm=new TimerTask() {
            @Override
            public void run() {

                userDao.remove(u);

            }
        };
        Timer timer =new Timer();
        timer.schedule(tm,60*1000);




    }
}