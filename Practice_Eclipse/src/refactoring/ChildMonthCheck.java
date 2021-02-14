package refactoring;

public class ChildMonthCheck {

	/**
	 * @param year
	 * @param month
	 * @return days
	 */
	protected static int chkDays(int year, int month) {
		int days = 1; // 日

		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			// 31日の月か？
			days = 31;
		} else if(month == 2) {
			days = MonthCheck.leapYear(year);
		} else if(month == 4 || month == 6 || month == 9 || month == 11) {
			// 30日の月か？
			days = 30;
		} else {
			System.out.println("該当する年月がありません！");
		}
		return days;
	}

	/**
	 * @param year
	 * @return days
	 */
	static int leapYear(int year) {
		int days;
		// 閏年か？
		if((year % 4) == 0 && (year % 100 != 0 || year % 400 == 0)) {
			days = 29;
		} else
			days = 28;
		return days;
	}

	public ChildMonthCheck() {
		super();
	}

}