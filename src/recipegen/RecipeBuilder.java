package recipegen;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class RecipeBuilder{
	public static void main(String[] args){
		ArrayList<ArrayList<Ingredient>> ingList = new ArrayList<>();
		ArrayList<Ingredient> shoppings = new ArrayList<>();
		XMLHandler xmlBuildUp = new XMLHandler();	
		String file = "MyOwn_Shopping_List.json";

		try {
			Document allDoc;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			//Include all recipes from all.xml.
			allDoc = docBuilder.parse("all.xml");
			NodeList files = allDoc.getElementsByTagName("xi:include");

			//Fetch ingredients lists to ingList by using getXmlDocument method of XMLHandler class.
			for(int i = 0; i < files.getLength(); i++){
				ingList.add(xmlBuildUp.getXmlDocument(files.item(i).getAttributes().item(0).getNodeValue()));
			}

			//Insert every ArrayList ingLists elements ingredients to shoppings list.
			for(int j = 0; j < ingList.size(); j++){
				for(int k = 0; k < ingList.get(j).size(); k++){
					shoppings.add(ingList.get(j).get(k));
				}
			}

			//Create json shopping list.
			ShoppingListGenerator list = new ShoppingListGenerator(shoppings, file);
			list.WriteJSONFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}