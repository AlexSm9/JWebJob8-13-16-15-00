package gov.lbl.webjob.vc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;

import gov.lbl.webjob.db.DataProvider;
import gov.lbl.webjob.test.Car;
import gov.lbl.webjob.test.IceCream;

/**
 * Servlet implementation class JobInputVC
 */
@WebServlet("/JobInputVC")
public class JobInputVC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JobInputVC() {
        super();
        //Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DataProvider dp = DataProvider.getInstance();
		
		//carDatabaseTest(dp);
		
		/// JSTL's <c:forEach ... > can use any iterable list of objects, List<Object> is used here
		List<IceCream> iterableItemList = iceCreamDatabaseTest(dp); 
		
		/// NOTE: Calling a specific object value through JSTL's ${Object} requires an appropriate getter for that value
		
		request.setAttribute("iterableItemList", iterableItemList);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/JobInput.jsp"); 
		rd.forward(request, response);
	}
	
	private ArrayList<String> makeInitialIceCreamDatabase(DataProvider dp){
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> flavorList; 
		ArrayList<String> toppingList;
		IceCream ice;
		
		flavorList = new ArrayList<String>(Arrays.asList("vanilla", "coconut", "peach"));
		toppingList = new ArrayList<String>(Arrays.asList("strawberries", "coconut shavings", "whipped cream"));
		ice = new IceCream("57a904de289d5109994f8b10", "sunset suave", flavorList);
		ice.addToppings(toppingList);
		ice.setSoftServe(true);
		ice.returnPrice();
		al.add((String) dp.saveGetID(ice));
		
		flavorList = new ArrayList<String>(Arrays.asList("chocolate", "coffee"));
		toppingList = new ArrayList<String>(Arrays.asList("oreos", "chocolate chips"));
		ice = new IceCream("57a904de289d5109994f8b11", "choco dream", flavorList);
		ice.addToppings(toppingList);
		ice.setSoftServe(true);
		ice.returnPrice();
		al.add((String) dp.saveGetID(ice));
		
		flavorList = new ArrayList<String>(Arrays.asList("lime", "lemon", "orange"));
		toppingList = new ArrayList<String>(Arrays.asList("lemon peel", "coconut shavings", "waffle"));
		ice = new IceCream("57a904de289d5109994f8b12", "citrus sherbert", flavorList);
		ice.addToppings(toppingList);
		ice.setSoftServe(false);
		ice.returnPrice();
		al.add((String) dp.saveGetID(ice));
		
		flavorList = new ArrayList<String>(Arrays.asList("strawberry", "orange", "blueberry"));
		toppingList = new ArrayList<String>(Arrays.asList("sprinkles", "fudge", "cherry"));
		ice = new IceCream("57a904de289d5109994f8b13", "rainbow explosion", flavorList);
		ice.addToppings(toppingList);
		ice.setSoftServe(false);
		ice.setCarSticker(new Car("orange", 4, 0));
		ice.returnPrice();
		al.add((String) dp.saveGetID(ice));
		
		flavorList = new ArrayList<String>(Arrays.asList("vanilla"));
		toppingList = new ArrayList<String>(Arrays.asList("fudge", "whipped cream"));
		ice = new IceCream("57a904de289d5109994f8b15", "plain vanilla", flavorList);
		ice.addToppings(toppingList);
		ice.setSoftServe(true);
		ice.returnPrice();
		al.add((String) dp.saveGetID(ice));
		
		return al;
	}
	
	private List<IceCream> iceCreamDatabaseTest(DataProvider dp){
		//ArrayList<String> iceIds = makeInitialIceCreamDatabase(dp);
		ArrayList<String> iceIds = new ArrayList<String>(Arrays.asList("57a904de289d5109994f8b10", "57a904de289d5109994f8b11", "57a904de289d5109994f8b12", "57a904de289d5109994f8b13", "57a904de289d5109994f8b15"));
		System.out.println(iceIds);
		ArrayList<IceCream> iceCreams = new ArrayList<IceCream>();
		
		for(int i=0; i<iceIds.size(); i++){
			String id = iceIds.get(i);
			System.out.print("IceCream in position " + (i+1) + " of database is: ");
			System.out.println(dp.findById(IceCream.class, id));
		}
		
		return dp.getAllAsList(IceCream.class);
	}
	
	
	private String makeInitialCarDatabase(DataProvider dp){
		Car accord = new Car("silver", 4, 250.2f);
		Car pilot = new Car("gold", 4, 320.8f);
		Car buggy = new Car("grey", 3, 15.2f);
		Car hayuna = new Car("pink", 4, 600f);
		Car mack = new Car("white", 8, 400f);
		dp.save(accord);
		dp.save(pilot);
		dp.save(buggy);
		dp.save(hayuna);
		return (String) dp.saveGetID(mack);
	}
	
	private void carDatabaseTest(DataProvider dp){
		
		Car foundCar;
		
		/*
		String ID = makeInitialCarDatabase(dp);
		System.out.println(ID);
		foundCar = dp.findById(Car.class, ID);
		System.out.println("foundCar: " + foundCar);
		*/
		
		foundCar = dp.findOne(Car.class, "wheels", 3);
		System.out.println(foundCar);
		if(foundCar!=null){
			foundCar.wheels = 2;
			dp.save(foundCar);
		}else{
			System.out.println("No Car found!");
		}
		foundCar = dp.findOne(Car.class, "color", "grey");
		System.out.println(foundCar);
		
		List<Car> carlist = dp.findList(Car.class, "horsepower >=", 3000f);
		System.out.println(carlist);
		
		//New functions test
		dp.deleteKey(Car.class, "wheels", 2, "color");
		foundCar = dp.findOne(Car.class, "wheels", 2);
		System.out.println(foundCar);
		
		dp.updateKey(Car.class, "wheels", 2, "wheels", 3);
		foundCar = dp.findOne(Car.class, "wheels", 3);
		System.out.println(foundCar);
		
		dp.deleteEntity(Car.class, "wheels", 8);
	}
	
}
