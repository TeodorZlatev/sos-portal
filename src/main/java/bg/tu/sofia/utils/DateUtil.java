package bg.tu.sofia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	Logger logger = Logger.getLogger(DateUtil.class.getName());

	public Date convertFromStringToDate(String dateStr) {
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sm.parse(dateStr);
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "Parsing date failed!", e);
		}
		return date;
	}

}
