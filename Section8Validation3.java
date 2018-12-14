package Advanced;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import qa.arf.JSON;

public class Section8Validation3 {
	public static void comparison(String comp1, String comp2, String section) {
		if(!comp1.equalsIgnoreCase(comp2)) {//if the names are not the same

	    	System.out.println(section + "FAIL");
	    	//System.out.println(comp1+ "=" + comp2);
	    }
	    else {//else output that they are equal
	    	System.out.println(section + "PASS");
	    	//System.out.println(comp1+ "=" + comp2);
	    }

		
		
	}
	public static void print(String str) {
		System.out.println(str);
	}
	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger/advanced.html");
	    
	    

	    JSON jsonVar;
		try {
			String file_path = "C:\\Users\\kkimball\\Downloads\\ex2_tc3.json";
			jsonVar = new JSON();
		    jsonVar.load(JSON.LoadType.FilePath, file_path);
		    new Select(driver.findElement(By.xpath("//select[@id='validation_tc_select']"))).selectByVisibleText("TC 3: Multiple Vehicles and NFEs");

		    //TC 1: Standard Data
		    //TC 2: Contextual Comparison
		    //TC 3: Multiple Vehicles and NFEs
		    //TC 4: Changing Coverage
		    //TC 5: Failure
		    //TC 6: Extra Vehicle, Extra NFE in JSON
		    //TC 7: Extra Vehicle, Extra NFE on screen

		    driver.findElement(By.xpath("//input[@id='tc_selector_button']")).click();//open test case
		    
		    //name compare//////////////////////////////////////////////////////////////////////////////////////////////////
		    String middle = "";//sets middle to empty at first
		    if (jsonVar.get("ph.middle_inital") != "") {//if middle name is not empty
		    	middle = jsonVar.get("ph.middle_inital") + ". ";//set middle to the middle initial and add a period
		    }
		    else {//if middle is empty
		    	middle = "";//set middle equal to empty
		    }
		    String compare1 = jsonVar.get("ph.first_name") + " " +  middle + jsonVar.get("ph.last_name");//set compare 1 to first_name middle_initial. last_name
		    String compare2 = driver.findElement(By.xpath("//input[@class='ph_name']")).getAttribute("value");//set compare2 to the name fiels in the danger room's test case
		    comparison(compare1, compare2, "Name Compare:	");
		    
		    //phone compare//////////////////////////////////////////////////////////////////////////////////////////////
		    compare1 = jsonVar.get("ph.phone");
		    String phone_1 = driver.findElement(By.xpath("//input[@class='phone1']")).getAttribute("value");
		    String phone_2 =driver.findElement(By.xpath("//input[@class='phone2']")).getAttribute("value");
		    String phone_3 =driver.findElement(By.xpath("//input[@class='phone3']")).getAttribute("value");
		    compare2 = "(" + phone_1 + ") " + phone_2 + "-" +  phone_3;
		    comparison(compare1, compare2, "Phone Compare:	");
		    
		    //discounts compare////////////////////////////////////////////////////////////////////////////////////////
		    //print(driver.findElement(By.xpath("//input[@class='discounts']")).getAttribute("checked"));
		    if(driver.findElement(By.xpath("//input[@class='discounts']")).getAttribute("checked") != null) {
		    	compare1 = "Yes";
		    }
		    else {
		    	compare1 = "No";
		    }
		    compare2 = jsonVar.get("ph.discounts");
		    comparison(compare1, compare2, "discounts:	");
		    
		    
		    if((driver.findElements(By.xpath("//div[@id='policy_details']/div")).size() - 4) == jsonVar.getSize("vehicles")) {
			    for( int i = 0; i < jsonVar.getSize("vehicles"); i++){
			    	//vin////////////////////////////////////////////////////////////////////////////////////////////////////
			    	String vin1 = driver.findElement(By.xpath("//div[@id='policy_details']/div[" + (i+3) + "]/div[2]/div[1]/input")).getAttribute("value");
			    	String vin2 = jsonVar.get("vehicles[" +i+"].vin");
			    	comparison(vin1,vin2, "VIN:		");
			    	
			    	//New or Used?//////////////////////////////////////////////////////////////////////////////////////////
			    	if(driver.findElement(By.xpath("//div[@id='policy_details']/div[" + (i+3) + "]/div[2]/div[2]/input[1]")).getAttribute("checked") != null) {
			    		compare1 = "New";
			    	}
			    	else {
			    		compare1 = "Used";
			    	}
			    	compare2 = jsonVar.get("vehicles[" +i+"].new_or_used");
			    	comparison(compare1, compare2, "Used?:		");
			    	
			    	//usage/////////////////////////////////////////////////////////////////////////////////////////////////
			    	compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+ (i+3) + "]/div[2]/div[3]/select/option[@selected='selected']")).getText();
			    	compare2 = jsonVar.get("vehicles[" +i+"].usage");
			    	comparison(compare1,compare2,"usage:		");
			    	System.out.println(jsonVar.getSize("vehicles["+i+"].nfes"));
			    	for(int j = 0; j<jsonVar.getSize("vehicles["+i+"].nfes"); j++) {
			    		
			    		//type//////////////////////////////////////////////////////////////////////////////////////////////
			    		compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(i+3)+"]/div[3]/div[2]/div["+ (j+2) + "]/div[2]")).getText();
			    		compare2 = jsonVar.get("vehicles[" +i+"].nfes[" +j+"].type");
			    		comparison(compare1,compare2,"Type:		");
			    		
			    		//value//////////////////////////////////////////////////////////////////////////////////////////////
			    		compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(i+3)+"]/div[3]/div[2]/div["+ (j+2) + "]/div[3]")).getText();
			    		compare2 = "$" + jsonVar.get("vehicles[" +i+"].nfes[" +j+"].value");
			    		comparison(compare1,compare2, "Value:		");
			    		
			    		//other_desc////////////////////////////////////////////////////////////////////////////////////////
			    		if(driver.findElement(By.xpath("//div[@id='policy_details']/div["+(i+3)+"]/div[3]/div[2]/div["+ (j+2) + "]/div[4]")).getText().equals("")) {
			    			compare1 = "[blank]";
			    		}
			    		else {
			    			compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(i+3)+"]/div[3]/div[2]/div["+ (j+2) + "]/div[4]")).getText();
			    		}
			    		compare2 = jsonVar.get("vehicles[" +i+"].nfes[" +j+"].other_desc");
			    		comparison(compare1,compare2,"other_desc:	");
			    		
			    	}
			    }
			    
			    for(int i = 0; i<jsonVar.getSize("coverages"); i++){
			    	for(int j = 0; j<jsonVar.getSize("coverages"); j++){
			    		//coverages///////////////////////////////////////////////////////////////////////////////////////
			    		if(driver.findElement(By.xpath("//div[@id='policy_details']/div["+(jsonVar.getSize("vehicles") + 4) +"]/div[" +(j+2)+"]/div[1]")).getText().equalsIgnoreCase(jsonVar.get("coverages["+i+"].coverage"))) {
			    			compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(jsonVar.getSize("vehicles") + 4) +"]/div[" +(j+2)+"]/div[1]")).getText();
					    	compare2 = jsonVar.get("coverages["+i+"].coverage");
					    	comparison(compare1,compare2,"Coverage:	");
					    	//limit///////////////////////////////////////////////////////////////////////////////////////
					    	compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(jsonVar.getSize("vehicles") + 4) +"]/div[" +(j+2)+"]/div[2]")).getText().replace(",", "");
					    	compare2 = "$" + jsonVar.get("coverages["+i+"].limit");
					    	comparison(compare1,compare2,"limit:		");
					    	//premium////////////////////////////////////////////////////////////////////////////////////
					    	compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(jsonVar.getSize("vehicles") + 4) +"]/div[" +(j+2)+"]/div[3]")).getText();
					    	compare2 = "$" + jsonVar.get("coverages["+i+"].premium");
					    	comparison(compare1,compare2,"Premium:	");
			    		}
			    	}
			    }
			    
			    compare1 = driver.findElement(By.xpath("//div[@id='policy_details']/div["+(jsonVar.getSize("vehicles") + 4) +"]/div[5]")).getText().replace("Total Premium: $", "");
			    compare2 = jsonVar.get("total_premium").replace(".00", "");
			    comparison(compare1,compare2,"Total Premium:	");
		    }
		    else {
		    	System.out.println("JSON and screen's number of vehicles do not match");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
