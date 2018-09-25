import java.util.Scanner;
	

public class lab09 {
		
		public static void main(String[] args)
		{
			Scanner input=new Scanner(System.in);
			System.out.println("Enter list: ");
			int listlength=input.nextInt();
			int[] list=new int[listlength];
			for(int i=0; i<listlength; i++){
				list[i]=input.nextInt();
			}
			
			System.out.println("Your pattern: ");
			int pattern=input.nextInt();
			
			System.out.println(" There are " +match(list, pattern)+" matches");
			
		}
		
		public static int[] toArray(int number)
		{
			int numDigit=0;
			int copy=number;
			while(number!=0)
			{
				numDigit++;
				number/=10;
			}
			int[] result=new int[numDigit];
			for(int i=numDigit-1; i>=0; i--)
				
			{
				result[i]=copy%10;
				copy/=10;
			}
			return result;
		}
		public static int match(int[] list, int pattern){
			int count=0;
			int[] patternA=toArray(pattern);
			for(int pos=0; pos<=list.length-patternA.length;pos++)
			{
				boolean isMatch=true;

				for(int j=0; j<patternA.length; j++){
					if(patternA[j]!=list[pos+j]) // if( patternA[j]!=0 && patternA[j]!=list[pos+j]);
					isMatch=false;
				}
				if(isMatch==true) count++;
			}
			return count;
		}
	}


