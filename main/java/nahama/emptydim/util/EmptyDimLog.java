package nahama.emptydim.util;

import nahama.emptydim.EmptyDimCore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmptyDimLog {
	private static Logger logger = LogManager.getLogger(EmptyDimCore.MODID);

	public static void info(String msg) {
		logger.info(msg);
	}

	public static void info(String msg, String data) {
		EmptyDimLog.info(msg + " [" + data + "]");
	}

	public static void info(String msg, String data, boolean flag) {
		if (flag)
			EmptyDimLog.info(msg, data);
	}

	public static void error(String msg) {
		logger.error(msg);
	}

	public static void error(String msg, String data) {
		EmptyDimLog.error(msg + " [" + data + "]");
	}

	public static void error(String msg, String data, boolean flag) {
		if (flag)
			EmptyDimLog.error(msg, data);
	}
}
