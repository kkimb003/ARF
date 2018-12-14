package Advanced;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import qa.arf.ARF;
import qa.arf.JSON;

public class Section8Validation2 {
	public static void policyHolderInfo(JSON jsonVar,ARF arf) {
		try {
		    /*-----------------
		    *	Name
		    *-------------------*/
		    //Initialize middle to empty
		    String middle = "";
		    //if middle name in json is not empty, step into 
		    if (jsonVar.get("ph.middle_inital") != "") {
		    	
		    	//Set middle to the middle initial and add a period
		    	middle = jsonVar.get("ph.middle_inital") + ". ";
		    }
		    
		    //Concatenates the different name elements to mirror the .json format
		    String nameActual = jsonVar.get("ph.first_name") + " " +  middle + jsonVar.get("ph.last_name");
		    String nameExpected = arf.getValue(By.xpath("//input[@class='ph_name']"));
	
	    	/*--------------------
		     *	Phone
		     *--------------------*/
		    //Collects the phone number from json
		    String phoneActual = jsonVar.get("ph.phone");
		    
		    //Collects the 3 different number elements 
		    String phoneOne = arf.getValue(By.xpath("//input[@class='phone1']"));
		    String phoneTwo = arf.getValue(By.xpath("//input[@class='phone2']"));
		    String phoneThree = arf.getValue(By.xpath("//input[@class='phone3']"));
		    
		    //Concatenates the 3 different number elements' strings
		    String phoneExpected = "(" + phoneOne + ") " + phoneTwo + "-" +  phoneThree;
	
		    /*--------------------
		     *	Discount
		     *--------------------*/
		    //Initialized to "blank"
		    String discountExpected = "blank";
		    String discountActual = "blank";
		    
		    //Checks the "yes" value in xpath to see if it is checked, otherwise the "no" value must be checked, there is no middle ground
		    if(arf.exists(By.xpath("//input[@class='discounts'][@checked='checked']"))){
		    	discountExpected = "Yes";
		    }
		    else {
		    	discountExpected = "No";
		    }
		    
		    discountActual = jsonVar.get("ph.discounts");
	
		    arf.bookmark("Policyholder Info");
	
	    	//Output to Table
		    String comparisonOutput = arf.output().table().validate("Name", nameExpected, nameActual);
		    comparisonOutput += arf.output().table().validate("Phone", phoneExpected, phoneActual);
		    comparisonOutput += arf.output().table().validate("Discount", discountExpected, discountActual);
			arf.printComparisonTable("Policyholder Info", comparisonOutput, true);
	
		}
	    catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void nonFactoryEquipment(int nfes, int xpathVehicleIndex, int vehicleIndex, ARF arf, JSON jsonVar) {
		try {
			int xpathNfes = nfes + 2; 
			/*--------------------
			 * 	Type
			 *--------------------*/
		    //initialized to "blank"
			String typeExpected = "blank";
			String typeActual = "blank";
			
	    	//Verify if exists in html
			if(arf.exists(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_type']"))) {
				typeExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_type']"));
			}
	
	    	//if the JSON file has no NFEs set the type to "blank"
			if(!jsonVar.get("vehicles[" + vehicleIndex + "].nfes").equals("[]")) {
				typeActual = jsonVar.get("vehicles[" + vehicleIndex + "].nfes[" + nfes +"].type");
			}
	
			/*--------------------
			 * 	Value
			 *--------------------*/	
		    //initialized to "blank"	
	    	String valueExpected = "blank";
	    	String valueActual = "blank";	 
	    	
			//Verify if exists in html
	    	if(arf.exists(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_value']"))) {
	    		valueExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_value']"));
	    	}
	
	    	//if the JSON file has no NFEs set the value to "blank"
			if(!jsonVar.get("vehicles[" + vehicleIndex + "].nfes").equals("[]")) {
				valueActual = "$" + jsonVar.get("vehicles[" + vehicleIndex + "].nfes[" + nfes + "].value");
			}
	
			/*--------------------
			 * 	Other Description
			 *--------------------*/
		    //Initialized to "blank"
			String descExpected = "blank";
			String descActual = "blank";
			
			//"Other Description" might be blank so check to see if it is empty or not
			if(arf.exists(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_other_desc']"))) {
	    		if(arf.getDriver().findElement(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_other_desc']")).getText().equals("")) {
	    			//if field is empty, set string to blank because the name inside the .json file is "[blank]" then remove brackets from .json
	    			descExpected = "blank";
	    		}
	    		else {
	    			descExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//div[" + xpathNfes + "]/div[@class='cell nfe_other_desc']"));
	    		}
			}
	
	    	//if the JSON file has no NFEs or it is set to [blank] set the type to "blank"
			if(!jsonVar.get("vehicles[" + vehicleIndex + "].nfes").equals("[]")) {
				if(jsonVar.get("vehicles[" + vehicleIndex + "].nfes[" + nfes + "].other_desc").equals("[blank]")){
	    			descActual = "blank";
	    		}
	    		else {
	    			descActual = jsonVar.get("vehicles[" + vehicleIndex + "].nfes[" + nfes + "].other_desc");//.replace("[", "").replace("]", "");
	    		}
			}
			
			
	    	//Output to Table
			String nfesOutput = arf.output().table().validate("Type", typeExpected, typeActual);
	    	nfesOutput += arf.output().table().validate("Value", valueExpected, valueActual);
	    	nfesOutput += arf.output().table().validate("Other Desc", descExpected, descActual);
			arf.printComparisonTable("Non-Factory Equipment " + (nfes + 1), nfesOutput);
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void vehicle(int vehicleIndex, JSON jsonVar,ARF arf) {
		try {
			//Add three to vehicle index to skip over the "Policyholder Info" title and "Policyholder Info" information and get to the vehicle's info
	    	int xpathVehicleIndex = vehicleIndex + 3;
	    	
	    	//Will be = or > 0 if the vehicle index in the html matches the index of the vehicle in the JSON if not then that means the HTML has more vehicles than the JSON and JSON goes out of bounds
	    	//for example if there are 2 vehicles in the JSON and 3 vehicles in the HTML, vehicle index will increase and json vehicle size will stay the same making "matchingIndecies" go like below
	    	//1-0 = 1 	TRUE
	    	//1-1 = 0	TRUE
	    	//1-2 = -1	FALSE
	    	int matchingIndecies = (jsonVar.getSize("vehicles") - 1) - vehicleIndex;
	    	
	    	/*--------------------
	    	 * 	VIN
	    	 *--------------------*/
		    //Initialized to "blank"
	    	String vinExpected = "blank";
	    	String vinActual = "blank";
	    	
	    	//Verify if exists in html
	    	if(arf.exists(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//input[@class='vin']"))){
	    		vinExpected = arf.getValue(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//input[@class='vin']"));
	    	}
	
	    	//Verify if exists in .json
	    	if(matchingIndecies >= 0) {
	    		vinActual = jsonVar.get("vehicles[" + vehicleIndex + "].vin");
	    	}
	
		    /*--------------------
	    	 * 	New or Used
	    	 *--------------------*/
		    //initialized to "blank"
		    String newExpected = "blank";
	    	String newActual = "blank";
	    	
	    	//if new is checked and it exists then step into
	    	if(arf.exists(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//input[@checked='checked'][@value='n']"))) {
	    		newExpected = "New";
	    	}
	    	//if new is not checked but still exists step into
	    	else if(arf.exists(By.xpath("//div[@id='policy_details']/div[" + xpathVehicleIndex + "]//input[@checked='checked'][@value='u']"))) {
	    		newExpected = "Used";
	    	}
	    	else {
	    		newExpected = "blank";
	    	}
	
	    	//Verify if exists in .json
	    	if(matchingIndecies >= 0) {
	
	    		newActual = jsonVar.get("vehicles[" + vehicleIndex + "].new_or_used");
	    	}
	    	
			/*--------------------
	    	 * 	Usage
	    	 *--------------------*/
		    //initialized to "blank"
		    String usageExpected = "blank";
	    	String usageActual = "blank";
	    	
	    	//Verify if exists in html
		    if(arf.exists(By.xpath("//div[@id='policy_details']/div["+ xpathVehicleIndex + "]//option[@selected='selected']"))){
		    	usageExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div["+ xpathVehicleIndex + "]//option[@selected='selected']"));
		    }
	
	    	//Verify if exists in .json
	    	if(matchingIndecies >= 0) {
	
	    		usageActual = jsonVar.get("vehicles[" + vehicleIndex + "].usage");
	    	}
	
	    	arf.bookmark("Vehicle " + (vehicleIndex + 1));
	
	    	//Output to Table
		    String vehicleOutput = arf.output().table().validate("VIN", vinExpected, vinActual);
			vehicleOutput += arf.output().table().validate("New or Used", newExpected, newActual);
	    	vehicleOutput += arf.output().table().validate("Usage", usageExpected, usageActual);
			arf.printComparisonTable("Vehicle " + (vehicleIndex + 1), vehicleOutput);
			
			int nfesMax = 0;
			
			//Takes the larger NFES between the html and json
			if(matchingIndecies < 0) {
				
			}
			else if(jsonVar.getSize("vehicles[" + vehicleIndex + "].nfes") <= arf.getDriver().findElements(By.xpath(".//div[@id='policy_details']/div[" + (vehicleIndex + 3) + "]/div[@class='validation_section border nfe']/div[@class='validation_section border']/div")).size() - 1) {
				nfesMax = arf.getDriver().findElements(By.xpath(".//div[@id='policy_details']/div[" + (vehicleIndex + 3) + "]//div[@class='validation_section border']/div")).size() - 1;
			}
			else {
				nfesMax = jsonVar.getSize("vehicles[" + vehicleIndex + "].nfes");
			}
	
	    	//Each vehicle might have multiple "Non-Factory Equipment" so loop through each
	    	for(int nfes = 0; nfes<nfesMax; nfes++) {
	    		
	    		nonFactoryEquipment( nfes, xpathVehicleIndex, vehicleIndex, arf, jsonVar);
				
	    	}
	    	arf.result("","",true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
	
	public static void coverages(int xpathCoveragesIndex, ARF arf, JSON jsonVar, int jsonCoveragesIndex) {
		try {
			int realXpathCoverages = xpathCoveragesIndex + 2;
			//once found, continue the process as if it were in the same position
			if(arf.fetchText(By.xpath("//div[@id='policy_details']/div[@class='validation_section border coverage']/div[" + realXpathCoverages + "]/div[1]")).equalsIgnoreCase(jsonVar.get("coverages[" + jsonCoveragesIndex + "].coverage"))){
	    		
				/*--------------------
	    		 * 	Coverages
	    		 *--------------------*/
				String coveragesExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div[@class='validation_section border coverage']/div[" + realXpathCoverages + "]/div[1]"));
				String coveragesActual = jsonVar.get("coverages[" + jsonCoveragesIndex + "].coverage");
	
	    		/*--------------------
	    		 * 	Limit
	    		 *--------------------*/
		    	String limitExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div[@class='validation_section border coverage']/div[" + realXpathCoverages + "]/div[2]")).replace(",", "");
		    	String limitActual = "$" + jsonVar.get("coverages[" + jsonCoveragesIndex + "].limit");
	
	
	    		/*--------------------
	    		 * 	Premium
	    		 *--------------------*/
		    	String premiumExpected = arf.fetchText(By.xpath("//div[@id='policy_details']/div[@class='validation_section border coverage']/div[" + realXpathCoverages + "]/div[3]"));
		    	String premiumActual = "$" + jsonVar.get("coverages[" + jsonCoveragesIndex + "].premium");
	
	
		    	//Output to Table
		    	String coverageOutput = arf.output().table().validate("Coverage", coveragesExpected, coveragesActual);
		    	coverageOutput += arf.output().table().validate("Limit", limitExpected, limitActual);
		    	coverageOutput += arf.output().table().validate("Premium", premiumExpected, premiumActual);
				arf.printComparisonTable("Coverage " + (jsonCoveragesIndex + 1), coverageOutput);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void totalPremium(ARF arf, JSON jsonVar) {
		try {
		/*--------------------
		 * 	Total Premium
		 *--------------------*/
	    String totalExpected = arf.fetchText(By.xpath("//div[@id='policy_details']//div[contains(text(), 'Total Premium')]")).replace("Total Premium: $", "");
	    String totalActual = jsonVar.get("total_premium").replace(".00", "");

    	//Output to Table
		String totalPremiumOutput = arf.output().table().validate("Coverage", totalExpected, totalActual);
		arf.printComparisonTable("Total Premium", totalPremiumOutput, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		//Creating a new arf element
		ARF arf = new ARF(args);
		
		if (arf.isDevMode()) {
			arf.setId("1");	
			arf.setURL("http://arf/danger/advanced.html");
			arf.selectBrowserDevMode(ARF.BrowserType.Firefox);		
		} 
		arf.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		arf.getDriver().manage().window().maximize();
		
		//Opens Danger Room (http://arf/danger/advanced.html)
		arf.navigate(arf.getURL());
		
		//Initialize array to size 7
	    String testCases[] = new String[7];
	    //Populate array with all 7 test cases
	    testCases[0] = "TC 1: Standard Data";
	    testCases[1] = "TC 2: Contextual Comparison";
		testCases[2] = "TC 3: Multiple Vehicles and NFEs";
		testCases[3] = "TC 4: Changing Coverage";
		testCases[4] = "TC 5: Failure";
		testCases[5] = "TC 6: Extra Vehicle, Extra NFE in JSON";
		testCases[6] = "TC 7: Extra Vehicle, Extra NFE on screen";
	    JSON jsonVar;
		try {
			//Sets the test case number to the value associated with the test case run in arf then subtract one to get the correct array index
			int testCaseNum = Integer.parseInt(arf.getId()) - 1;
			
			//Opens the .json test case file on my local computer
			String file_path = "C:\\Users\\kkimball\\Downloads\\ex2_tc" + (testCaseNum + 1) + ".json";
			jsonVar = new JSON();
		    jsonVar.load(JSON.LoadType.FilePath, file_path);
		    
		    //Selects the test case from the dropdown
		    arf.set(By.xpath("//select[@id='validation_tc_select']"),testCases[testCaseNum]);
		    //press the go button to open the test case
		    arf.click(By.xpath("//input[@id='tc_selector_button']"));
		    
		    policyHolderInfo(jsonVar,arf);

		    int vehicleMax = 0;
		    
		    //Gets the max number of vehicles between the HTML and the JSON 
		    //(xpath -4 because we need to remove "Policy Holder title, Policy holder body, Covreages title, and Coverages Body)
		    if((arf.getDriver().findElements(By.xpath("//div[@id='policy_details']/div")).size() - 4) >= jsonVar.getSize("vehicles")) {
		    	vehicleMax = (arf.getDriver().findElements(By.xpath("//div[@id='policy_details']/div")).size() - 4);
		    }
		    else{
		    	vehicleMax = jsonVar.getSize("vehicles");
		    }
	    
	    	//Loops through the different vehicles
		    for(int vehicleIndex = 0; vehicleIndex < vehicleMax; vehicleIndex++){
		    	
		    	vehicle(vehicleIndex, jsonVar, arf);
	    	
	    	}
		    arf.bookmark("Coverages");
		    
			//Coverages may be in a different order, need to loop through and find which one matches with which (TC 4)
		    for(int jsonCoveragesIndex = 0; jsonCoveragesIndex<jsonVar.getSize("coverages"); jsonCoveragesIndex++){
		    	for(int xpathCoveragesIndex = 0; xpathCoveragesIndex<jsonVar.getSize("coverages"); xpathCoveragesIndex++){
		    		
		    		coverages( xpathCoveragesIndex, arf, jsonVar, jsonCoveragesIndex);
	    		
		    	}
		    }
	    
		    totalPremium(arf, jsonVar);
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		arf.stopTest();
	}
	
}
