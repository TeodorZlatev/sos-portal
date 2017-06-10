package bg.tu.sofia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String receiver, String host, String guest, String date) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject("Нощувка - " + date);
		msg.setTo(receiver);
		msg.setText("Здравейте, " + host + ",\n\n имате вписана нощувка. \n\nГост: " + guest + "\nДата: " + date
				+ "\n\nМоля, заплатете на касите на ТУ в 5-дневен срок!\n\nСтудентски общежития и столове, ТУ - София");

		this.mailSender.send(msg);
	}

}
