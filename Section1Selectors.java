package basic;

import java.util.Scanner;

//import org.apache.bcel.generic.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class Section1Selectors {


	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger");
	    //Text input
		driver.findElement(By.xpath("//input[@id='text_field']")).sendKeys("DANGER!");
		
	    //select dropdown
		new Select(driver.findElement(By.xpath("//select[@id='select_field']"))).selectByValue("select_this");
		
	    //Radio Buttons
		driver.findElement(By.xpath("//input[@value='select_this']")).click();
		
	    //Checkbox
		driver.findElement(By.xpath("//input[@id='checkbox_field']")).click();
		
	    //Text Area
		 driver.findElement(By.xpath("//textarea[@id='textarea_field']")).sendKeys("DANGER AREA!");
		 
	    //hyperlink
		driver.findElement(By.xpath("//a[@id='hyperlink']")).click();
		    
	    //form button
	    driver.findElement(By.xpath("//input[@id='basic_button']")).click();
	    
	    Scanner reader = new Scanner(System.in);  // Reading from System.in
	    System.out.println("would you like to use the \"xpath\" method or \"css\" method?:");
	    String n = reader.nextLine();//ask for user input
	    reader.close();
	    
	    if(n.equals("xpath")) {
	    	//xpath		    
	    	//ID Checkbox xpath
		    driver.findElement(By.xpath("//input[@id='with_id']")).click();
		    
		    //CLASS Checkbox xpath
		    driver.findElement(By.xpath("//input[@class='with_class']")).click();
		    
		    //NAME Checkbox xpath
		    driver.findElement(By.xpath("//input[@name='with_name']")).click();
		    
		    //Attribute Checkbox xpath
		    driver.findElement(By.xpath("//input[@with='attribute']")).click();
		    
		    //starts_with Checkbox xpath
		    driver.findElement(By.xpath("//input[starts-with(@class, 'UseStartsWith')]")).click();
		    
		    //Contains Checkbox xpath
		    driver.findElement(By.xpath("//input[contains(@class, 'UseContains')]")).click();
		    
		    //Multiple Attributes Checkbox xpath
		    driver.findElement(By.xpath("//input[@type='checkbox'][@shape='round'][@color='purple'] [@fruit='grape']")).click();
		    
		    //Changing Root Node Checkbox xpath
		    driver.findElement(By.xpath("//div [@id='nested_div']/input[@class='field']")).click();
		    
		} 
	    else if(n.equals("css")) {
			//css
			//ID Checkbox css
			driver.findElement(By.cssSelector("input[id='with_id']")).click();
			
		    //CLASS Checkbox css
			driver.findElement(By.cssSelector("input[class='with_class']")).click();
			
		    //NAME Checkbox css
			driver.findElement(By.cssSelector("input[name='with_name']")).click();
			
		    //Attribute Checkbox css
			driver.findElement(By.cssSelector("input[with='attribute']")).click();
			
		    //starts_with Checkbox css
			driver.findElement(By.cssSelector("input[class^= 'UseStartsWith']")).click();
			
			//ends_with Checkbox CSS
			driver.findElement(By.cssSelector("input[class$='EndsWith']")).click();
			
		    //Contains Checkbox css
			driver.findElement(By.cssSelector("input[class*='UseContains']")).click();
			
		    //Multiple Attributes Checkbox css
			driver.findElement(By.cssSelector("input[type='checkbox'][shape='round'][color='purple'][fruit='grape']")).click();
			
		    //Changing Root Node Checkbox css
			driver.findElement(By.cssSelector("div [id='nested_div'] > input[class='field']")).click();
		}
		else {
		    System.out.println("please enter either \"xpath\" or \"css\" next time");
		}
	
	    //text selector
		driver.findElement(By.xpath("//div[text()='Hello!']")).click();
	    //Partial text selector
		System.out.println(driver.findElement(By.xpath("//div[contains(text(), 'Good Bye')]")).getText());

	}
}


	
	

