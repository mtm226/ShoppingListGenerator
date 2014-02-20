package recipegen;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.util.*;

/*
 * This class is responsible of parsing the XML document and creating an ArrayList of ingredients which is returned to the caller.
 */
public class XMLHandler {

	public ArrayList<Ingredient> getXmlDocument(String pathToXml) throws Exception{
		Document doc = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(pathToXml);
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		String parent = null;

		//Get root element "dish".
		Element root = doc.getDocumentElement();

		//The servings attribute of root element determines the number of multiplier when scaling up the recipe.
		int servings = Integer.parseInt(root.getAttribute("servings"));

		//Each Ingredient to nodelist.
		NodeList ingredientNodes = doc.getElementsByTagName("ingredient");

		//The loop goes through every ingredient in nodelist and creates new instances of Ingredient class.
		for(int i = 0; i < ingredientNodes.getLength(); i++)
		{
			//Individual ingredient's attributes.
			NamedNodeMap attributes = ingredientNodes.item(i).getAttributes();

			if(ingredientNodes.item(i).getParentNode().getAttributes() != null){
				//Gets the parent node of each ingredient.
				NamedNodeMap ingParent = ingredientNodes.item(i).getParentNode().getAttributes();
				if(ingParent.item(0) != null){
					parent = ingParent.item(0).getNodeValue();
				}
			}

			/*
			 * Create new Ingredient instances and add them to ArrayList<Ingredient> ingredients. Then return the list to the caller.
			 */
			// If ingredient contains only "name" attribute.
			if(attributes.getLength() < 2){
				Ingredient ingredient = new Ingredient(attributes.getNamedItem("name").getNodeValue(), servings, parent);
				ingredients.add(ingredient);
			}
			//If ingredient contains "name" and "amount" attributes.
			else if(attributes.getLength() < 3){
				Ingredient ingredient = new Ingredient(attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("amount").getNodeValue(), servings, parent);
				ingredients.add(ingredient);
			}
			//If ingredient contains all attributes.
			else{
				Ingredient ingredient = new Ingredient(attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("amount").getNodeValue(), attributes.getNamedItem("unit").getNodeValue(), servings, parent);
				ingredients.add(ingredient);
			}
		}
		return ingredients;
	}
}
