package geojsonoptimizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * Sujit
 * Class to remove alternative coordinates
 */
public class RemoveAlternateCordinates {

	public static void main(String[] args) throws FileNotFoundException,
	IOException, ParseException {

		// Directory path here
		String path1 = "/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/geojson-tiger-half-5/";


		ArrayList<String> fil = new ArrayList<String>();
		fil.add(path1);
		JSONParser parser = new JSONParser();
	

		File folder = new File(path1);
		File[] listOfFiles = folder.listFiles();

		Set<String> multiPolyList = new HashSet<String>();
		for (int i = 0; i < listOfFiles.length; i++)

		{

			String fileName = listOfFiles[i].getName();

			String path = path1
					+ listOfFiles[i].getName();
			Object obj = parser.parse(new FileReader(path));

			JSONObject jsonObject = (JSONObject) obj;
		
			JSONObject geometry = (JSONObject) jsonObject.get("geometry");
			
			String type = (String) geometry.get("type");

			if (type.equals("Polygon")) {

				JSONArray cords2 = new JSONArray();

				cords2 = (JSONArray) geometry.get("coordinates");

				JSONArray cords1 = new JSONArray();
				String zip = "";
				for (int j = 0; j < cords2.size(); j++) {

					cords1 = (JSONArray) cords2.get(j);
					JSONArray cords3 = new JSONArray();

					System.out.println("before size--->" + cords1.size());
					JSONArray cordsshort = new JSONArray();
					for (int k = 0; k < cords1.size(); k++) {

						cords3 = (JSONArray) cords1.get(k);
						if (k > 1 && k < (cords1.size() - 1) && k % 2 != 0) {
							cordsshort.add(cords1.get(k));

						}
					}
					cords1.removeAll(cordsshort);
					System.out.println("after size--->" + cords1.size());

				}

				System.out.println("File name is==>"
						+ fileName.substring(0, fileName.length() - 5));
				multiPolyList.add(fileName.substring(0, fileName.length() - 5));

				zip = fileName.substring(0, fileName.length() - 5);
				parseResult(zip, jsonObject);
			} else {
				// System.out.println("----------"+type);
				multiPolyList.add(fileName.substring(0, fileName.length() - 5));
				System.out.println("File name is==>"
						+ fileName.substring(0, fileName.length() - 5));

				JSONArray cords1 = new JSONArray();


				cords1 = (JSONArray) geometry.get("coordinates");

				JSONArray cords2 = new JSONArray();
				String zip = "";
				for (int j = 0; j < cords1.size(); j++) {

					cords2 = (JSONArray) cords1.get(j);



					JSONArray cords3 = new JSONArray();
					for (int l = 0; l < cords2.size(); l++) {
						cords3 = (JSONArray) cords2.get(l);
						JSONArray cords4 = new JSONArray();
						System.out.println("before size--->" + cords3.size());
						JSONArray cordsshort = new JSONArray();
						for (int k = 0; k < cords3.size(); k++) {

							cords4 = (JSONArray) cords3.get(k);
							if (k > 1 && k < (cords3.size() - 1) && k % 2 != 0) {
								cordsshort.add(cords3.get(k));

							}
						}
						cords3.removeAll(cordsshort);
						System.out.println("after size--->" + cords3.size());
					}

				}

				System.out.println("File name is==>"
						+ fileName.substring(0, fileName.length() - 5));
				multiPolyList.add(fileName.substring(0, fileName.length() - 5));

				zip = fileName.substring(0, fileName.length() - 5);
				parseResult(zip, jsonObject);
			}
		}
		System.out.println("Total No of multipolygons is"
				+ multiPolyList.size());
		// System.out.println("---tiger----"+tiger.size());

	}

	public static void parseResult(String zip, JSONObject jsonObject) {
		
		String oppath = "";
		JSONObject emptyObj = new JSONObject();
		try {

			JSONObject modJson = jsonObject;

			
			 modJson.put("properties",emptyObj);
			 modJson.put("id",zip);
		
			oppath = "/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/geojson-tiger-half-6/"
					+ zip + ".json";
			FileWriter file = new FileWriter(oppath);
			file.write(modJson.toJSONString());
			file.flush();
			file.close();
			System.out.println("************name***************" + modJson);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
