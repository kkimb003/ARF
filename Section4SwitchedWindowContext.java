package basic;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.TakesScreenshot;

public class Section4SwitchedWindowContext {
	public static void screenshot(String save_loc, RemoteWebDriver driver) {
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);//takes a screenshot of entire page
	    
	    try {
			FileUtils.copyFile(src, new File(save_loc));//saves screenshot
		} 
	    catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String args[]) {
	    System.setProperty("webdriver.gecko.driver", "c:/selenium/webdrivers/geckodriver.exe");
	    RemoteWebDriver driver = new FirefoxDriver();
	    driver.get("http://arf/danger");
	    
	    //4.A overlay/////////////////////////////////////////////////////////////////
	    driver.findElement(By.id("show_overlay")).click();//opens overlay


	    screenshot("C:/selenium/screenshot1.png", driver);//calls screenshot function
	    driver.findElement(By.id("close_overlay")).click();//close overlay

	    //4.B pop up window///////////////////////////////////////////////////////////
	    driver.findElement(By.xpath("//a[text()='[Click To Show Pop-Up]']")).click();
	    
	    String parentHandle = driver.getWindowHandle(); //get the current window handle

	    for (String winHandle : driver.getWindowHandles()) {
	    	driver.switchTo().window(winHandle); //switch focus of WebDriver to the next found window handle (newly opened window)
	    }

	    screenshot("C:/selenium/popup.png", driver);
	    driver.close(); // close newly opened window when done with it
	    driver.switchTo().window(parentHandle); // switch back to the original window
	    
	    //4.C iFrame/////////////////////////////////////////////////////////////////
	    driver.switchTo().frame("eye_frame");//switch frames

	    System.out.println(driver.findElement(By.xpath("//textarea[@id='iframe_textarea']")).getText());//get text inside the frame
	}
}
