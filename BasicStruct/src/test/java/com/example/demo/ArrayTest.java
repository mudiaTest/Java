package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ArrayTest {

	@Test
	public void ArrTest1() {
		String[] stArr1 = new String[2];
		String[] stArr2 = {"a", "b"};
		List<String> stLst1 = Arrays.asList(stArr1);
		List<String> stLst2 = Arrays.asList(new String[]{"a", "b"});
		System.out.println(stArr1.length);
		Arrays.fill(stArr1, "c");

		System.out.println(stArr1);
		System.out.println(stArr2);
		System.out.println(stLst1);
		System.out.println(stLst2);

		Stream.of(stArr1).forEach(System.out::println);

		System.out.println("-");
		Arrays.stream(new String[]{"a", "b", "c", "d", "e"} , 2, 2).forEach(System.out::print);
		System.out.println("--");
		Arrays.stream(new String[]{"a", "b", "c", "d", "e"} , 2, 4).forEach(System.out::print);
		System.out.println("---");
		String[] stArr3 = {"a", "b", "c", "d", "e"};
		Arrays.stream(stArr3 , 2, stArr3.length).forEach(System.out::print);

		BinaryOperator<Integer> operator1 = (a, b) -> a + b + 1;
		Integer[] intArr1 = new Integer[]{1,2,3,4,5,6};
		System.out.println("-");
		Arrays.stream(intArr1).forEach(a -> System.out.print(a+","));
		System.out.println("--");
		Arrays.parallelPrefix(intArr1, 2, 5, operator1 );
		Arrays.stream(intArr1).forEach(a -> System.out.print(a+","));
		System.out.println("---");
	}
}
