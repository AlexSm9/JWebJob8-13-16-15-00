package gov.lbl.webjob.test;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity("cars")
public class Car {

	@Id private String id;

	public String color;
	public int wheels;
	private float horsepower;
	
	public Car(String color, int wheels, float horsepower){
		this.color = color;
		this.wheels = wheels;
		this.horsepower = horsepower;
		id = new ObjectId().toString();
	}
	
	public Car(){
		//*REQUIRED* empty constructor for MongoDB Morphia
	}
	
	public String getId() {
		return id;
	}
	
	public float getHorsepower() {
		return horsepower;
	}
	
	public String toString(){
		return "{id: " + id + ", color: " + color + ", wheels: " + wheels + ", horsepower: " + horsepower + "}";
	}
}
