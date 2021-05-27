package base;

/**
 * 
 * Es una clase tipo interface que permite guardar los valores de las constantes 
 * que empleara el test: rutas logs, rutas drivers y direcciones web entre otras.
 * 
 * 
 */
public interface Locations {
	/**
	 * {@value #ProjectLoc} Ruta inicial del proyecto en Java.
	 */
	String ProjectLoc		= "SeleniumPOMTng/";
	/**
	 * {@value #ChromedriverLoc} Ruta del driver de Chrome.
	 */
	String ChromedriverLoc	= "./src/test/resources/chromedriver/chromedriver.exe";
	/**
	 * {@value #FirefoxdriverLoc} Ruta del driver de Firefox.
	 */
	String FirefoxdriverLoc	= "./src/test/resources/geckodriver/geckodriverwin64.exe";
	/**
	 * {@value #PhantomdriverLoc} Ruta del driver de Phantomjs.
	 */
	String PhantomdriverLoc = "";
	/**
	 * {@value #LogLoc} Ruta inicial de los archivos de log.
	 */
	String LogLoc           = "./target/log/";
	/**
	 * {@value #VideoLogLoc} Ruta de los archivos de video de cada prueba.
	 */
    String VideoLogLoc 		= "./target/log/videos/";
    /**
     * {@value #ImageLogLoc} Ruta de los archivos de imagenes de cada prueba.
     */
    String ImageLogLoc 		= "./target/log/img/";
    /**
     * {@value #FileLogLoc} Ruta del archivo de log de todas las pruebas del proyecto Java Maven.
     */
    String FileLogLoc 		= "./target/log/LogGoogle.csv";
    /**
     * {@value #HTMLLogLoc} Ruta del archivo HTML de log de todas las pruebas del proyecto Java Maven.
     */
    String HTMLLogLoc 		= "./target/log/html/";
    /**
     * {@value #ParametersLoc} Ruta del archivo de parametros de las pruebas.
     */
    String ParametersLoc 	= "./src/test/resources/parametros/ParametrosGoogle.csv";
    /**
     * {@value #InitialUrl} Direccion url inicial de las pruebas.
     */
    String InitialUrl 		= "http://www.google.com";
}
