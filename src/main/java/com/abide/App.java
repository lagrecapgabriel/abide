package com.abide;
import java.io.IOException;

public class App 
{
	public static long startTime = System.currentTimeMillis();

    public static void main(String[] args) throws IOException {

		String file = "";
		String file2 = "";
		
		if(args.length == 3){
			
			file = args[0];
			file2 = args[1];
			
			//in case we have a file with spaces
			if (args[0].contains("%20")) {
	            file = args[0].replace("%20", " ");
	        }
			if (args[1].contains("%20")) {
	            file2 = args[1].replace("%20", " ");
	        }

			Data data = new Data(file,file2);
			data.read(Integer.parseInt(args[2]));
		}
		else{
			System.out.println("You must specify *file centers*, " +
					"*file practices* and *number of exercise*");
		}
	}
}
