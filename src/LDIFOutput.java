import java.util.ArrayList;
import java.util.HashMap;

public class LDIFOutput {
	public void PrintOutput(ArrayList<String> Keys,HashMap<String, String> recordData){
		StringBuilder sbOutput = new StringBuilder();
		String sTemp;
		String sOutput;
		
		for(String sKey : Keys){
			String sRecordValue = recordData.get(sKey);
			
			sbOutput.append(sRecordValue + "\t");
		}
		//Strip off the last tab
		sTemp = sbOutput.toString();
		sOutput = sTemp.substring(0,sTemp.length() - 1);
		
		//final record output
		System.out.println(sOutput);
	}
	
	public void PrintOutput(ArrayList<String> Keys){
		StringBuilder sbOutput = new StringBuilder();
		String sTemp;
		String sOutput;
		
		for(String sKey : Keys){
			sbOutput.append(sKey + "\t");
		}
		//Strip off the last tab
		sTemp = sbOutput.toString();
		sOutput = sTemp.substring(0,sTemp.length() - 1);
		
		//final record output
		System.out.println(sOutput);
	}
}
