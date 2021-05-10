package test;

// Librerias Junit
import static org.junit.Assert.assertEquals; // libreria validaciones
import static org.junit.Assert.fail; // libreria de validaciones

// Librerias basicas de Java
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;

import base.Locations;
import base.LogManagement;
import base.RecordScreen;
import base.ScreenShot;
import base.Setup;
// Importa la clase GooglePage es decir la pagina a ser probada
import pages.GooglePage;

// Librerias extent reports
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Esta clase contiene el test case que sera ejecutado 
 * 
 * */
public class testSearchTest {
	/**
	 * driver: Contiene el web driver responsable de cargar navegador, datos de la pagina y ejecucion comandos Selenium
	 */
	private WebDriver driver;
	GooglePage google_page;
	// private boolean acceptNextAlert = true; 
	private StringBuffer verificationErrors = new StringBuffer();
	String outputFile = Locations.FileLogLoc;
	static String iteration ;	static String userName; static String userPass; static String searchString;
	// public static final String separatorString=";";
	// public static final String quoteString="\"";
	String [] fields;
	static List<String> waitedText = new ArrayList();
	static List<String> stepProcess = new ArrayList();
	private String processName;
	// private String pathScreen = Locations.ImageLogLoc; 
	private String fileScreen;private String pageTitle;
	private String imageTime;private String processFlag;
	private LocalDateTime datehtml = LocalDateTime.now(); 
	private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(Setup.dateImageFormat);
    private String imageTimehtml = datehtml.format(myFormatObj);
    
	
	
	
	/**
	 * Este metodo se ejecuta previo al test case
	 * @throws Exception Si se presenta problema al crear los directorios de log del test case
	 */
	@Before
	public void setUp() throws Exception {
		// Crea los directorios de log para guardar: logcsv, imagenes , video, html
		LogManagement.createLogPath(Locations.LogLoc);
		LogManagement.createLogPath(Locations.ImageLogLoc);
		LogManagement.createLogPath(Locations.VideoLogLoc);
		LogManagement.createLogPath(Locations.HTMLLogLoc);
		
	}
    
	/**
	 * Este metodo representa el test case que sera ejecutado
	 * @throws Exception si se presenta una excepción en la ejecución de la prueba
	 */
	@Test
	public void testSearchTest() throws Exception {
		
		// Obtiene nombre del proceso que luego se reportWara en el log de eventos
		processName = Locations.ProjectLoc+"testSearchTest";
		System.out.println("Nombre proceso/caso de prueba: "+processName);
		// Inicializa archivo de log.html y objetos requeridos ExtentReports
		String methodName = "testSearchTest";
		ExtentReports reports = LogManagement.createLoghtml (methodName);
		ExtentTest test = reports.startTest(methodName);
		test = reports.startTest(methodName);

		// Incializa archivo video del test e inicia grabacion test case
		RecordScreen.startRecording(methodName);
		
		
		BufferedReader br = null;
		try {

			List<String> x = null ;
			br =new BufferedReader(new FileReader(Locations.ParametersLoc));
			String line = br.readLine();
			System.out.println("Linea 1 parameters: "+line);
			fields = line.split(Setup.sepatatorListString);
			System.out.println("fields: "+Arrays.asList(fields));
			List<String> parameters = new ArrayList();
			parameters = removeTrailingquoteStrings(fields);
			
			x = parameters; // Obtiene el array con los nombres de los campos
			loadParameters(x);
			System.out.println("Lista parameters: "+ Arrays.asList(x));
			stepProcess = new ArrayList();
			waitedText = new ArrayList();
			Integer ind = 0;
			Integer countIterations = 1;
			while (null!=line) {
				// Inicializa driver , navegador y pide visitar url
				google_page = new GooglePage(driver);
				driver = google_page.chromeDriverConnection();
				// driver = google_page.firefoxDriverConnection();
				test.log(LogStatus.PASS, "Abrio sesion en navegador --> Iteracion: "+countIterations);
				google_page.visit("https://google.com");
								
				// Carga primera linea de parameters
				// Si la linea n de parameters es nula 
				// Cierra: driver, archivo de parameters
				// y se dirige a validar la condicion while del ciclo
				line = br.readLine();
				if(line == null)
				{
					driver.close();
					test.log(LogStatus.PASS, "Cerro sesion --> final lista parametros");
					test.log(LogStatus.PASS, "Finalizo prueba");
					br.close();
					continue;
				}
				fields = line.split(Setup.sepatatorListString);
				parameters = removeTrailingquoteStrings(fields);
				x = parameters;
				loadParameters(x);
				
				// Imprime cada linea de registro leída del CSV
				System.out.println("UserID" + ":" + "userName "+"-- userPass");
				System.out.println(iteration + ":" + userName+"  --  *****");

							
				//System.out.println("stepProcess.get(1) : "+stepProcess.get(ind));
				//System.out.println("ind : "+ind);
				
				// Valida stepProcess inicio navegacion
				List<String> resultsLog = new ArrayList();
				resultsLog = LogManagement.validateStep(outputFile,driver,processName,iteration,stepProcess.get(ind),waitedText.get(ind),userName,test);
				imageTime = resultsLog.get(0);
				if (imageTime != "")
				{System.out.println("stepProcess validado");}
				else
				{System.out.println("stepProcess no validado");} 
				
				//Assert.assertEquals(driver.getTitle(),waitedText.get(ind));
				//assertEquals(driver.getTitle(),waitedText.get(ind));
				// Valida con assertEquals que el titulo de la pagina sea igual a waitedText
				pageTitle = google_page.returnpagetitle(driver);
				assertEquals(waitedText.get(ind),pageTitle);
				
				// Ejecuta busqueda
				// System.out.println("Buscar: "+searchString);
				google_page.search(searchString);
				
				// Valida stepProcess busqueda
				resultsLog = new ArrayList();
				resultsLog = LogManagement.validateStep(outputFile,driver,processName,iteration,stepProcess.get(ind+1),waitedText.get(ind+1),userName,test);
				imageTime = resultsLog.get(0);   // Fecha y hora de registro de la prueba para captura imagen
				processFlag = resultsLog.get(1); // Bandera proceso permite saber si hubo exito en la busqueda
				if ( processFlag.equals("true") )
				{
					System.out.println("stepProcess validado");
					Thread.sleep(3000);
					test.log(LogStatus.PASS, "Ejecuto busqueda --> "+ searchString);
					
					// Captura pantalla actual exito
					//fileScreen = pathScreen+ searchString+"-"+ iteration +"-"+imageTime+".png";
					//fileScreen = pathScreen + methodName +"-"+ iteration +"-"+imageTime+".png";
					fileScreen = LogManagement.defineLogName(Locations.ImageLogLoc, methodName, iteration,imageTime, "png"); 
					ScreenShot.takescreenshot(driver,fileScreen);
					
					// Hace scroll hasta final de pagina
					// google_page.scrollEndOfPage(driver);
					// System.out.println("Ejecuto scroll");
					
					// Hace scroll hasta 2000 pixeles abajo
					google_page.scrollDownVertical(driver);
					google_page.scrollDownVertical(driver);
					// Captura pantalla final exito
					// fileScreen = pathScreen+ searchString+"-f-"+ iteration +"-"+imageTime+".png";
					//fileScreen = Locations.ImageLogLoc+ methodName +"-f-"+ iteration +"-"+imageTime+".png";
					fileScreen = LogManagement.defineLogName(Locations.ImageLogLoc, methodName+"-f-", iteration,imageTime, "png"); 
					ScreenShot.takescreenshot(driver,fileScreen);
				}
				else
				{
					System.out.println("stepProcess no tuvo exito");
					test.log(LogStatus.FAIL, "Ejecuto busqueda --> "+ searchString);
					// Captura pantalla actual error
					// fileScreen = pathScreen+ searchString+"-"+ iteration + "-"+imageTime+"-ERR.png";
					// fileScreen = Locations.ImageLogLoc + methodName + "-" + iteration + "-" + imageTime + "-ERR.png";
					fileScreen = LogManagement.defineLogName(Locations.ImageLogLoc, methodName, iteration,imageTime+"-ERR", "png"); 
					ScreenShot.takescreenshot(driver, fileScreen);
				}
				
				
				// Valida con assertEquals que el titulo de la pagina sea igual a waitedText
				//Assert.assertEquals(driver.getTitle(),waitedText.get(ind+1));
				pageTitle = google_page.returnpagetitle(driver);
				assertEquals(waitedText.get(ind+1),pageTitle);
				
				driver.close();
				test.log(LogStatus.PASS, "Cerro sesion");
				
				stepProcess = new ArrayList();
				waitedText = new ArrayList();	
				countIterations += 1;
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null!=br) {
				br.close();
			}
		}
		
		// Cierra grabación video del test case
		RecordScreen.stopRecording();
		
		// Termina genaración log.html
		
		reports.endTest(test);
		reports.flush();
		
	} // Final testSearchTest case
	
	@Test
	public void testSearchTestPL() throws Exception 
	{
		// Obtiene nombre del proceso que luego se reportWara en el log de eventos
		processName = Locations.ProjectLoc+"testSearchTest";
		System.out.println("Nombre proceso/caso de prueba: "+processName);
		String methodName = "testSearchTestPL";
		// Incializa archivo video del test
		RecordScreen.startRecording(methodName);
		// Inicializa archivo de log.html y objetos requeridos ExtentReports
		ExtentReports reports = LogManagement.createLoghtml (methodName);
		ExtentTest test = reports.startTest(methodName);
		test = reports.startTest(methodName);
		
		google_page = new GooglePage(driver);
		driver = google_page.chromeDriverConnection();
		test.log(LogStatus.PASS, "Abrio sesion");
		
		google_page.visit("https://google.com");
		test.log(LogStatus.PASS, "Cargo google.com");
		
		String searchText = "Satelites";
		google_page.search(searchText);
		if(driver.getTitle().equals("Satelites - Buscar con Google"))
		{
			test.log(LogStatus.PASS, "Cargo resultados en: Satelites - Google Search");
		}
		else
		{
			test.log(LogStatus.FAIL, "Test Fallido");
		}
		
		// Prueba busqueda: buscara si hay o no al menos un enlace
		
		if(google_page.validateTextLinks(driver, searchText) == true)
		{
			test.log(LogStatus.PASS, "Hallo al menos un enlace en: Satelites - Google Search");
		}
		else
		{
			test.log(LogStatus.PASS, "Hallo al menos un enlace de: Satelites - Google Search");
		}
		
		// Para hallar el numero de enlaces relacionados con la cadena de busqueda
		List<String> resultsLog = new ArrayList();
		resultsLog = google_page.exitsLinks(driver, searchText);
		
		if (resultsLog.get(0).equals("-9999")) // Valida si hubo error al tratar de ejecutar la prueba
		{
			test.log(LogStatus.FAIL, "No pudo ejecutar funcion buscar enlaces error: "+ resultsLog.get(0));
		} else
		{ 
			if (resultsLog.get(0).equals("0")) // No encontro enlaces a pesar de ejecutar la prueba
			{
				test.log(LogStatus.INFO, "Enlaces hallados: "+ resultsLog.get(0));
			}else
			{   
				// Hallo por lo menos un enlace al ejecutar la prueba
				test.log(LogStatus.PASS, "Enlaces hallados: "+ resultsLog.get(0));
			}
		}
		
		// Cierra sesion
		driver.close();
		// Cierra grabación video del test case
		RecordScreen.stopRecording();
				
		/* 
		 * Cierra objetos del test que generan el log en html
		 */
		test.log(LogStatus.PASS, "Cerro sesion");
		test.log(LogStatus.PASS, "Finalizo prueba");
		reports.endTest(test);
		reports.flush();

	} // final test case
	
	
	/**
	 * Este metodo se ejecuta despues del test case para salir del sistema
	 * @throws Exception genera excepcion si tiene problemas al cerrar el test case
	 */
	@After 
	public void tearDown() throws Exception {
		System.out.println("Entrando al tearDown"); 
		driver.quit();
		
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
	
	/**
	 * Este metodo carga un cadena de valuesString en sus respectivas listas de parameters
	 * @param valuesString lista de valores que sera cargada en sus listas de parametros
	 */
	private static void loadParameters(List<String> valuesString) {
		//System.out.println("void Cargarparameters");
		//System.out.println("valuesString: "+Arrays.asList(valuesString));
		
		int count = 0;
		String var = valuesString.get(count);
		//System.out.println("var: "+var);
		
		String com = "x";
				
		while(!var.equals(com)){
			if(count == 0){
				iteration = var;
			}if(count == 1){
				userName = var;
			}if(count == 2){
				userPass = var;
			}if(count ==3) {
				searchString = var;
				System.out.println("Cadena busqueda: "+searchString);
			}if(count > 3){
				if(count%2 != 0){
					stepProcess.add(var);
					//System.out.println("stepProcess: "+var);
				}else{
					waitedText.add(var);
					//System.out.println("waitedText: "+var);
				}
			}
			
			count+=1;
			var = valuesString.get(count);
			//System.out.println("var: "+var);
			//System.out.println("count: "+count);
			
		}
		//System.out.println("final void Cargarparameters");
	}

	
    	    
    /*
     * Este metodo elimina las posibles comillas dobles en los elementos de una lista
     */
	private static List<String> removeTrailingquoteStrings(String[] fields) {

		String result[] = new String[fields.length];
	    List<String> returnedList = new ArrayList();
		for (int i=0;i<result.length;i++){
			result[i] = fields[i].replaceAll("^"+Setup.quoteString, "").replaceAll(Setup.quoteString+"$", "");
		}
		return returnedList=Arrays.asList(result);
	}
	

}
