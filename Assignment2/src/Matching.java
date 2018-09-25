import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;


public class Matching {
	public static void main (String[] args) {
		Scanner sc= new Scanner(System.in);
		System.out.println("Please Enter List (Don't input the total number, use blank space between numbers):");
		String input = sc.nextLine();			
		String[] inputTokens=input.split(" ");//enter the list

		System.out.println("Please Enter Pattern:");
		int pattern = sc.nextInt();		
		sc.close();	
		int[] List = new int[inputTokens.length];
		for (int x=0;x<inputTokens.length; x++) {
//			System.out.println(inputTokens[x]);
			List[x]= Integer.parseInt(inputTokens[x]);
		}		
		int patternSize=(int)(Math.log10(pattern)+1);
		int[] inverseKeys= new int[patternSize];// create an array to store keys
		for (int x=0;x<patternSize;x++) {
			inverseKeys[x]=pattern%10;
			pattern=pattern/10;			
		}
		
//		for (int x=0;x<patternSize;x++) {
//			System.out.println(inverseKeys[x]);
//		}
		
		ArrayList<ArrayList<Integer>> SliceArrayList = new ArrayList<ArrayList<Integer>>();	
		for(int key=inverseKeys.length-1; key>=0;key--) {//find all the combinations of patterns
			if (SliceArrayList.size()==0) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(inverseKeys[key]);
				SliceArrayList.add(tmp);
			}
			else {
				if(inverseKeys[key]==0) {
					//create new arraylist to store the values with "0" added
					
					ArrayList<ArrayList<Integer>> SliceTemp = new ArrayList<ArrayList<Integer>>();
					for(int x=0;x<SliceArrayList.size();x++){
						ArrayList<Integer> newList = new ArrayList<>(SliceArrayList.get(x));
						newList.add(0);
						SliceTemp.add(newList);
					}
					for(int w=0;w<SliceTemp.size();w++) {
						if(isArrayExist(SliceTemp.get(w),SliceArrayList,w)==false)//avoid adding a duplicated entry
						SliceArrayList.add(SliceTemp.get(w));
					}
				}
				else {
					for(int x=0;x<SliceArrayList.size();x++) {
						
						SliceArrayList.get(x).add(inverseKeys[key]);
					}
				}
			}
		}//find all the combinations of patterns, stored in SliceArrayList

		
		//find the locations matching the first key
		ArrayList<Integer> location = new ArrayList<Integer>();
		int firstKey = inverseKeys[inverseKeys.length-1];
		for (int x=0;x<List.length;x++) {
			if (List[x]==firstKey) {
			location.add(x); 	
			}
		}
		
//		System.out.println("locationbegain");
//		for (int x=0;x<location.size();x++) {
//			System.out.println(location.get(x));
//			
//		}
//		System.out.println("locationend");
//		
		
		//checking the slices from different initial position
		HashMap <String,Integer> results = new HashMap <String,Integer>();	
		System.out.println("SliceArrayList.size()"+SliceArrayList.size());
		for (int y=0;y<SliceArrayList.size();y++)  {	
			int SliceAppearTimes=0;
			for (int x=0;x<location.size();x++){
				int Appear=1;
				
				if(List.length-location.get(x)>=SliceArrayList.get(y).size()) {			
					for(int z=0;z<SliceArrayList.get(y).size();z++) {
						
						if (SliceArrayList.get(y).get(z)==0) {
							continue;
						}
						
						else if (SliceArrayList.get(y).get(z)!=List[location.get(x)+z]) {
							System.out.println(location.get(x)+z);
							Appear=0;
							break;
						}
					}
					SliceAppearTimes+=Appear;
				}
			}	
			results.put(SliceArrayList.get(y).toString(), SliceAppearTimes);
		}
		
		
		mapPrint(results);
	}
	
	
	
	public static boolean isArrayExist( ArrayList<Integer> newList,ArrayList<ArrayList<Integer>> Slice,int w) {
		boolean Exist=false;
		
			if(Slice.get(w).size()!=newList.size()) {
				Exist=false;;
			}
			else {
				boolean subExist=true;
				for (int y=0;y<newList.size();y++) {
					if(Slice.get(w).get(y)!=newList.get(y)) {
						subExist=false;
						break;
					}
				}
				if(subExist=true) {
					Exist=true;
				}
			}			
		
		return Exist;
	}
	
	public static String replace0(String output) {
		output=output.replaceAll("[0]", "*");
		output=output.replaceAll("[,\\[\\]\\s]", "");
		return output;
	}
	
	public static void mapPrint(HashMap<String,Integer> map) {
		for (HashMap.Entry<String, Integer> entry : map.entrySet())		{
		    if(entry.getValue()>0)
			System.out.println(replace0(entry.getKey()) + ":" + entry.getValue());
		}
	}
	
}
