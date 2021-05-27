package base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * 
 * Esta clase permite capturar imagenes de la sesion y pantalla actual
 *
 */
public class ScreenShot extends Base{

	/**
	 * Este es el metodo constructor de la clase
	 * @param driver con la sesion actual
	 */
	public ScreenShot(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Este metodo captura imagenes de la pantalla actual
	 * @param driver representa la sesión-pantalla actual 
	 * @param fileScreen es la ruta del archivo de imagenes
	 */
	public static void takescreenshot(WebDriver driver,String fileScreen)
	{
		System.out.println("takescreenshot() : "+fileScreen);
		//Take the screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        //Copy the file to a location and use try catch block to handle exception
        try {
            FileUtils.copyFile(screenshot, new File(fileScreen));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
	}
	
}
