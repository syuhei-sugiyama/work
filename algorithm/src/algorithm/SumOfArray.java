package algorithm;

public class SumOfArray {
	public static void main(String[] argas) {
		int[] a = {72, 68, 92, 88, 41, 53, 97, 84, 39, 55};
		int sum = 0;
		System.out.printf("ループの前 : sum = %d\n",sum);
		for (int val : a) {
			sum += val;
			System.out.printf("ループの中 : sum = %d\n",sum);
		}
		System.out.printf("sum = %d\n",sum);
		System.out.printf("ループの後 : sum = %d\n",sum);
	}
}
