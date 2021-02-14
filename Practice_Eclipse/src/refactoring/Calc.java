package refactoring;

public class Calc {

	public static void main(String[] args) {

		int year = 2018; // 西暦年
		int month = 1; // 月
		int days = MonthCheck.chkDays(year, month);

		System.out.println("その月の日数は" + days + "です！");
	}

}
