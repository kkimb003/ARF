package Advanced;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import qa.arf.*;
public class Section7EnterBulkTestData2 {
	public static void addDriver(int driverNum, ARF arf, JSON jsonVar) {
		String output = "";
    	//collect data about first, last name, and age
    	String firstName;
		try {
			firstName = jsonVar.get("drivers[" + driverNum + "].first_name");
		
	    	String lastName = jsonVar.get("drivers[" + driverNum + "].last_name"); 
	    	String age = jsonVar.get("drivers[" + driverNum + "].age"); 
			arf.waitForSelector(By.xpath("//a[@id='add_driver']"));
			//arf.setStepLabel("Adding Driver");
			
			//Click add driver button
		    arf.click(By.xpath("//a[@id='add_driver']"));
		    //arf.setStepLabel("Adding First Name, Last Name, and Age");
		    
		    //input first name
		    arf.set(By.xpath("//div[@class='drivers']/div[" + (driverNum + 1) + "]//input[@class='first_name']"),firstName);
		    
		    //input last name
		    arf.set(By.xpath("//div[@class='drivers']/div[" + (driverNum + 1) + "]//input[@class='last_name']"),lastName);
		    
		    //input age
		    arf.set(By.xpath("//div[@class='drivers']/div[" + (driverNum + 1) + "]//select[@class='age']"),age);
		    
		    //output to arf
		    output += arf.output().block().add("First Name", firstName);
			output += arf.output().block().add("Last Name", lastName);
			output += arf.output().block().add("Age", age);
			arf.printInputBlock("Driver #" + (driverNum + 1), output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void addViolation(int driverNum, ARF arf, JSON jsonVar, int violationNum) {
		String output = "";
	    //arf.setStepLabel("Click");
	    
	    //click add violations button
    	arf.click(By.xpath("//div[@class='drivers']/div[" + (driverNum + 1) + "]//a[@class='add_violation']"));
    	
    	//collect info about the violation
		try {
			
			String vioType = jsonVar.get("drivers[" + driverNum + "].violations[" + violationNum + "].violation_type");
	    	String vioYear = jsonVar.get("drivers[" + driverNum + "].violations[" + violationNum + "].violation_year");
	    	String vioPath = "//div[@class='drivers']/div[" + (driverNum + 1) + "]//div[@class='violations']/div[" + (violationNum + 1) + "]//select[@class='violation_type']";
	    	
	    	//input violation type
		    arf.set(By.xpath(vioPath),vioType);
		    String vioPath2 = "//div[@class='drivers']/div[" + (driverNum + 1) + "]//div[@class='violations']/div[" + (violationNum + 1) + "]//select[@class='violation_year']";
		    
		    //input violation year
		    arf.set(By.xpath(vioPath2),vioYear);
		    //arf.setStepLabel("Adding Violation");
		    
		    //output to arf
		    output += arf.output().block().add("Violation Type", vioType);
			output += arf.output().block().add("Violation Year", vioYear);
			arf.printInputBlock("Violation #" + (violationNum + 1), output);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	

	public static void main(String[] args)
	{
		//start a new arf session
		ARF arf = new ARF(args);
		
		if (arf.isDevMode()) {
			arf.setId("1");	// custom package
			arf.setURL("http://arf/danger/advanced.html");
			arf.selectBrowserDevMode(ARF.BrowserType.Firefox);		
		} 
		arf.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		arf.getDriver().manage().window().maximize();

		JSON jsonVar;
		//run through the 7 test cases
			//section off each test case
			int tc = Integer.parseInt(arf.getId());
			arf.navigate(arf.getURL());
			try {
				String filePath = "C:\\Users\\kkimball\\Downloads\\ex1_tc" + tc + ".json";
				jsonVar = new JSON();
			    jsonVar.load(JSON.LoadType.FilePath, filePath);
		    	//check if empty
			    if(jsonVar.getSize("drivers") == 0) {
			    	arf.result("Test Case is Empty");
			    }
			    
			    else {
				    for(int driverNum = 0; driverNum < jsonVar.getSize("drivers"); driverNum++) {
				    	arf.bookmark("Driver #" + (driverNum + 1));
				    	addDriver(driverNum,arf,jsonVar);

					    for(int violationNum = 0; violationNum <jsonVar.getSize("drivers[" + driverNum + "].violations"); violationNum++) {
					    	addViolation(driverNum,arf,jsonVar,violationNum);

					    }
					    arf.result("","",true);
				    }
			    }
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			//screenshot the finished page
		    
		 
		arf.stopTest();
	
		
	}

}
