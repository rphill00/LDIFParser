import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader brReader;
		String fileName = null;
		String separator = "?";
		String sLine = null;
		String[] aValues = new String[2];
		String sAttr = null;
		String sValue = null;
		HashMap<String, String> hmData = new HashMap<String, String>();
		ArrayList<String> alKeys = new ArrayList<String>();
		String sTemp = null;
		
		//Create an LDIFOutput object for writing data
		LDIFOutput oOut = new LDIFOutput();
		
		//Read through the contents one full time to build a
		//list of attributes to be used as keys(key/value)
		if (args.length > 0){			
			for (int i = 0; i <= (args.length) - 1;i++){
				if (args[i].equals("-f")){
					fileName = args[i+1];
				}else if (args[i].equals("-s")){
					separator = args[i+1];
				}
			}
		}else{
			showUsage();
			System.exit(0);
		}

		try {
			brReader = new BufferedReader(new FileReader(fileName));
			
			while ((sLine = brReader.readLine()) != null){
				if (!sLine.isEmpty()){
					if (sLine.contains(": ")){
						aValues = new String[2];
						aValues = sLine.split(": ");
						
						if (aValues.length == 2){
							sAttr = aValues[0].trim();
							sValue = aValues[1].trim();
							
							if (alKeys.contains(sAttr) == false){
								alKeys.add(sAttr);
							}
						}						
					}
				}
			}
			
			//Output the header line
			oOut.PrintOutput(alKeys);		
			brReader.close();

			//Once here, input file has been parsed for all attribute options
			//Open the file again for reading and output
			brReader = new BufferedReader(new FileReader(fileName));
			
			while ((sLine = brReader.readLine()) != null){
				if (!sLine.isEmpty()){
					if (sLine.contains(": ")){
						aValues = new String[2];
						aValues = sLine.split(": ");
						if (aValues.length == 2){
							sAttr = aValues[0].trim();
							sValue = aValues[1].trim();
							
							if (hmData.containsKey(sAttr)){
								//Get the existing value and append it
								sTemp = hmData.get(sAttr);
								sTemp += separator + sValue;
								hmData.put(sAttr, sTemp);
							}else{
								hmData.put(sAttr,  sValue);
							}
							
						}else{
							sTemp = hmData.get(sAttr);
							sTemp += sLine.trim();
							hmData.put(sAttr, sTemp);
						}
					}
				}else{
					//If line is empty, that means a break in records -- new user
					//Print out what exists in the hashmap and then reset it
					oOut.PrintOutput(alKeys, hmData);
					
					hmData = new HashMap<String, String>();
				}				
			}
			
			//Output the last record
			oOut.PrintOutput(alKeys, hmData);
			
			//close the file
			brReader.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void showUsage(){
		System.out.println("Usage: java -jar ldif2csvexport [-options] -f filename");
		System.out.println("\n" + "where options include:");
		System.out.println("\t" + "-s <value>\tseparator(default is ?)");
	}
}
