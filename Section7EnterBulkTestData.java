package Advanced;

import qa.arf.*;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class Section7EnterBulkTestData {
	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger/advanced.html");
	    
	    

	    JSON jsonVar;
		try {
			String file_path = "C:\\Users\\kkimball\\Downloads\\ex1_tc5.json";
			jsonVar = new JSON();
		    jsonVar.load(JSON.LoadType.FilePath, file_path);
		    if(jsonVar.getSize("drivers") == 0) {
		    	System.out.println("no data");//check if empty
		    }
		    else {
			    for(int i = 0; i < jsonVar.getSize("drivers"); i++) {
			    	String firstName = jsonVar.get("drivers[" + i +"].first_name"); 
			    	String lastName = jsonVar.get("drivers[" + i +"].last_name"); 
			    	String age = jsonVar.get("drivers[" + i +"].age"); 
				    driver.findElement(By.xpath("//a[@id='add_driver']")).click();//add new driver
				    //arf.click();
				    driver.findElement(By.xpath("//div[2]/div[" + (i + 1) +"]//input[@class='first_name']")).sendKeys(firstName);//add firstname
				    driver.findElement(By.xpath("//div[2]/div[" + (i + 1) +"]//input[@class='last_name']")).sendKeys(lastName);//add lastname
				    new Select(driver.findElement(By.xpath("//div[2]/div[" + (i + 1) +"]//select[@class='age']"))).selectByVisibleText(age);//add age from dropdown
				    for(int j = 0; j <jsonVar.getSize("drivers[" + i + "].violations");j++) {
				    	driver.findElement(By.xpath("//div[2]/div["+ (i + 1) +"]//a[@class='add_violation']")).click();//add new violation
				    	String vioType =  jsonVar.get("drivers[" + i +"].violations["+ j +"].violation_type");
				    	String vioYear = jsonVar.get("drivers[" + i +"].violations["+ j +"].violation_year");
				    	String vioPath = "//div[2]/div[" + (i + 1) +"]//div[@class='violations']/div[" + (j + 1) + "]//select[@class='violation_type']";
					    new Select(driver.findElement(By.xpath(vioPath))).selectByVisibleText(vioType);//addviolation type
					    String vioPath2 = "//div[2]/div[" + (i + 1) +"]//div[@class='violations']/div[" + (j + 1) +"]//select[@class='violation_year']";
					    new Select(driver.findElement(By.xpath(vioPath2))).selectByVisibleText(vioYear);//add violation year
				    }
				    
			    	
			    }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}


}
