package bg.tu.sofia.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import bg.tu.sofia.constants.RoleEnum;

@Component
public class HeaderUtil {

	public String createHeader(RoleEnum role) {

		JSONArray arr = new JSONArray();

		JSONObject obj = new JSONObject();
		
		obj.put("innerHTML", "Хора с нощувки");
		obj.put("onclick", "createDivPeopleWithNightTaxes");
		
		arr.put(obj);
		
		obj = new JSONObject();
		obj.put("innerHTML", "Вписване на нощувка");
		obj.put("onclick", "createDivCreateNightTax");
		
		arr.put(obj);
		
		obj = new JSONObject();
		obj.put("innerHTML", "Вписване на живущ");
		obj.put("onclick", "createDivInsertInhabitant");

		arr.put(obj);
		
		return arr.toString();
	}

}
