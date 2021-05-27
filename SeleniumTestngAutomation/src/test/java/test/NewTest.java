package test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import base.Locations;

import org.testng.annotations.BeforeTest;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;

public class NewTest {
	WebDriver driver;
	String driverPath = Locations.FirefoxdriverLoc;
	
  @BeforeTest
  public void beforeTest() {
	//Create firefox driver object
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("https://google.com");
  }

  @Test
  public void f() throws InterruptedException {
	    String searchKey = "Cafe de Colombia";
		WebElement searchText = driver.findElement(By.name("q"));
		//search value in google searchbox
		searchText.sendKeys(searchKey);
		//searchText.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		String testValue = searchText.getAttribute("value");
		System.out.println(testValue +" :::: "+searchKey);
		// searchText.clear();
		// Verify if the value in google search box is correct
		AssertJUnit.assertTrue(testValue.equalsIgnoreCase(searchKey));
		
		WebElement searchBtn = driver.findElement(By.name("btnK"));
		searchBtn.click();
		System.out.println("Titulo: "+driver.getTitle());
		assertTrue(driver.getTitle().equals("Cafe de Colombia - Buscar con Google"));
  }
  
  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
