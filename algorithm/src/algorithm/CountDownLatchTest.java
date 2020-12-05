package algorithm;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	private static final int LOOP_COUNT = 10;
	private static CountDownLatch countDownLatch = new CountDownLatch(LOOP_COUNT);

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < LOOP_COUNT; i++) {
			new Thread() {
				public void run() {
					System.out.println("wait...");
					countDownLatch.countDown();
					System.out.println(countDownLatch.getCount());
				}
			}.start();
		}
		countDownLatch.await();
		System.out.println("finish!");
	}
}
