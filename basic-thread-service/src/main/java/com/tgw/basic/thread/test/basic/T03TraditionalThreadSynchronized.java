package com.tgw.basic.thread.test.basic;

public class T03TraditionalThreadSynchronized {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new T03TraditionalThreadSynchronized().init();
	}
	
	private void init(){
		final Outputer outputer = new Outputer();
		new Thread(new Runnable(){
//			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output3("0123456789");
				}
				
			}
		}).start();
		
		new Thread(new Runnable(){
//			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output3("lihuoming");
				}
				
			}
		}).start();
		
	}

	static class Outputer{

		public void output0(String name){
			int len = name.length();
			for(int i=0;i<len;i++){
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}

		public void output1(String name){
			int len = name.length();
			synchronized (Outputer.class) 
			{
				for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}
		
		public synchronized void output2(String name){
			int len = name.length();
			for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
			}
			System.out.println();
		}
		
		public static synchronized void output3(String name){
			int len = name.length();
			for(int i=0;i<len;i++){
					System.out.print(name.charAt(i));
			}
			System.out.println();
		}	
	}
}
