package jackson;

public class Views {
	public static class Private {}
  public static class Public {}
  // Internal powoduje te¿ na pokazywanie pól Public
  public static class Internal extends Public {}
}
