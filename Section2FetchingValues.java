package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Section2FetchingValues {
	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger");
		//Text Input
	    System.out.println("textinput: " + driver.findElement(By.id("fetch_text_field")).getAttribute("value"));
	    
		//Select
	    System.out.println("select: " + driver.findElement(By.xpath("//option[@value='get_this']")).getText());
	    
	    //Radio Buttons
	    System.out.println("radio buttons: " + driver.findElement(By.xpath("//input[@checked='']")).getAttribute("value"));
	    
	    //checkbox
	    System.out.println("checkbox: " + driver.findElement(By.xpath("//input[@id='fetch_checkbox_field']")).getAttribute("checked"));
	    
		//Text Area
	    System.out.println("Text Area: " + driver.findElement(By.xpath("//textarea[@id='fetch_message']")).getText());
	    
		//Hyperlink
	    System.out.println("Hyperlink: " + driver.findElement(By.xpath("//a[@id='fetch_link']")).getText());
	    
		//DIV
	    System.out.println("DIV: " + driver.findElement(By.xpath("//div[@id='fetch_div']")).getText());


	    //username
	    String username = "My username is ";//remove this from before username string
	    
	    System.out.println("username: " + driver.findElement(By.xpath("//div[@id='username']")).getText().substring(username.length()));
	    //password
	    String prePassword = "i've been using ";//remove this from before password string
	    String postPassword = " as my password";//remove this from after password string
	    
	    System.out.println("password: " + driver.findElement(By.xpath("//div[@id='password']"))
	    		.getText().substring(prePassword.length()).substring(0, driver.findElement(By.xpath("//div[@id='password']"))
	    		.getText().substring(prePassword.length()).length() - postPassword.length()));
	    
	    
		//username
	    //System.out.println(driver.findElement(By.xpath("//div[@id='username']/strong")).getText());
	    //password
	    //System.out.println(driver.findElement(By.xpath("//div[@id='password']/strong")).getText());

	    
	    
	    
	    
	}
	

}
