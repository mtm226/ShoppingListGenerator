package recipegen;

/*
 * The converter class converts all units to grams, when same ingredient is presented in different units.
 * NOTE: I did not implement every case here, only the ones which were needed in this task. Also if this is fully implemented, 
 * it would take in consideration each type of ingredient because 1 tablespoon of flour does not weight the same as 1 tablespoon 
 * of syrup for example.
 */
public class Converter {

	public static String[] convert(String unit, double qty){
		String[] convertable = new String[2];
		Double temp;

		switch (unit){
		case "tsp" :
			convertable[0] = "g";
			temp = qty*5;
			convertable[1] = temp.toString();
			break;
		case "tsp." :
			break;
		case "tbsp" :
			convertable[0] = "g";
			temp = qty*10;
			convertable[1] = temp.toString();
			break;
		case "tbsp." :
			break;
		case "fl oz" :
			break;
		case "fl. oz." :
			break;
		case "gill" :
			break;
		case "gills" :
			break;
		case "cup" :
			break;
		case "cups" :
			break;
		case "pint" :
			break;
		case "pints" :
			break;
		case "quart" :
			break;
		case "quarts" :
			break;
		case "gallon" :
			break;
		case "gallons" :
			break;
		case "ml" :
			break;
		case "l" :
			break;
		case "dl" :
			break;
		case "lb" :
			break;
		case "oz" :
			break;
		case "ug" :
			break;
		case "µg" :
			break;
		case "mg" :
			break;
		case "g" :
			convertable[0] = "g";
			temp = qty*1;
			convertable[1] = temp.toString();
			break;
		case "kg" :
			break;
		case "mm" :
			break;
		case "cm" :
			break;
		case "m" :
			break;
		case "in" :
			break;
		case "can" :
			break;
		case "cans" :
			break;
		case "jar" :
			break;
		case "jars" :
			break;
		case "pinch" :
			convertable[0] = "g";
			temp = qty*3;
			convertable[1] = temp.toString();
			break;
		case "pinches" :
			break;
		case "portion" :
			break;
		case "portions" :
			break;
		case "handful" :
			break;
		case "handfuls" :
			break;
		case "splash" :
			break;
		case "splashes" :
			break;
		case "scoop" :
			break;
		case "scoops" :
			break;
		case "slice" :
			break;
		case "slices":
			break;
		}
		return convertable;
	}
}