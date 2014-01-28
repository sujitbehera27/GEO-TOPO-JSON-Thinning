package geojsonoptimizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SplitGeojsonToZipFiles {
	public static JSONObject emptyObj = new JSONObject();

	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException {

		// Input file location

		String path1 = "/home/sujit/mapshaper/us-zipcodes-2012-01.json";
		ArrayList<String> fil = new ArrayList<String>();
		fil.add(path1);
		JSONParser parser = new JSONParser();
		

		File path = new File(path1);
		Object obj = parser.parse(new FileReader(path));

		JSONObject jsonObject = (JSONObject) obj;
		JSONObject zip = new JSONObject();
		JSONArray features = (JSONArray) jsonObject.get("features");
		for (int j = 0; j < features.size(); j++) {

			zip = (JSONObject) features.get(j);

			parseResult(zip);

		}
		

	}

	/*
	 * Function for splitting and writing the files into individual zip code
	 * level files containing geojson.
	 */
	public static void parseResult(JSONObject zip) {

		String oppath = "";
		try {

			JSONObject jsonObject = (JSONObject) zip;
			JSONObject modJson = jsonObject;

			JSONObject props = (JSONObject) jsonObject.get("properties");

			String zipCode = (String) props.get("ZCTA5CE10");
			System.out.println(zipCode);

			JSONObject geometry = (JSONObject) jsonObject.get("geometry");
			modJson.put("type", "Feature");
			modJson.put("properties", emptyObj);
			modJson.put("id", zipCode);
			// geometry.put("type", "MultiPolygon");
			// writing down the jsons to outut folder
			// oppath="/home/sujit/mapshaper/US-ZIPCODE-2012-29MB-01/"+zipCode+".json";
			oppath = "/home/sujit/rcloud-release-0.9/htdocs/DCWIFI/lib/geojson/"
					+ zipCode + ".json";
			FileWriter file = new FileWriter(oppath);
			file.write(modJson.toJSONString());
			file.flush();
			file.close();
			System.out.println("Final Modified Json is --->" + modJson);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
