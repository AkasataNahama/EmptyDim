package nahama.emptydim.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nahama.emptydim.EmptyDimCore;

public class Util {

	private static Logger logger = LogManager.getLogger(EmptyDimCore.MODID);

	public static void info(String msg) {
		logger.info(msg);
	}

	public static void info(String msg, String data) {
		Util.info(msg + " [" + data + "]");
	}

	public static void info(String msg, String data, boolean flag) {
		if (flag)
			Util.info(msg + " [" + data + "]");
	}

	public static void error(String msg) {
		logger.error(msg);
	}

	public static void error(String msg, String data) {
		Util.error(msg + " [" + data + "]");
	}

	public static void error(String msg, String data, boolean flag) {
		if (flag)
			Util.error(msg + " [" + data + "]");
	}

}
