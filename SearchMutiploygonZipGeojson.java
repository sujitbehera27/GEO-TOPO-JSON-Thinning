package geojsonoptimizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SearchMutiploygonZipGeojson {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException 
	{
	 
	  // Directory path here
	  String path1 = "/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/geojson-tiger"; 

	  ArrayList<String> fil = new ArrayList<String>();
	  fil.add(path1);
	  JSONParser parser = new JSONParser();
	

				  File folder = new File(path1);
				  File[] listOfFiles = folder.listFiles(); 
				 
					 
				 
				  Set<String> multiPolyList = new HashSet<String>();
				  for (int i = 0; i < listOfFiles.length; i++) 
				  {
					  String fileName = listOfFiles[i].getName();
					 // System.out.println();
					  String path="/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/geojson-tiger/"+listOfFiles[i].getName();
					  Object obj = parser.parse(new FileReader(path));

				        JSONObject jsonObject = (JSONObject) obj;
				        JSONObject modJson = jsonObject;
				       
				        JSONObject geometry = (JSONObject) jsonObject.get("geometry");

				        String type = (String)geometry.get("type");
				   
				        if(type.equals("MultiPolygon")){
				        	//System.out.println("----------"+type);
				        	multiPolyList.add(fileName.substring(0,fileName.length()-5));
				        	System.out.println("File name is==>"+fileName.substring(0,fileName.length()-5));
				        	
				        }
				      
				  }
				  System.out.println("Total No of multipolygons is"+multiPolyList.size());
	 
	 
	  parseResult(multiPolyList);
	}
	/*
	 * function for modifying the individual zipcode geojson and write them
	 * as new file content in the same file name ina  different location
	 */
	public static void parseResult(Set<String> result){
		Iterator<String> iter = result.iterator();
		JSONParser parser = new JSONParser();
		String zip = "";
		String path = "";
		String oppath = "";
		JSONObject emptyObj = new JSONObject();
		try{
		while (iter.hasNext()) {
		 // System.out.println(iter.next());
			
			zip = iter.next();
			path="/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/geojson-tiger/"+zip+".json";
		  Object obj = parser.parse(new FileReader(path));

	        JSONObject jsonObject = (JSONObject) obj;
	        JSONObject modJson = jsonObject;
	      
	        JSONObject geometry = (JSONObject) jsonObject.get("geometry");
	        modJson.put("type","Feature");
	        modJson.put("properties",emptyObj);
	        modJson.put("id",zip);
	        //geometry.put("type", "MultiPolygon");
	        //writing down the jsons to outut folder
	        oppath="/home/sujit/ZZZZZZIPCODE/LBA-FINAL/DATA_OPTIMIZE/multipolygons/"+zip+".json";
	        FileWriter file = new FileWriter(oppath);
	        file.write(modJson.toJSONString());
	        file.flush();
			file.close();
			System.out.println("Final Modified Json is --->" + modJson);
		}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
