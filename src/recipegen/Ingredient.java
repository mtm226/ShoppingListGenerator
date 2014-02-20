package recipegen;

import java.util.ArrayList;

/*
 * Ingredient is implemented here to match ingredients in XML files. Ingredient has name, quantity, unit, parent and servings digit.
 */

public class Ingredient {

	private String name;
	ArrayList<String> parents = new ArrayList<>();
	private double qty;
	private String unit;
	private int servings;

	/*
	 * Constructors
	 */
	public Ingredient(String name, int servings, String parent) {
		this.name = name;
		this.servings = servings;
		this.parents.add(parent);
	}

	public Ingredient(String name, String qty, int servings, String parent) {
		this.name = name;
		/*
		 * The following part ensures that all ingredient amounts are presented as decimals. This is done by splitting up the possible fraction 
		 * number and dividing the digits to create a decimal number. The digits are multiplied based on servings value.
		 */
		if(qty.contains("/")){
			double a, b;
			String digits[] = qty.split("/");
			a = Double.parseDouble(digits[0]);
			b = Double.parseDouble(digits[1]);
			if(servings == 4){
				this.qty = (a/b)*2.5;
			}
			if(servings == 6){
				this.qty = (a/b)*1.7;
			}
		}
		else{
			if(servings == 4){
				this.qty = Double.parseDouble(qty)*2.5;
			}
			if(servings == 6){
				this.qty = Double.parseDouble(qty)*1.7;
			}
		}
		this.parents.add(parent);
	}

	public Ingredient(String name, String qty, String unit, int servings, String parent){
		this.name = name;

		/*
		 * The following part ensures that all ingredient amounts are presented as decimals. This is done by splitting up the possible fraction 
		 * number and dividing the digits to create a decimal number. The digits are multiplied based on servings value.
		 */
		if(qty.contains("/"))
		{
			double a, b;
			String digits[] = qty.split("/");
			a = Double.parseDouble(digits[0]);
			b = Double.parseDouble(digits[1]);
			if(servings == 4){
				this.qty = (a/b)*2.5;
			}
			if(servings == 6){
				this.qty = (a/b)*1.7;
			}
		}
		else{
			if(servings == 4){
				this.qty = Double.parseDouble(qty)*2.5;
			}
			if(servings == 6){
				this.qty = Double.parseDouble(qty)*1.7;
			}
		}

		this.unit = unit;	
		this.servings = servings;
		this.parents.add(parent);
	}

	/*
	 * Methods
	 */
	public String getName() {
		return name;
	}
	
	public void setQty(double updatedQty) {
		this.qty = updatedQty;
	}

	public void addQty(double addQty) {
		this.qty += addQty;
	}

	public double getQty() {
		return qty;
	}

	public void setUnit(String updatedUnit) {
		this.unit = updatedUnit;
	}
	
	public String getUnit() {
		return unit;
	}

	public void updateParent(String addParent){
		this.parents.add(addParent);
	}

	public ArrayList<String> getParent() {
		return parents;
	}
}
