package base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.csvreader.CsvWriter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * Esta clase se emplea para el manejo los log de un test case
 *
 */
public class LogManagement extends Base{

	/**
	 * método constructor de la clase que retorna un WebDriver
	 * @param driver que será retornado
	 */
	public LogManagement(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
	}

	
	/**
	 * Esta rutina valida que se cumpla cada paso de la prueba y lo reporta al log de eventos de la misma
	 * @param outputFile es la ruta y nombre del archivo de log.csv
	 * @param driver sesión actual (página actual)
	 * @param processName nombre del proceso que se está ejecutando
	 * @param iterationN número de la iteración que sera reportada 
	 * @param step paso actual que se está validando
	 * @param waitedText texto de validación del paso actual
	 * @param userName nombre del usuario
	 * @param test objeto empleado para registrar paso ejecutado en el archivo html
	 * @return retorna lista con 2 objetos para saber fecha y hora de la prueba y si se pudo validar la misma
	 */
	public static List<String> validateStep(String outputFile,WebDriver driver, String processName,String iterationN,String step, String waitedText, String userName, ExtentTest test) 
	{
		List<String> resultsLog = new ArrayList(); // Saber si se pudo actualizar o no log csv
		List<String> resultsRet = new ArrayList(); // Retornar si la prueba tuvo exito y fecha de la prueba
		
		
		String timeLog;
		if (Base.validateText(driver,waitedText) == true)
		{   // Prueba superada y valida si genero o no log
			System.out.println("Prueba superada! >> Paso:"+step+" - "+waitedText);
			// Actualiza log html
			// test.log(LogStatus.PASS, "Cargo pagina de respuestas: "+ waitedText);
			// Actualiza log de eventos si timelog == "true" actualizo el log y retorna cadena fecha resultsLog.get(0)
			resultsLog = updateLog(outputFile,iterationN,processName,step,waitedText,"","Exitoso",userName);
			timeLog = resultsLog.get(1);
			// Valida si actualizo log
			if (timeLog.equals("true"))
			{System.out.println("Log actualizado");
			 // resultsRet = resultsLog;
			 return resultsLog;
			}
			else 
			{System.out.println("Log No actualizado");
			 return resultsLog;
			}
			
		} 
		else
		{  
			// Prueba no superada y valida si genero o no log
			// Actualiza log html
			//test.log(LogStatus.FAIL, "Cargo pagina de respuestas: "+ waitedText);
			// Actualiza log de eventos si timelog == "true" actualizo el log y retorna cadena fecha resultsLog.get(0)
			resultsLog = updateLog(outputFile,iterationN,processName,step,waitedText,"","Fallido",userName);
			timeLog = resultsLog.get(1);
			if (timeLog.equals("true"))
			{System.out.println("Log actualizado");
			 resultsRet.add(resultsLog.get(0)); // Asigna fecha y hora del log
			 resultsRet.add("false"); // Asigna false por prueba no superada
			 return resultsRet;
			}
			else
			// Prueba no superada y log no actualizado	
			{System.out.println("Log No actualizado");
			resultsRet.add(resultsLog.get(0)); // Asigna fecha y hora del log
			resultsRet.add("false"); // Asigna false por prueba no superada
			return resultsRet;

			}
		} 

	}
	

	/**
	 * Esta rutina se encarga de actualizar el log en cada paso de la prueba
	 * @param outputFile es la ruta y nombre del archivo de log
	 * @param Iteracion numero de la iteración actual
	 * @param NombreProceso nombre del proceso(proyecto/test case)
	 * @param Paso  nombre del paso actual
	 * @param ValorEsperado1 texto de validación del paso actual
	 * @param Fecha fecha y hora de la validación
	 * @param Resultado de la validación
	 * @param Usuario con el que se está ejecutando el paso actual
	 * @return boolean para saber si se pudo registrar o no el paso actual
	 */
	public static List<String> updateLog(String outputFile,String Iteracion,String NombreProceso,String Paso,String ValorEsperado1,String Fecha, String Resultado,String Usuario) // Curso seccion
	{
		// Crea un arreglo que retorna fecha y hora actualizacion log y bandera de actualizacion
		List<String> results = new ArrayList();
		  
		// Antes de abrir el archivo log se valida si existe

		boolean alreadyExists = new File(outputFile).exists();
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat(Setup.dateLogFormat);
		DateFormat imagedateFormat = new SimpleDateFormat(Setup.dateImageFormat);
		
		try {
             
			// Usa el constructor FileWriter que especifica que se anexaran registros
			//CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ';');
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), Setup.separatorFileColumns);

			// si el archivo no existe entonces escribimos la linea de header
			if (!alreadyExists)
			{
				csvOutput.write("Fecha");
				csvOutput.write("Iteracion");
				csvOutput.write("Proceso");
				csvOutput.write("Paso");
				csvOutput.write("ValorEsperado");
				csvOutput.write("Resultado");
				csvOutput.write("Usuario");
				csvOutput.endRecord();
			}
			// sino asume que el archivo ya existe y tiene header, y anexara los registros

			// Escribe registro
			// Carga, formatea y escribre fecha hora actual
			
			System.out.println("Fecha y hora: "+hourdateFormat.format(date));
			csvOutput.write(hourdateFormat.format(date));
			csvOutput.write(Iteracion);
			csvOutput.write(NombreProceso);
			csvOutput.write(Paso);
			csvOutput.write(ValorEsperado1);
			csvOutput.write(Resultado);
			csvOutput.write(Usuario);
			csvOutput.endRecord();
			csvOutput.close();
			// Retorna fecha y hora para la imagen y verdadero si actualizo el log.csv
			results.add(imagedateFormat.format(date));
			results.add("true");
			return results;
			
		} catch (IOException e) {
			e.printStackTrace();
			// Retorna fecha y hora para la imagen y falso sino actulizo el log.csv
			results.add(imagedateFormat.format(date));
			results.add("false");
			return results;
			
		}
	}




	/**
	 * Este método recibe una cadena, verifica que la ruta exista en el sistema
	 * y la crea si no existe
	 * @param pathlog ruta donde quedará el log de imágenes, csv o video
	 */
	public static void createLogPath (String pathlog)
	{
		File logpath = new File(pathlog);
		if (!logpath.exists()) {
			if (logpath.mkdirs()) {
				System.out.println("Directorio creado:"+pathlog);

			} else {
				System.out.println("Error al crear directorio");}
		}
	}
	
	/**
	 * Esta funcion recibe el nombre de un test case y crea un archivo html
	 * @param methodName es el nombre del test case
	 * @return objeto ExtentTest que contiene archivo html
	 */
	public static ExtentReports createLoghtml (String methodName)
	{
		// Inicializa fechas hora minuto actual
		String imageTimehtml = defineDateTimeLog();
		// Define ruta , nombre archivo y extension
		String htmlFileName = defineLogName(Locations.HTMLLogLoc,methodName,"0",imageTimehtml,"html");
		// Crea el archivo
		ExtentReports reports = new ExtentReports(htmlFileName, true);
		return reports;
	}
	
	/**
	 * Esta funcion genera una cadena para  archivo de log de cada iteracion: imagenes 
	 * @param filePath  ruta del archivo
	 * @param methodName nombre del proceso
	 * @param iteration numero de iteracion
	 * @param imageTime fecha y hora creacion
	 * @param extension extension del archivo
	 * @return ruta y nombre del archivo de log: imagenes, csv o html
	 * 
	 */
	public static String defineLogName(String filePath,String methodName,String iteration,String imageTime,String extension)
	{
		if (iteration.equals("0")) // Si iteracion igual a "0" sera un archivo de log para todo el proceso
		{
			return filePath + methodName + "-" +imageTime+ "." + extension;

		} else
		{   // Si iteracion es > 0 entonces sera un archivo de log por iteracion: imagenes
			return filePath + methodName + "-" + iteration + "-" + imageTime + "." + extension ;
		}

				
	}

	/**
	 *  Esta función toma la fecha y hora actual y le da un formato yyyy-MM-dd HHmmss, que sera empleado en la creacion de los logs: imagenes, video y html
	 * @return retorna cadena de la fecha formateada
	 */
	public static String defineDateTimeLog()
	{
		LocalDateTime datehtml = LocalDateTime.now(); 
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(Setup.dateImageFormat);
		return datehtml.format(myFormatObj);
	}
	
	/**
	 *  Esta función toma la fecha y hora actual y le da un formato yyyy-MM-dd HH:mm:ss, que sera empleado en el control del proceso geenral
	 * @return retorna cadena de la fecha formateada
	 */
	public static String defineDateTimeTest()
	{
		LocalDateTime datehtml = LocalDateTime.now(); 
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(Setup.dateLogFormat);
		return datehtml.format(myFormatObj);
	}
	

}
