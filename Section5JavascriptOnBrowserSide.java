package basic;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Section5JavascriptOnBrowserSide {
	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger");
	    
	    //5.A Javascript Executor
	    JavascriptExecutor js = (JavascriptExecutor)driver;	
	    js.executeScript("evalThis()");//evaluate the javascript in the webpage named evalThis() 
	    if(driver.findElement(By.xpath("//div[@id='eval_message']")).getText().equals("Oh no! I'm evaluated!")) {
	    	System.out.println("5.A: Pass");
	    }
	    else {
	    	System.out.println("5.A: Fail");
	    }
	    //5.B FireEvent
	    js = (JavascriptExecutor)driver;	
	    js.executeScript("fireeventExample()");//evaluate javascript and run the fireeventExample()
	    if(driver.findElement(By.xpath("//input[@id='fireevent_example_field']")).getAttribute("value").equals("NOOOOOO! You triggered my onclick()!")) {
	    	System.out.println("5.B: Pass");
	    }
	    else {
	    	System.out.println("5.B: Fail");
	    }
	    
	    
	    
	    
	}
}
