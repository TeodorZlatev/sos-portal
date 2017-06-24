package bg.tu.sofia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {

	private Logger logger = Logger.getLogger(DateUtil.class.getName());
	
	private final String dateFormat = "dd-MM-yyyy";
	private final String dateFormatWithTime = "HH:mm dd-MM-yyyy";
	private final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	private final SimpleDateFormat sdfWithTime = new SimpleDateFormat(dateFormatWithTime);
	
	public Date convertFromStringToDate(String dateStr) {

		if (dateStr == null) {
			return null;
		}
		
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "Parsing date failed!", e);
		}
		return date;
	}

	public String convertFromDateToString(Date date) {
		return date != null ? sdf.format(date) : null;
	}
	
	public String convertFromDateWithTimeToString(Date date) {
		return date != null ? sdfWithTime.format(date) : null;
	}

}
