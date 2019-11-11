package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToStringTest {

	private class TestClass{
		int i;
		double d;

		@Override
		public String toString() {
			return "i:"+i+"; d:"+d;
		}
	}

	@Test
	public void test() {

		TestClass t1 = new TestClass();


		System.out.println(t1);

		int[] a1 = new int[2];
		a1[0] = 11;
		a1[1] = 12;

		Integer[] a2 = new Integer[2];
		a2[0] = 11;
		a2[1] = 12;

		System.out.println(a1);
		System.out.println(a2);
	}

	@Test
	public void varTypes()
	{
		int a1 = 1;
		Integer a2 = 1;

		long b1 = 1;
//		Long b2 = 1;
		Long b3 = 0xC0B0L;

		float c1 = 1;
		Float c2 = 1F;
//		Float c3 = 1.0;
		Float c4 = 1.0F;

		double d1 = 1_1;
		System.out.println(d1);
		double d2 = 1.0;
		Double d3 = 1.0;
		Double d4 = 1D;
		Double d5 = 1.0D;

	}
}
