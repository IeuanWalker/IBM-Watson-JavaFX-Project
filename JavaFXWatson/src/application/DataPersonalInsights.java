package application;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*
 * <h2>The personal insights data modal class</h2>
 * This class is used to model the JSON file returned from Watson.
 * When the JSON file is returned it is parsed and then attached to this class.
 * 
 * @author Ieuan Walker
 */
public class DataPersonalInsights {
	/** The first object in the JSON file */
	private JsonObject tree =  this.tree;
	
	/*
	 * This method is used to get a JSON array of object for the personality details.
	 * The method is private because it is only used within this class.
	 * 
	 * @return JSONArray. This is a JsonArray and it returns an array of objects.  
	 */
	
	//Personality array
	private JsonArray personality(){
		JsonArray list1 = new JsonArray();
    	list1 = this.tree.getAsJsonArray("children");
    	
    	JsonObject child1;
    	child1 = (JsonObject) list1.get(0);
    	
    	JsonArray list2 = new JsonArray();
    	list2 = child1.getAsJsonArray("children");
    	
    	JsonObject child2;
    	child2 = (JsonObject) list2.get(0);
    	
    	JsonArray list3 = new JsonArray();
    	list3 = child2.getAsJsonArray("children");
    	
    	JsonObject child3;
    	child3 = (JsonObject) list3.get(0);
    	
    	JsonArray list4 = new JsonArray();
    	list4 = child3.getAsJsonArray("children");
    	
   
    	return list4;
	}
	
	/*
	 * This method is used to get the number of objects in the personality array.
	 * 
	 * @return int. The number of item in the array. 
	 */
	public int getPersonalitySize(){
		int size = 0;
		
		JsonArray list = personality();
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
    public String getPersonalityDetails(int number, String variable){
    	JsonArray list = personality();
    	
    	JsonObject last = (JsonObject) list.get(number);
    	
    	return last.get(variable).toString();
    }
    
    
    /*
     * The way the JSON tree for needs and values is structured it allowed me to 
     * combine the way to get a JSON array for the need and values data.
     *     
     * @param number. Used to determine whether to return the needs array (1) or the values array (2).    
     * 
     * @return JsonArray(). This returns the JSON array of objects.  
     */
    //Needs and Value array
    private JsonArray needsAndValue(int number){
		JsonArray list1 = new JsonArray();
    	list1 = this.tree.getAsJsonArray("children");
    	
    	JsonObject child1;
    	child1 = (JsonObject) list1.get(number);
    	
    	JsonArray list2 = new JsonArray();
    	list2 = child1.getAsJsonArray("children");
    	
    	JsonObject child2;
    	child2 = (JsonObject) list2.get(0);
    	
    	JsonArray list3 = new JsonArray();
    	list3 = child2.getAsJsonArray("children");
    	
    	return list3;
	}
    
    /*
	 * This method is used to get the number of objects in the needs or value array.
	 * 
	 * @param number. Used to determine whether to check the needs array or the value array
	 * 
	 * @return int. The number of item in the array. 
	 */
    public int getNeedValueSize(int number){
		int size = 0;
		
		JsonArray list = needsAndValue(number);
		size = list.size();
		
		return size;
	}
    
    /*
	 * This method is used to get a single piece of data from a specific object within a JSON array.  
	 * 
	 * @param number. Used to determine if its the needS or the values array. 
	 * @param i. This is the index of an object within the JSON array.
	 * @param variable. This is the variable you want returned 
	 * 
	 * @return String. The data the user asked for. This returns the specific variable that was asked for.
	 */
    public String getNeedValueDetails(int number, int i, String variable){
    	JsonArray list = needsAndValue(number);
    	
    	JsonObject last = (JsonObject) list.get(i);
    	
    	return last.get(variable).toString();
    }
}
