package org.chunta.chuntaautomail;

import android.support.v7.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * メールクラス.
 */
public class Mail extends AppCompatActivity {
    private UserData userData;

    /**
     * メールを非同期で送信する.
     */
    public void sendMail(UserData userData) {
        this.userData = userData;

        new Thread(new Runnable() {
            @Override
            public void run() {
                send();
            }
        }).start();
    }

    /**
     * 送信する.
     */
    private void send() {
        final String email = userData.getStrFrom();
        final String to = userData.getStrTo();
        final String password = userData.getStrPass();
        final String body = userData.getStrBody();
        final String subject = userData.getStrSubject();

        try {
//            // email と password更新
//            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.getInstance().getApplicationContext());
//            sp.edit().putString("email", email).commit();
//            sp.edit().putString("password", password).commit();

            // メール送信用パラメータ設定
            final Properties property = new Properties();
            property.put("mail.smtp.host", Const.EMAIL_HOST_GOOGLE);
            property.put("mail.host", Const.EMAIL_HOST_GOOGLE);
            property.put("mail.smtp.port", Const.EMAIL_PORT_GOOGLE);
            property.put("mail.smtp.socketFactory.port", Const.EMAIL_PORT_GOOGLE);
            property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            // セッション
            final Session session = Session.getInstance(property, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            MimeMessage mimeMsg = new MimeMessage(session);

            mimeMsg.setSubject(subject, "utf-8");
            mimeMsg.setFrom(new InternetAddress(email));
            mimeMsg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            mimeMsg.setText(body);

            // メール送信
            final Transport transport = session.getTransport("smtp");
            transport.connect(email,password);
            transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            System.out.println("EmailException = " + e);
        } finally {
            System.out.println("finish sending email");
        }
    }
}
