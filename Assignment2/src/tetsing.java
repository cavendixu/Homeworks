
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tetsing {
	public static void main (String[] args) {
		
		int[] a1 = {6, 13, 0, 3, 7};
		arrayMystery(a1);
		System.out.println(2 + 2 + "xyz" + 3 + 3);
		
		for(int i=0;i<a1.length;i++) {
			System.out.println(a1[i]);
		}
	}
	
	public static void arrayMystery(int[] a) {  for (int i = 1; i < a.length - 1; i++) {     a[i] = (a[i - 1] + a[i + 1]) / 2;  }}
}
