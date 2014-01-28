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
public class RemoveIntermediateCordinates {

	public static void main(String[] args) throws FileNotFoundException,
	IOException, ParseException {

		// Input Directory path for all the zip codes geojsons.
		String path1 = "/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/UPLOAD_ORI/geojson38/";


		ArrayList<String> fil = new ArrayList<String>();
		fil.add(path1);
		JSONParser parser = new JSONParser();
	

		File folder = new File(path1);
		File[] listOfFiles = folder.listFiles();

		Set<String> multiPolyList = new HashSet<String>();
		for (int i = 0; i < listOfFiles.length; i++)

		{
			System.out.println("-------i- before----"+i);
			//i = ++i ;
			System.out.println("-------i- after----"+i);
			String fileName = listOfFiles[i].getName();

			String path = path1
					+ listOfFiles[i].getName();
			Object obj = parser.parse(new FileReader(path));

			JSONObject jsonObject = (JSONObject) obj;
			JSONObject modJson = jsonObject;
			Collection<double[]>[] coordinates;
			JSONObject geometry = (JSONObject) jsonObject.get("geometry");

			String type = (String) geometry.get("type");

			if (type.equals("Polygon")) {

				JSONArray cords2 = new JSONArray();

				cords2 = (JSONArray) geometry.get("coordinates");

				JSONArray cords1 = new JSONArray();
				String zip = "";
				for (int j = 0; j < cords2.size(); j++) {

					cords1 = (JSONArray) cords2.get(j);
					//JSONArray cords3 = new JSONArray();

					System.out.println("before size--->" + cords1.size());
					JSONArray cordsshort = new JSONArray();
					for (int m = 0; m < cords1.size(); m++) {
						if(m<cords1.size()-1){
							m = m+1;
							}
						System.out.println("=======================m- after----"+m);
				//		cords3 = (JSONArray) cords1.get(m);
						//if (k > 1 && k < (cords1.size() - 1) && k % 2 != 0) {
							cordsshort.add(cords1.get(m));

						//}
					}
					//System.out.println("cordsshort before size--->" + cordsshort.size());
					cords1.clear();
					//System.out.println("cordsshort after size--->" + cordsshort.size());
					cords1.addAll(cordsshort);
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
							//System.out.println("-------k- before----"+k);
							if(k<cords3.size()-2){
							k = k+1;
							}
							System.out.println("-------k- after----"+k);
							cords4 = (JSONArray) cords3.get(k);
							//if (k > 1 && k < (cords3.size() - 1) && k % 2 != 0) {
								cordsshort.add(cords3.get(k));

							//}
						}
						cords3.clear();
						cords3.addAll(cordsshort);
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
	/*
	 * function for modifying the individual zipcode geojson and write them
	 * as new file content in the same file name ina  different location
	 */
	public static void parseResult(String zip, JSONObject jsonObject) {
		
		
		String oppath = "";
		JSONObject emptyObj = new JSONObject();
		try {

			JSONObject modJson = jsonObject;

			
			 modJson.put("properties",emptyObj);
			 modJson.put("id",zip);
			
			// writing down the jsons to outut folder
			
			oppath = "/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/UPLOAD_ORI/geojson/"
					+ zip + ".json";
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
