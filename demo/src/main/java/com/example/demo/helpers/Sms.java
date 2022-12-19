package com.example.demo.helpers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.ValidationRequest;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class Sms {

    @GetMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS() {

        Twilio.init("AC200f44a00de09e1f6081363b6aab00b3", "98374d8921218fa3264dae7c19a1b408");

//        Message.creator(new PhoneNumber("<TO +212777379407>"),
//                new PhoneNumber("<FROM +212777379407>"), "Hello from Twilio 📞").create();
        ValidationRequest validationRequest = ValidationRequest.creator(
                        new com.twilio.type.PhoneNumber("+212777379407"))
                .setFriendlyName("My Home Phone Number")
                .create();
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+212777379407"),
                        new com.twilio.type.PhoneNumber("+14455451523"),
                        " waaaasaaad ?")
                .create();

        return new ResponseEntity<String>(message.getSid(), HttpStatus.OK);
    }

    @PostMapping(value = "/send")
    public String sendSms() {
        try {
            // Construct data
            String apiKey = "apikey=" + "MzY3NjRlNzU2ZDY0NmI1NDc4MzI0NjMwNDgzNDY4MzQ=";
            String message = "&message=" + "This is your message";
            String sender = "&sender=" + "Jims Autos";
            String numbers = "&numbers=" + "777379407";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}
