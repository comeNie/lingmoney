package com.mrbt.lingmoney.test;

import java.lang.reflect.Method;

public class UserPostThreadTest{
	public static class TestThread extends Thread{
		public void run() {
			// 每个用户都要无限访问接口
			try {
				while(true){
					UserPostTest t = new UserPostTest();
					Class<?> c=t.getClass();
					Method m=c.getDeclaredMethod("queryNoticeDetail", null);
				    //Method m=c.getDeclaredMethod("sendModiPw", null);
					m.invoke(t);
					sleep(2000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static void main(String[] args) {
		Thread.currentThread();
		// 创建100个线程 模拟100个用户
		for(int i=0;i<100;i++){
			TestThread test = new TestThread();
			System.out.println("Into Thread["+test.getId()+"]"+":"+test.getName());
			test.start();
		}
	}
}
