package base;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

/**
 * 
 * Esta clase inicializa la libreria grafica y la libreria monte-screen-recorder
 * para captura del video del test case
 */


public class RecordScreen extends ScreenRecorder {
	public static ScreenRecorder screenRecorder;
	public String name;

	/*
	 *  Inicializa la pantalla antes de la captura de video
	 */
	public RecordScreen(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
			Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name)
					throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;

	}

	/**
	 *  Define la ruta del archivo de video. Si no encuentra la ruta entonces la crea.
	 *  @param fileFormat contiene el formato del archivo
	 */
	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {

		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		} else if (!movieFolder.isDirectory()) {
			throw new IOException("\"" + movieFolder + "\" no es un directorio.");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(Setup.dateImageFormat);
		return new File(movieFolder,
				name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));

	}

	/**
	 * Este metodo recibe el nombre del test case e inicia la grabación del mismo
	 * @param methodName nombre  la clase que contiene el test case a ser registrado
	 * @throws Exception genera excepción si se presenta problema al iniciar la grabación
	 */
	public static void startRecording(String methodName) throws Exception {
		File file = new File(Locations.VideoLogLoc);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		Rectangle captureSize = new Rectangle(0, 0, width, height);

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice()
				.getDefaultConfiguration();

		screenRecorder = new RecordScreen(gc, captureSize,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
				null, file, methodName);
		
		screenRecorder.start();

	}

	/**
	 * Este metodo detiene la grabación del test case
	 * @throws Exception genera excepción si presenta problema al ser detenida la grabación del test case
	 */
	public static void stopRecording() throws Exception {
		screenRecorder.stop();
	}

}
