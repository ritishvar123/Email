package com.example.hp.email;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.view.View;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText et_to, et_sub, et_msg;
    String to, sub, msg;
    Button btn_send;
    ProgressDialog progressDialog;
    Session session = null;
    Properties properties = null;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_to = (EditText)findViewById(R.id.editText);
        et_sub = (EditText)findViewById(R.id.editText2);
        et_msg = (EditText)findViewById(R.id.editText3);
        btn_send = (Button)findViewById(R.id.button);
        progressDialog = new ProgressDialog(MainActivity.this);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to = et_to.getText().toString();
                sub = et_sub.getText().toString();
                msg = et_msg.getText().toString();
                n = 100000 + new Random().nextInt(900000);
                properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "465");
                session = Session.getDefaultInstance(properties,new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication("bia.attendence@gmail.com", "bia@1234");
                    }
                });
                session.setDebug(true);
                new sendMail().execute();
            }
        });
    }

    public class sendMail extends AsyncTask<String, String, String>{
        @Override
        public void onPreExecute() {
            progressDialog.setMessage("Sending...");
            progressDialog.show();
        }

        @Override
        public String doInBackground(String... strings) {
            try {
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress("bia.attendence@gmail.com"));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mimeMessage.setSubject(sub);
                mimeMessage.setText(""+n);
                //mimeMessage.setContent(msg, "text/html; charset=utf-8");
                Transport.send(mimeMessage);
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException ex) {
                ex.printStackTrace();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            return null;
        }

        @Override
        public void onPostExecute(String s) {
            progressDialog.hide();
            Toast.makeText(MainActivity.this, "Message has sent", Toast.LENGTH_LONG).show();
            // jhgdhhhjgdsyxxhtyx
            //nchcjhcjhch
            // jhvhjcjjhccccccg
            // kjvkvjkcjyc
        }
    }
}
