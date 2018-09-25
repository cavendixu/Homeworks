package cw2;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;


public class wildcard {
	public static void main (String[] args) {
		try(Scanner sc= new Scanner(System.in)){
		System.out.println("Please Enter List (use blank space between numbers):");
		int listlength=sc.nextInt();
		int[] List=new int[listlength];
		for(int i=0; i<listlength; i++){
			List[i]=sc.nextInt();
		}

		System.out.println("Please Enter Pattern:");
		BigInteger pattern = sc.nextBigInteger();		
		sc.close();	
				
		//int patternSize=(int)(Math.log10(pattern)+1);//can not used for BI class
		int patternSize= pattern.toString().length();
		int[] inverseKeys= new int[patternSize];// create an array to store keys
		String[] bipattern=pattern.toString().split("");
		for (int x=0,j=patternSize;x<patternSize;x++) {
			inverseKeys[x]=Integer.parseInt(bipattern[j-1]);	
			j--;
		}
		
		ArrayList<ArrayList<Integer>> SliceArrayList = new ArrayList<ArrayList<Integer>>();	
		FindPatterns(SliceArrayList,inverseKeys);//find all combination of patterns
		//System.out.println("SliceArrayList.size:"+SliceArrayList.size());
				
		ArrayList<Integer> location = new ArrayList<Integer>();//find the locations matching the first key of input pattern
		int firstKey = inverseKeys[inverseKeys.length-1];
		for (int x=0;x<List.length;x++) {
			if (List[x]==firstKey) {
			location.add(x); 	
			}
		}
		
		HashMap <String,Integer> results = new HashMap <String,Integer>();
		PatternAndTimes(SliceArrayList, location, List, results);
		
		System.out.println("There are "+matchTimes(results)+" matches.")	;
		System.out.print("The matches are: ");
		BeautifulMapPrint(results);		
		}
	}
	
		
	public static boolean isArrayExist( ArrayList<Integer> newList,ArrayList<ArrayList<Integer>> Slice,int w) {
		boolean Exist=false;		
			if(Slice.get(w).size()!=newList.size()) {
				Exist=false;
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
		
		return Exist;//avoid adding a duplicated entry, but not that necessary as entry:value pairs will be stored in maps
	}
	
	public static void FindPatterns (ArrayList<ArrayList<Integer>> SliceArrayList, int[] inverseKeys) {
		for(int key=inverseKeys.length-1; key>=0;key--) {//find all the combinations of patterns
			if (SliceArrayList.size()==0) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(inverseKeys[key]);
				SliceArrayList.add(tmp);
			}
			else {
				if(inverseKeys[key]==0) {//create new arraylist to store the entries with "0" added					
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

	}

	public static void PatternAndTimes(ArrayList<ArrayList<Integer>> SliceArrayList, ArrayList<Integer> location, int[] List, HashMap <String,Integer> results) {	
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
							//System.out.println(location.get(x)+z);
							Appear=0;
							break;
						}
					}
					SliceAppearTimes+=Appear;
				}
			}	
			results.put(SliceArrayList.get(y).toString(), SliceAppearTimes);
		}
		
	}

	public static String replace0(String output) {
		output=output.replaceAll("[0]", "*");
		output=output.replaceAll("[,\\[\\]\\s]", "");
		return output;
	}
	
	public static void BeautifulMapPrint(HashMap<String,Integer> results) {
		HashMap <String,Integer> resultsForPrint = new HashMap <String,Integer>();
		for (HashMap.Entry<String, Integer> entry : results.entrySet())		{
		    if(entry.getValue()>0)
		    	resultsForPrint.put(replace0(entry.getKey()),entry.getValue());
		}
		ArrayList<String>  keys = new ArrayList<String>(resultsForPrint.keySet());
		for (int i = 0; i < keys.size(); i++) {
		    Object obj = keys.get(i);
		    if (i<keys.size()-1) {
		    	System.out.print(obj+":"+resultsForPrint.get(obj)+", ");
		    }
		    else System.out.print(obj+":"+resultsForPrint.get(obj)+".");
		}	
	}
	
	public static int matchTimes(HashMap<String,Integer> map) {
		int x=0;
		for (HashMap.Entry<String, Integer> entry : map.entrySet()){
			x=x+entry.getValue();
		}
		return x;
	}
	

}
