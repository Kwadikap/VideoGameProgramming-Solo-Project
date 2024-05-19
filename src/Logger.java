public class Logger {

  public static void log(String message) {
    System.out.println(message);
  }

  public static void log(Object o) {
    System.out.println(o.toString());
  }
}
