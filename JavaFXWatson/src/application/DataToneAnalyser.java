package application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * <h2>The tone analyser data modal class</h2>
 * This class is used to model the JSON file returned from Watson.
 * When the JSON file is returned it is parsed and then attached to this class.
 * 
 * @author Ieuan Walker
 */

public class DataToneAnalyser {
	/** The first object in the JSON file */
	private JsonObject document_tone =  this.document_tone;
	
	/*
	 * This method is used to get a JSON array of object.
	 * The method is private because it is only used within this class.
	 * 
	 * @return JSONArray. This is a JsonArray and it returns an array of objects.  
	 */	
	private JsonArray tone(){
		
		JsonArray list1 = new JsonArray();
		list1 = this.document_tone.getAsJsonArray("tone_categories");
		
		JsonObject child1;
		child1 = (JsonObject) list1.get(0);
		
		JsonArray list2 = new JsonArray();
		list2 = child1.getAsJsonArray("tones");
		
		return list2;
	}
	
	/*
	 * This method is used to get the number of objects in the tone array.
	 * 
	 * @return int. The number of item in the array. 
	 */
	public int getToneSize(){
		int size = 0;
		
		JsonArray list = tone();
		size = list.size();
		
		return size;
	}
	
	/*
	 * This method is used to get a single piece of data from a specific object within a JSON array.  
	 * 
	 * @param number. This is the index of an object within the JSON array.
	 * @param variable. This is the variable you want returned 
	 * 
	 * @return String. This returns the specific variable that was asked for.
	 */
	public String getToneDetails(int number, String variable){
		JsonArray list = tone();
		
		JsonObject last = (JsonObject) list.get(number);
		
		return last.get(variable).toString();
	} 
}