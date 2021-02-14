package refactoring;

import anonymous.Anony;

public class InLine {

	private static final double _0_08 = 0.08;

	public static void main(String[] args) {

		Anony ano = new Anony();

		int kingaku = 100;
		double tax = _0_08;

		int zeikomi = (int)(kingaku * (1 + tax));
		System.out.println(zeikomi);

		kingaku = kingaku * 20;
		zeikomi = (int) (kingaku * (1 + tax));
		System.out.println(zeikomi);
	}

}
