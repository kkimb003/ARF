package Advanced;

import java.util.Scanner;

import org.apache.bcel.generic.Select;
import org.openqa.jetty.html.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Section6AdvancedSelectors {
	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger/advanced.html");
	    
	    //6.A
	    driver.findElement(By.id("fake_select")).click();//select dropdown
	    driver.findElement(By.xpath("//div[text()= 'Grape']")).click();//select dropdown option
	    
	    //int temp = driver.findElement(By.cssSelector("[@class='header_row']/td")).size();
	    //System.out.println(driver.findElement(By.xpath(".//tr[@class='header_row']")).Size());
	    

		

	    
	    
	    Scanner reader = new Scanner(System.in);  //gets input from user
	    System.out.println("would you like to use the \"normal\" method or \"extra credit\" method?:");
	    String n = reader.nextLine();//gets input from user
	    reader.close();
	    
	    //6.B xpathAxes
	    if(n.equals("normal")) {
	    	
		    ////apple
		    String apple = driver.findElement(By.xpath("//td[text() = 'A1']//following::td[1]")).getText(); 
		    driver.findElement(By.xpath("//td[text() = 'A1']//following::input")).sendKeys(apple); 
		    
		    ////grape
		    String grape = driver.findElement(By.xpath("//td[text() = 'B2']//following::td[1]")).getText();
		    driver.findElement(By.xpath("//td[text() = 'B2']//following::input")).sendKeys(grape);
		    
		    ////Cherry
		    String cherry = driver.findElement(By.xpath("//td[text() = 'C3']//following::td[1]")).getText();
		    driver.findElement(By.xpath("//td[text() = 'C3']//following::input")).sendKeys(cherry);

	    }

    	//Extra credit
	    else if(n.equals("extra credit")){
	    	//System.out.println(driver.findElements(By.xpath("//tr[@class='header_row']/td")).size());
		    int rows = driver.findElements(By.xpath("//table[@id='xpath_axes_table']/tbody/tr")).size();//grabs the number of rows
		    int col = driver.findElements(By.xpath("//tr[@class='header_row']/td")).size();
		    int fruitCol = 0;
		    int fieldCol = 0;
		    //find the fruit column and save that location
		    for(int i = 1; i <= col; i++) {
		    	if(driver.findElement(By.xpath("//tr[@class='header_row']/td["+i+"]/strong")).getText().equals("Fruit")) {
		    		fruitCol = i;
		    	}
		    		
		    }
		    //find the field column and save that location
		    for(int i = 1; i <= col; i++) {
		    	if(driver.findElement(By.xpath("//tr[@class='header_row']/td["+i+"]/strong")).getText().equals("Field")) {
		    		fieldCol = i;
		    	}
		    		
		    }
		    //move through rows and put fruit text in field input
		    for(int i = 2; i <= rows; i++) {
		    	String fruit = driver.findElement(By.xpath(".//*[@id='xpath_axes_table']//tr["+i+"]/td["+fruitCol+"]")).getText();
		    	driver.findElement(By.xpath("//table[@id='xpath_axes_table']//tr["+ i +"]/td["+fieldCol+"]/input")).sendKeys(fruit);
		    }


	    }
	    
	    else {
	    	System.out.println("please enter either \"normal\" or \"extra credit\" next time");
	    }

	    
	}
}
