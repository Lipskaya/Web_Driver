package by.academy.it.framework;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

// Класс написан просто для примера. Целесообразнее вызывать логгер и его методы напрямую в классе.
public class Log {

  private static final Logger logger = Logger.getLogger("by.academy.it");

  static {
    PropertyConfigurator.configureAndWatch("log4j/log4j.properties");
  }

  public static void info(Logger log, Object message) {
    log.info(message);
  }

  public static void debug(Logger log, Object message) {
    log.debug(message);
  }

  public static void error(Logger log, Object message) {
    log.error(message);
  }

  public static void info(Object message) {
    logger.info(message);
  }

  public static void debug(Object message) {
    logger.debug(message);
  }

  public static void error(Object message) {
    logger.error(message);
  }


  public static void debugStart(Logger log, Object... params) {
    String message = getMethodName(27) + "()";
    if (params != null && params.length > 0) {
      message = message + " PARAMS:" + params.toString();
    }
    log.debug(message + " START");
  }

  public static void debugEnd(Logger log, Object returnObject) {
    String message = getMethodName(27) + "()";
    if (returnObject != null) {
      message = message + " RETURNS:" + returnObject.toString();
    }
    log.debug(message + " END");
  }

  public static void debugEnd(Logger log) {
    String message = getMethodName(27) + "()";
    log.debug(message + " END");
  }

  // from here: https://stackoverflow.com/questions/442747/getting-the-name-of-the-currently-executing-method
  //Динамически достаем из стека ИМЯ МЕТОДА в котором вызывался лог, чтобы не писать его руками
  private static String getMethodName(final int depth) {
    final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
    //    Arrays.asList(ste).stream().forEach(x->logg.debug(x.getClassName()));
    //System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
    // return ste[ste.length - depth].getMethodName();  //Wrong, fails for depth = 0
    return ste[ste.length - 1 - depth].getMethodName(); //Thank you Tom Tresansky
  }
}
