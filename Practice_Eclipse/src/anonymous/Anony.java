package anonymous;

public class Anony {

	private static final class French {
		@Override
		public String toString() {
			String french = "Bonjour le monde!";
			return french;
		}
	}

	public static void main(String[] args) {
		transWord(new French());
	}

	public static void transWord(Object obj) {
		System.out.println(obj.toString());
	}

}
