package bg.tu.sofia.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PageUtil {

	public String createPagination(int pageNumber, int pagesCount) {

		JSONObject obj = new JSONObject();

		obj.put("firstPage", 1);
		obj.put("lastPage", pagesCount);

		pageNumber = pageNumber > pagesCount ? pagesCount : pageNumber;
		pageNumber = pageNumber < 1 ? 1 : pageNumber;
		obj.put("active", pageNumber);

		// previousPages
		JSONArray previousPages = new JSONArray();
		if (pageNumber >= 3) {
			previousPages.put(pageNumber - 2);
			previousPages.put(pageNumber - 1);
		} else if (pageNumber == 2) {
			previousPages.put(pageNumber - 1);
		}
		obj.put("previousPages", previousPages);

		// nextPages
		JSONArray nextPages = new JSONArray();
		if ((pageNumber + 2) <= pagesCount) {
			nextPages.put(pageNumber + 1);
			nextPages.put(pageNumber + 2);
		} else if ((pageNumber + 1) <= pagesCount) {
			nextPages.put(pageNumber + 1);
		}

		obj.put("nextPages", nextPages);
		
		return obj.toString();
	}
}
