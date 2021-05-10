package base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




/**
 * Es una clase "wrapper" para separar los objetos de la pagina y
 * los comandos selenium de los test. También se le llama utility class
 * aca estaran las funciónes basicas de Selenium
 */

public class Base {

	/**
	 * La variable driver se emplea para acceder a la sesion actual donde se abrira una ventana
	 * con el navegador y se emplearan los metodos basicos de Selenium
	 * 
	 */
	private static WebDriver driver = null;
	
	
	/**
	 * Este metodo es el constructor de la clase Base
	 * @param driver es el parametro que recibe
	 */
	public Base (WebDriver driver)
	{
	 this.driver = driver;	
	}
	
	
	/**
	 * Metodo que inicializa el navegador Chrome
	 * @return driver con el navegador Chrome cargado
	 */
	public WebDriver chromeDriverConnection()
	{
		// Inicializa Navegador Chrome y retorna driver
		System.out.println(Locations.ChromedriverLoc);
		System.setProperty("webdriver.chrome.driver",Locations.ChromedriverLoc);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--incognito");
		driver = new ChromeDriver(options);
		return driver;
	}
	
	/**
	 * Metodo que inicializa el navegador Firefox
	 * @return driver con el navegador Mozilla Firefox cargado 
	 */
	
	public static WebDriver firefoxDriverConnection()
	{
		System.setProperty("webdriver.gecko.driver", Locations.FirefoxdriverLoc);
		driver = new FirefoxDriver();	
		return driver; 
	}
	
	/**
	 *  Localiza WebElement segun locator
	 * @param locator localizador del elemento
	 * @return El elemento web localizado
	 */
	public WebElement findElement(By locator)
	{
		return driver.findElement(locator);
		
	}
	/**
	 *  Captura texto en el objeto localizado
	 * @param inputText es el texto a digitar
	 * @param locator es el localizador del objeto
	 */
	public void type(String inputText, By locator)
	{
		WebDriverWait wait =new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	    driver.findElement(locator).sendKeys(inputText);	
	}
	
	/**
	 * 
	 *  Da click en el objeto localizado
	 *  @param locator es el localizador del objeto
	 */  
	public void click(By locator)
	{
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait =new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).click();

	}
	/**
	 * Abre la pagina que se le indique en la cadena url
	 * @param url es la cadena que guarda la url de la página a visitar
	 */
	public void visit(String url)
	{
	    driver.get(url);
	    driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS) ;
	}
	/**
	 * Informa si un objeto pudo ser localizado(visualizado) en una página
	 * @param locator es el localizador del objeto
	 * @return true o false dependiendo de si ubica o no el objeto
	 */
	public Boolean isDisplayed(By locator)
	{
		try {
			   return driver.findElement(locator).isDisplayed();
		}
		catch(org.openqa.selenium.NoSuchElementException e)
		{
			return false;
		}
	}
	
	/**
	 * Esta función retorna el texto del elemento
	 * @param element  es el elemento web
	 * @return El texto contenido en el elemento
	 */
	 
	public String getText(WebElement element)
	{
		return element.getText();
	}
	
	 
	/**
	 * Esta función retorna el texto del elemento referenciado por locator
	 * @param locator  es el localizador del elemento
	 * @return El texto contenido en el elemento
	 */
	
	public String getText(By locator)
	{
		WebDriverWait wait =new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return driver.findElement(locator).getText();
	}
	
	
	/**
	 * Esta función retorna la lista de elementos referenciada por locator
	 * 
	 * @param locator es el localizador del elemento
	 * @return Lista de elementos retornados
	 */
	
	public List<WebElement> findElements(By locator)
	{
		WebDriverWait wait =new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return driver.findElements(locator);
	}
	
	/**
	 * Este método valida que un texto se encuentre en un objeto web buscandolo en la pagina con xpath
	 * @param driver1 recibe un objeto driver(sesión actual) 
	 * @param texto que sera buscado en la página
	 * @return boolean para saber si halló o no el texto en la página actual
	 */
	public static boolean validateText(WebDriver driver1,String texto) {
		WebDriverWait wait =new WebDriverWait(driver1, 30);
				
		String textoorden   = "//*[contains(.,"+"'"+texto+"'"+")]";
		System.out.println(textoorden);
		try {
			 wait.until(ExpectedConditions.elementToBeClickable(By.xpath(textoorden)));
			 WebElement element = driver1.findElement(By.xpath(textoorden));
			if (element  == null) 
			{ return false;}
			else 
			{ return true;}
		}
		catch (Exception e) {return false;}
	} 
	
	/**
	 * Esta función recibe un driver y retorna el título de la página
	 * @param driver es el parametro que recibe
	 * @return Título de la página
	 */
	public String returnTitle(WebDriver driver)
	{
		return driver.getTitle();
		
	}
	
	/**
	 * Esta función hace scroll vertical hacia abajo por 1000 pixels
	 * @param driver recibe un elemento driver que tiene la sesion actual
	 */
	public void scrollDownVertical(WebDriver driver)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
        // This  will scroll down the page by  1000 pixel vertical		
        js.executeScript("window.scrollBy(0,1000)");
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
	}
	
	/**
	 * Esta función hace scroll vertical hacia abajo hasta ubicar un objeto
	 * 
	 * @param driver recibe un driver con la sesión actual
	 * @param locator recibe un localizador del objeto
	 */
	public void scrollVisibleObject(WebDriver driver, By locator)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
        // Espera a que el elemento sea visible       
		WebDriverWait wait =new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		WebElement Element =  driver.findElement(locator);
        // Hace scroll hasta que el elemento sea visible 		
        js.executeScript("arguments[0].scrollIntoView();", Element);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
		
	}
	
	/**
	 * Esta funcion hace scroll hasta el final de la pagina actual
	 *  
	 * @param  driver recibe elemento con la sesión actual
	 */
	public void scrollEndOfPage(WebDriver driver)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;
		
	}
	
	
	/*
	 * Esta rutina valida que un texto se encuentre en un link de la pagina
	 * @param driver sesion actual
	 * @param searchString cadena de busqueda
	 * @return boolean para saber si hay o no un enlace con la cadena de busqueda
	 */
	public boolean validateTextLinks(WebDriver driver,String searchString) {

		System.out.println("Validando si hay al menos un enlace de : "+searchString);
		try {
			WebElement element = driver.findElement(By.linkText(searchString));
			if (element  == null) 
			{ return false;}
			else 
			{ return true;}
		}
		catch (Exception e) 
		{
			// e.printStackTrace();
			System.err.println(e.getMessage());
			return false;
		}
	} 

	/**
	 * Esta rutina valida que existan enlaces en la pagina cargada en la sesion actual
	 * @param driver sesion actual 
	 * @param searchString cadena de busqueda
	 * @return numero de enlaces y si los pudo hallar o no
	 */
	public List<String> exitsLinks(WebDriver driver,String searchString) {

		List<String> results = new ArrayList();
		System.out.println("Buscando links de: "+searchString);
		try {
			List<WebElement> links = driver.findElements(By.tagName("a"));

			if (links  == null) 
			{ 
				results.add("0");
				results.add("false");
				return results;
			}
			else 
			{ 
				String numberLinks = String.valueOf(links.size());
				results.add(numberLinks);
				results.add("true");
				return results;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			results.add("-9999"); // Retorna este codigo de error si hubo problemas buscando los enlaces
			results.add("false");
			return results;
		}
	} //  Final funcion
	
	/*
	 *  Funcion que devuelve el id de sesion de un driver
	 *  @return id de la sesion actual 
	 */
	public static SessionId sessionIdOf(WebDriver driver) {
		  if (driver instanceof RemoteWebDriver) {
		    return ((RemoteWebDriver) driver).getSessionId();
		  }
		  return null;
		}

	
} // Final de la clase Base

