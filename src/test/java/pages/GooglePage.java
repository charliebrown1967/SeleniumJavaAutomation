package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.Base;


/**
 * 
 * Esta clase centraliza los localizadores y métodos de la página que será usada por el test case.
 * Se requiere una clase por cada página a ser probada. 
 *
 */
public class GooglePage extends Base{

    // Define localizadores objetos de la pagina
	// Localizador campo de texto Google busqueda "q" 
	By textbox_search = By.name("q");
	// Localizador boton de busqueda Google "btnK"
	By button_search = By.name("btnK");
	
	/**
	 * Este método es el constructor de la clase
	 * @param driver contiene la sesión actual
	 */
	public GooglePage(WebDriver driver) {
		super(driver);
		
	}
	
	
	/**
	 * Este método toma un texto de búsqueda y lo escribe en el campo "q" 
	 * de Google, para luego dar clic en el botón de búsqueda
	 * @param searchText cadena de texto que será buscada en Google
	 * @throws InterruptedException genera excepción en caso de que no pueda localizar el campo de búsqueda de la página donde se digita el texto
	 */
	public void search (String searchText) throws InterruptedException
	{
		// Pregunta si el campo textbox_search esta presente
		if (isDisplayed(textbox_search))
		{
			// Envia cadena de busqueda al campo textbox_search "q"
			type(searchText,textbox_search);
			// Solicita dar click en button_search btnK
			click(button_search);
		}else
		{  // Informa que el campo "q" no fue hallado
			System.out.println("No fue encontrado el campo de busqueda q !!");
		}
	}
	/**
	 * Este método recibe un objeto driver (sesión actual) y
	 * retorna el título de la página actual del navegador
	 * @param driver tiene la sesión actual
	 * @return título de la página actual
	 */
	public String returnpagetitle (WebDriver driver)
	{
	    return driver.getTitle();	
	}	
	
} // Final GooglePage
