package basic;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait; 
import org.openqa.selenium.TimeoutException;

public class Section3WaitFor {
		private static final int WAIT_TIME = 10;
		
		
		public static void main(String args[]) {
			
		    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
		    RemoteWebDriver driver = new FirefoxDriver();
		    driver.get("http://arf/danger");
		    
		    
		    //waitForSelector
		    driver.findElement(By.xpath("//a[@id='waitForSelectorLink']")).click();//find path and click
		    WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME);
		    wait.until(ExpectedConditions.elementToBeClickable(By.id("waitForSelectorCheckBox")));//waits for element to appear
		    driver.findElement(By.id("waitForSelectorCheckBox")).click();//clicks said element
		    
		    //waitUntilVisible
		    driver.findElement(By.id("waitUntilVisibleLink")).click();
		    wait = new WebDriverWait(driver, WAIT_TIME);
		    wait.until(ExpectedConditions.elementToBeClickable(By.id("waitUntilVisibleCheckBox")));
		    driver.findElement(By.id("waitUntilVisibleCheckBox")).click();
		    
		    //WaitForText()
		    driver.findElement(By.id("waitForTextLink")).click();
		    wait = new WebDriverWait(driver, WAIT_TIME);
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("waitForTextHolder")));
		    System.out.println(driver.findElement(By.xpath("//div[@id='waitForTextHolder']")).getText());
		  
		    //WaitForever
		    try {
		    	driver.findElement(By.id("waitForeverLink")).click();//find path and click
		    	wait = new WebDriverWait(driver, WAIT_TIME);//remove webdriverwait in front after uncommenting
		    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("waitForTextHolder11")));//waits until visible(will never be visible)
			} 
		    catch(TimeoutException e) {
				//e.printStackTrace();
		    	
				System.out.println(e.getLocalizedMessage());//always hits
			}
		    
		}
	
}
