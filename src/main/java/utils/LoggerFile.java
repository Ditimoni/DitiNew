package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class LoggerFile {


	/**
	 * Author: Priya Bharti
	 * Date: 31/3/22(Modified)
	 * Description: This class is implemented for logging purpose of which configuration
	 * available in log4j.properties file, which can be used throughout the framework.
	 */
	private static boolean root = false;

//	static {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
//		System.setProperty("currenttime", dateFormat.format(new Date()));
//	}

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class cls) {
		if (root) {
			return Logger.getLogger(cls);
		}
//		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/resources/log4j.properties");
		root = true;
		return Logger.getLogger(cls);
	}
}
