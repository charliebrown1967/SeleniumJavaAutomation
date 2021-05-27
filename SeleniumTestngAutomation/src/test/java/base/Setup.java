package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * Es una clase tipo interface que permite guardar los valores de las constantes 
 * que empleara el test: caracteres separadores de listas, caracteres separadores de columnas,
 * set de caracteres, formato de fecha log, formato de fecha imagenes 
 * 
 */

public interface Setup {
	
	/*
	 * Es la cadena de la pagina de codigos que sera empleada
	 */
	String codePageString = "UTF-16";
	
	/*
	 * Es la cadena separadora de listas  
	 */
	String sepatatorListString = ";";
	/*
	 * Es el caracter seeparador de columnas de un archivo csv
	 */
	char separatorFileColumns = ';';
	/*
	 *  Es la cadena para suprimir comillas dobles de un String
	 */
	String quoteString="\"";
	/*
	 * Es la cadena con el formato que se empleara en las fechas de: videos, imagenes, htmls 
	 */
	String dateImageFormat = "yyyyMMdd-HHmmss";
	/*
	 * Es la cadena con el formato que se empleara en los log.csv 
	 */
	String dateLogFormat = "yyyy/MM/dd HH:mm:ss";

}
