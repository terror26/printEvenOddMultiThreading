package main.java;

import java.util.concurrent.atomic.AtomicInteger;

public class printTest {

	static class Printer implements Runnable {

		private int n;
		AtomicInteger idx;
		public Printer(int n,AtomicInteger st) {
			this.n= n;
			this.idx = st;
		}
		
		@Override
		public void run() {
			do {
				synchronized(idx) {
					System.out.println(Thread.currentThread().getName() + "idx= " + idx.get());
					idx.compareAndSet(idx.get(), idx.get()+1);
					idx.notify();
					try {
						idx.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} while(idx.get()<= n);
		}
	}
	
	
	public static void main(String[] args) {
		int n = 20;
		
		AtomicInteger idx = new AtomicInteger(0);
		Thread t1 = new Thread(new Printer(n,idx),"first");
		Thread t2 = new Thread(new Printer(n,idx),"second");
		t1.start();
		t2.start();
	}

}
