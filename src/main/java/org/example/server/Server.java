package org.example.server;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.example.model.User;
import org.example.model.VerifyOtpRequest;
import org.example.service.OtpService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger("Server");
    private final int PORT;
    private ServerSocket serverSocket;


    public Server(int port) {

        this.PORT = port;

    }

    public void startServer() throws IOException {


      serverSocket = new ServerSocket(PORT);


        logger.log(Level.INFO, String.format("Started server at port %d", PORT));


    }

    public void acceptRequests() {
        while (true) {

            try (Socket socket = serverSocket.accept()) {
                logger.log(Level.INFO, "A new client accepted");

                try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                    try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

                    String line = dataInputStream.readLine();
                    if (line != null) {

                        proccesRequests(getRequestMethod(line),getPath(line),dataOutputStream,dataInputStream);
                        }


                    }


                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error ", e);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public String getJsonBody(DataInputStream dataInputStream){
        String json=null;
        try {

            byte[] response = new byte[1024];
            dataInputStream.read(response);
            String stringresponse = new String(response);
            int start = stringresponse.indexOf("{");
            int end = stringresponse.indexOf("}");
            if (isJsonBody(start,end)) {


                 json = stringresponse.substring(start, end + 1);
            }
        } catch (IOException e){
            logger.log(Level.SEVERE,"Error when process body:",e);
        }
        return json;
    }
    public boolean isJsonBody(int start,int end){
       return start > 0 && end > 0;

        }

    public String getRequestMethod(String requestLine) {
        String[] x = requestLine.split(" ");


        return x[0];
    }

    public String getPath(String requestLine) {
        String[] x = requestLine.split(" ");


        return x[1];

    }


    public void proccesRequests(String method, String path, DataOutputStream dos, DataInputStream dis) {

        String msg = null;
        ObjectMapper mapper = new ObjectMapper();
        String json = getJsonBody(dis);
        if(json==null){
            return;
        }
        try {
            if (path.equals("/otp") && method.equals("GET")) {


                User user = mapper.readValue(json, User.class);
                OtpService.generateOtp(user);

                msg = "otp generate";

            } else if (path.equals("/otp") && method.equals("POST")) {
                 VerifyOtpRequest verifyOtpRequest = mapper.readValue(json, VerifyOtpRequest.class);
                 boolean verifyOtp = OtpService.verifyOtp(verifyOtpRequest);
                if (verifyOtp == true) {
                    msg = "otp verify";

                } else {
                    msg = "otp dosen't verify";

                }

            } else {
                msg = "not found";
            }
            sendResponse(msg, dos);
            dos.flush();
// google

        } catch (JsonMappingException e) {
            logger.log(Level.SEVERE, "Error ", e);

        } catch (JsonParseException e) {
            logger.log(Level.SEVERE, "Error ", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error ", e);
        }
    }



    public void sendResponse(String message, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(String.format(
                "HTTP/1.1 200 OK\n\r\n {\"message\":\"%s\"}", message));

    }

}


