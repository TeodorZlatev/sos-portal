package bg.tu.sofia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import bg.tu.sofia.dtos.UserDto;

@Component
public class MailUtil {

	@Autowired
	private JavaMailSender mailSender;

	public void sendMailForNightTax(String receiver, String host, String guest, String date) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject("Нощувка - " + date);
		msg.setTo(receiver);
		msg.setText("Здравейте, " + host + ",\n\n имате вписана нощувка. \n\nГост: " + guest + "\nДата: " + date
				+ "\n\nМоля, заплатете на касите на ТУ в 5-дневен срок!\n\nСтудентски общежития и столове, ТУ - София");

		this.mailSender.send(msg);
	}

	public void sendMailForRegistration(UserDto inhabited, String role, String room, String block) {

		StringBuilder sb = new StringBuilder();

		sb.append("Здравейте, " + inhabited.getUsername()
				+ ",\n\n успешно ви бе създадена регистрация в портала на СОС ( http://localhost:8080/sos-portal ). \n\nЛични данни:\nЕГН: "
				+ inhabited.getPersonalNumber() + "\nEmail: " + inhabited.getEmail() + "\nСтатус: " + role);
		if (room != null) {
			sb.append("\nСтая: " + room);
		}

		if (block != null) {
			sb.append("\nБлок: " + block);
		}

		sb.append("\n\nСтудентски общежития и столове, ТУ - София");

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject("Регистрация в портал на СОС");
		msg.setTo(inhabited.getEmail());
		msg.setText(sb.toString());

		this.mailSender.send(msg);
	}

}
