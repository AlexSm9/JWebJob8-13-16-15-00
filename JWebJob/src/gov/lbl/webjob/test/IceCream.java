package gov.lbl.webjob.test;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("icecream")
public class IceCream {

	@Id private String id;
	
	public String name; 
	public Boolean isSoftServe;
	public ArrayList<String> toppings;
	public ArrayList<String> flavors;
	public float price;
	public Car carSticker = null;
	
	public String toString(){
		String sss = "";
		String cs = "";
		if(isSoftServe){
			sss = ", Soft Serve" ;
		}
		if(carSticker != null){
			cs = ", Car: " + carSticker.toString();
		}
		return "{id: " + id + ", name: " + name + ", flavors: " + flavors + ", toppings: " + toppings + sss + cs + ", price: " + price + "}";
	}
	
	public IceCream(String name, ArrayList<String> flavors){
		this.name = name;
		this.flavors = flavors;
		id = new ObjectId().toString();
	}
	
	public IceCream(String id, String name, ArrayList<String> flavors){
		this.name = name;
		this.flavors = flavors;
	}
	
	public IceCream(){
		//*REQUIRED* empty constructor for MongoDB Morphia
	}
	
	public void addToppings(ArrayList<String> toppings){
		this.toppings = toppings;
	}
	
	public float returnPrice(){
		price = calcPrice();
		return price;
	}
	
	private float calcPrice(){
		float basePrice = (float) (0.95*toppings.size() + 1.99*flavors.size());
		if(isSoftServe == true){
			basePrice *= 1.05;
		}
		return basePrice;
	}
	
	public void setSoftServe(boolean bool){
		isSoftServe = bool;
	}

	public Car getCarSticker() {
		return carSticker;
	}

	public void setCarSticker(Car carSticker) {
		this.carSticker = carSticker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
