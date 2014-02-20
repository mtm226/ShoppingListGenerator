package recipegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * This class creates the json file containing shopping list.
 */
public class ShoppingListGenerator {
	private ArrayList<Ingredient> iToShop;
	private FileWriter fileW;
	private BufferedWriter out;
	private String file;
	private String jsonText = "";

	/*
	 * Constructor
	 * Takes as parameter ArrayList of ingredient instances and a file to point to the filewrite location.
	 */
	public ShoppingListGenerator(ArrayList<Ingredient> ingredients, String file){
		this.iToShop = ingredients;
		this.file = file;
	}

	/*
	 * Method to create Shopping_List.json file
	 */
	@SuppressWarnings("unchecked")
	public void WriteJSONFile() throws IOException{
		JSONObject mainObj = new JSONObject();
		JSONArray jsList = new JSONArray();

		try{
			/*
			 * This loop cleans up the ingredients array by removing duplicates and combining amounts and parents into single ingredient.
			 */
			for(int k = 0; k < iToShop.size(); k++){
				for(int l = 0; l < iToShop.size()-1; l++){

					//Looks for duplicate ingredients from ingredient list. If found, then proceed.
					if(iToShop.get(k).getName().equals(iToShop.get(l).getName())){

						//To ensure the duplicate is not "self".
						if(iToShop.get(k) != iToShop.get(l)){

							//If ingredient has quantity.
							if(iToShop.get(l).getQty() != 0.0){

								//If units are different, combine and match the units.
								if(iToShop.get(k).getUnit() != iToShop.get(l).getUnit()){
									
									//Two string tables, each holding new gram-converted values of two ingredients.
									String[] combinedNewUnit1 = Converter.convert(iToShop.get(k).getUnit(), iToShop.get(k).getQty());
									String[] combinedNewUnit2 = Converter.convert(iToShop.get(l).getUnit(), iToShop.get(l).getQty());
									
									//Update the ingredient units and quantities.
									iToShop.get(k).setUnit(combinedNewUnit1[0]);
									iToShop.get(k).setQty(Double.parseDouble(combinedNewUnit1[1]));
									iToShop.get(l).setUnit(combinedNewUnit2[0]);
									iToShop.get(l).setQty(Double.parseDouble(combinedNewUnit2[1]));

									//Add the other ingredients quantity to the other.
									iToShop.get(k).addQty(iToShop.get(l).getQty());
								}
								else{			
									//Add the other ingredients quantity to the other.
									iToShop.get(k).addQty(iToShop.get(l).getQty());	
								}
							}

							//If ingredient has parents, update users (parents) of spared ingredient .
							for(int m = 0; m < iToShop.get(l).getParent().size(); m++){
								if(iToShop.get(l).getParent() != null){
									iToShop.get(k).updateParent(iToShop.get(l).getParent().get(m));
								}
							}

							//Remove the extra duplicate.
							iToShop.remove(l);
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		//Loops through the ingsForShoppin ArrayList and inserts them jsonText string. This would require still some refinement...
		for(int i = 0; i < iToShop.size(); i++){
			//Ingredient objects
			JSONObject jsObj = new JSONObject();

			/*
			 * If-blocks for every variant of ingredient. Only one of these is used on a single round of the loop.
			 */
			if(iToShop.get(i).getName() != null && iToShop.get(i).getQty() != 0.0 && iToShop.get(i).getUnit() != null){
				jsObj.put("entry", iToShop.get(i).getQty() + " " + iToShop.get(i).getUnit() + " " + iToShop.get(i).getName());

				//Determine if the ingredient has parent (users) or not. If true, then set parents to JSONObject jsObj and replace the previous "entry" property.
				if(!iToShop.get(i).getParent().contains(null)){
					jsObj.put("users", iToShop.get(i).getParent());
					jsObj.put("entry", iToShop.get(i).getQty() + " " + iToShop.get(i).getUnit() + " " + iToShop.get(i).getName() + " (" + iToShop.get(i).getParent().toString().replace("[", "").replace("]", "") + ")");
				}

				//Set rest of ingredient properties to JSONObject jsObj.
				jsObj.put("item", iToShop.get(i).getName());
				jsObj.put("qty", iToShop.get(i).getQty());
				jsObj.put("unit", iToShop.get(i).getUnit());

				//Add jsObj to JSONArray jsList.
				jsList.add(jsObj);
			}

			if(iToShop.get(i).getName() != null && iToShop.get(i).getQty() != 0.0 && iToShop.get(i).getUnit() == null){
				jsObj.put("entry", iToShop.get(i).getQty() + " "  + iToShop.get(i).getName());

				//Determine if the ingredient has parent (users) or not. If true, then set parents to JSONObject jsObj and replace the previous "entry" property.
				if(!iToShop.get(i).getParent().contains(null)){
					jsObj.put("users",iToShop.get(i).getParent());
					jsObj.put("entry", iToShop.get(i).getQty() + " "  + iToShop.get(i).getName() + " (" + iToShop.get(i).getParent().toString().replace("[", "").replace("]", "") + ")");
				}

				//Set rest of ingredient properties to JSONObject jsObj.
				jsObj.put("item", iToShop.get(i).getName());
				jsObj.put("qty", iToShop.get(i).getQty());

				//Add jsObj to JSONArray jsList.
				jsList.add(jsObj);
			}

			if(iToShop.get(i).getName() != null && iToShop.get(i).getQty() == 0.0 && iToShop.get(i).getUnit() == null){

				//Determine if the ingredient has parent (users) or not. If true, then set parents to JSONObject jsObj and replace the previous "entry" property.
				jsObj.put("entry", iToShop.get(i).getName());
				if(!iToShop.get(i).getParent().contains(null)){
					jsObj.put("users", iToShop.get(i).getParent());
					jsObj.put("entry", iToShop.get(i).getName() + " (" + iToShop.get(i).getParent().toString().replace("[", "").replace("]", "") + ")");
				}

				//Set rest of ingredient properties to JSONObject jsObj.
				jsObj.put("item", iToShop.get(i).getName());

				//Add jsObj to JSONArray jsList.
				jsList.add(jsObj);		
			}			
		}

		//Append JSONArray jsList to JSONObject mainObj and add them to jsonText string variable.
		mainObj.put("list", jsList);
		jsonText += mainObj.toString();

		//Write json to file.
		fileW = new FileWriter(file);
		out = new BufferedWriter(fileW);
		out.write(jsonText);
		out.close();
	}
}
