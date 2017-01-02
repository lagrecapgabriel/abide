package com.abide;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Exercise {

	//We make arrays with subregions from the regions, there are some of them in different regions.
	//Therefore, we give this subregions depending where the capital is from.
	private ArrayList<String> LNDp = new ArrayList<String>(Arrays.asList("BR","CR",//London subregions
			"EC","EN","HA","IG","KT","NW","RM","SE","SM","SW","TW","UB", "WC"));
	private ArrayList<String> LNDp2 = new ArrayList<String>(Arrays.asList("E","N", "W"));
	private ArrayList<String> EOEp = new ArrayList<String>(Arrays.asList("AL","CB","CM",//East of England
			"CO","HP","IP","LU","NR","PE","SG","SS","WD"));
	private ArrayList<String> SEp = new ArrayList<String>(Arrays.asList("BN","CT","DA","GU",//South East
			"ME","MK","OX","PO","RG","RH","SL","SO","TN"));
	private ArrayList<String> SWp = new ArrayList<String>(Arrays.asList("BA","BH","BS","DT",//South West
			"EX","GL","PL","SN","SP","TA","TQ","TR"));
	private ArrayList<String> NEp = new ArrayList<String>(Arrays.asList("DH","DL","NE","SR",//North East
			"TD","TS"));
	private ArrayList<String> NWp = new ArrayList<String>(Arrays.asList("BB","BL","CA","CH",//North West
			"CW","FY","LA","OL","PR","SK","WA","WN"));
	private ArrayList<String> NWp2 = new ArrayList<String>(Arrays.asList("L","M"));
	private ArrayList<String> YAHp = new ArrayList<String>(Arrays.asList("BD","DN","HD","HG",//Yorshire and Humber
			"HU","HX","LS","WF","YO"));
	private String YAHp2 = "S";
	private ArrayList<String> EMp = new ArrayList<String>(Arrays.asList("DE","LE","LN","NG",//East Midlands
			"NN"));
	private ArrayList<String> WMp = new ArrayList<String>(Arrays.asList("CV","DY","HR","LD",//West Midlands
			"ST","SY","TF","WR","WS","WV"));
	private String WMp2 = "B";
	
	public Exercise() {
		
	}
	
	//We get the time from each exercise
	public void clock(){
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - App.startTime;
		System.out.println("Time(secs): "+totalTime/1000.0);
	}
	
	//When it is used an stream, it is used for making practices
	public static Function<String, Practice> mapToPractice = (line) -> {
		String[] p = line.split(",");
		return new Practice(p[0],p[1],p[2],p[3],p[4],Integer.parseInt(p[5]),
				Float.parseFloat(p[6]),Float.parseFloat(p[7]));
	};
	
	public static Function<String, Center> mapToCenter = (line) -> {
		String[] c = line.split(",");
		return new Center(c[1],c[2],c[3],c[4],c[5],c[6],c[7]);
	};
	
	//Number of practices in the region of London. Temporal cost: 30s
	public void ex1(BufferedReader bufferCent, BufferedReader bufferPrac){
		
		//It is got the first part of the post code for obtaining its region
		List<Center> centersLO = bufferCent.lines()
				.map(mapToCenter)
				.filter(center -> LNDp.contains(center.getPostalCode().substring(0, 2)) ||
						(LNDp2.contains(center.getPostalCode().substring(0,1)) && 
								center.getPostalCode().substring(1,2).charAt(0)<= '9' &&
								center.getPostalCode().substring(1,2).charAt(0)> '0'))
				.collect(Collectors.toList());

		ArrayList<String> centersID = new ArrayList<String>();

		for(int i=0; i<centersLO.size();i++){
			centersID.add(centersLO.get(i).getId());
		}
		
		long nCentersLO = 0;
		
		//If the center of the practice is the same as one center of the region of London.
		nCentersLO = bufferPrac.lines()
				.map(mapToPractice)
				.filter(practice -> centersID.contains(practice.getCenter()))
				.count();
		
		clock();
		System.out.println("Number of practices in the region of London: "+nCentersLO);
	}
	
	//Obtain average cost of peppermint oil. Temporal cost: 4s
	public void ex2(BufferedReader bufferPrac){
		
		float sumPresOil = 0;
		
		List<Practice> practicesPPOIL = bufferPrac.lines()
				.map(mapToPractice)
				.filter(practice -> practice.getCode().contains("0102000T0"))
				.collect(Collectors.toList());
		
		for(int i=0; i<practicesPPOIL.size(); i++){
			
			//It is done a sumatory of all the preinscriptions (items * cost + expenses)
			sumPresOil += practicesPPOIL.get(i).getItems() * practicesPPOIL.get(i).getNic() +
					practicesPPOIL.get(i).getPCTCost();
		}
		
		//Getting the average
		float result = sumPresOil/practicesPPOIL.size();
		
		clock();
		System.out.println("The average cost of Peppermint Oil prescriptions is: "+result+"£");
	}
	
	//top 5 post codes with more expenses in UK. Temporal cost: 45s
	public void ex3(BufferedReader bufferCent, BufferedReader bufferPrac){
		
		ArrayList<String> centersId = new ArrayList<String>();
		int numCenter = 0;
		float spendPractice = 0;
		List<Center> centers = bufferCent.lines()
				.map(mapToCenter)
				.collect(Collectors.toList());
		
		for(int i=0; i<centers.size();i++){
			centersId.add(centers.get(i).getId());
		}
		
		//We get practices with prescription bigger than 1000 for knowing before the result (about 50s).
		//It can be changed until 100000 and the result is similar but not so precise.
		List<Practice> practices = bufferPrac.lines()
				.map(mapToPractice)
				.filter(practice -> (practice.getItems() * practice.getNic() + practice.getPCTCost()) >1000)
				.collect(Collectors.toList());
		
		System.out.println(practices.size());
		
		//We get the medical center of the practice and we are accumulating the expenses of the center.
		for(int i=0; i<practices.size();i++){
			numCenter = centersId.indexOf(practices.get(i).getCenter());
			if(numCenter !=-1){
				spendPractice = practices.get(i).getItems()*practices.get(i).getNic()+practices.get(i).getPCTCost();
				centers.get(numCenter).sumSpend(spendPractice);
			}
		}
		
		//we order the List descendent
		Collections.sort(centers, new Comparator<Center>() {
	        @Override
	        public int compare(Center fruit2, Center fruit1)
	        {

	            return  (int)fruit1.getSpend() - (int)fruit2.getSpend();
	        }
	    });
		
		//we get the top 5
		for(int i=0; i<5; i++){
			System.out.println("The center: "+centers.get(i).getPostalCode()+" spend: "+centers.get(i).getSpend());
		}
		
		clock();
	}
	
	//Average price from each region of Flucloxacilin with Clofluampicil. Temporal cost: 4s
	public void ex4(BufferedReader bufferCent, BufferedReader bufferPrac){
		
		ArrayList<String> centersID = new ArrayList<String>();
		int centerNum = 0;
		float sumFCXLND = 0, sumFCXEOE = 0, sumFCXSE = 0, sumFCXSW = 0, sumFCXNE = 0,
				sumFCXNW= 0, sumFCXYAH = 0, sumFCXEM = 0, sumFCXWM = 0;
		int csumFCXLND = 0, csumFCXEOE = 0, csumFCXSE = 0, csumFCXSW = 0, csumFCXNE = 0,
				csumFCXNW= 0, csumFCXYAH = 0, csumFCXEM = 0, csumFCXWM = 0;
		
		List<Practice> practicesFCX = bufferPrac.lines()
				.map(mapToPractice)
				.filter(practice -> practice.getCode().contains("0501012G0"))
				.collect(Collectors.toList());
		
		List<Center> centers = bufferCent.lines()
				.map(mapToCenter)
				.collect(Collectors.toList());
		
		for(int i=0; i<centers.size();i++){
			centersID.add(centers.get(i).getId());
		}
		
		//Classify the summation of every center's practice (Name region + p)
		for(int i=0; i<practicesFCX.size(); i++){

			centerNum = centersID.indexOf(practicesFCX.get(i).getCenter());
		
			if(centerNum > -1){
				String PCode = centers.get(centerNum).getPostalCode().substring(0, 2);

				if(this.LNDp.contains(PCode) || (this.LNDp2.contains(PCode.substring(0, 1))
						&& PCode.charAt(1) > '0' && PCode.charAt(1) <= '9')){
					sumFCXLND += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXLND++;
				}
				else if(this.EOEp.contains(PCode)){
					sumFCXEOE += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXEOE++;
				}
				else if(this.SEp.contains(PCode)){
					sumFCXSE += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXSE++;
				}
				else if(this.SWp.contains(PCode)){
					sumFCXSW += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXSW++;
				}
				else if(this.NEp.contains(PCode)){
					sumFCXNE += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXNE++;
				}
				else if(this.NWp.contains(PCode) || (this.NWp2.contains(PCode.substring(0, 1))
						&& PCode.charAt(1) > '0' && PCode.charAt(1) <= '9')){
					sumFCXNW += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXNW++;
				}
				else if(this.YAHp.contains(PCode) || (PCode.substring(0, 1).contains(this.YAHp2)
						&& PCode.charAt(1) > '0' && PCode.charAt(1) <= '9')){
					sumFCXYAH += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXYAH++;		
				}
				else if(this.EMp.contains(PCode)){
					sumFCXEM += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXEM++;
				}
				else if(this.WMp.contains(PCode) || (PCode.substring(0, 1).contains(this.WMp2)
						&& PCode.charAt(1) > '0' && PCode.charAt(1) <= '9')){
					sumFCXWM += practicesFCX.get(i).getItems() * practicesFCX.get(i).getNic() +
							practicesFCX.get(i).getPCTCost();
					csumFCXWM++;
				}
				else{//we assure that we get all of the practices
					System.out.println("Iteracion: "+i+" en "+centers.get(centerNum).getId());
				}
			}
		}
		
		//average of flucloxacillin in each region.
		float rFCXLND = sumFCXLND/csumFCXLND, rFCXEOE = sumFCXEOE/csumFCXEOE, rFCXSE = sumFCXSE/csumFCXSE,
				rFCXSW = sumFCXSW/csumFCXSW, rFCXNE = sumFCXNE/csumFCXNE, rFCXNW = sumFCXNW/csumFCXNW,
				rFCXYAH = sumFCXYAH/csumFCXYAH, rFCXEM = sumFCXEM/csumFCXEM, rFCXWM = sumFCXWM/csumFCXWM;
		
		//national average
		float avNat = (rFCXLND + rFCXEOE + rFCXSE + rFCXSW + rFCXNE + rFCXNW + rFCXYAH + rFCXEM + rFCXWM) /9;

		System.out.println("The average cost of Flucloxacillin in London is: "+rFCXLND+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXLND)+"£.");
		System.out.println("The average cost of Flucloxacillin in East of England is: "+rFCXEOE+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXEOE)+"£.");
		System.out.println("The average cost of Flucloxacillin in South East is: "+rFCXSE+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXSE)+"£.");
		System.out.println("The average cost of Flucloxacillin in South West is: "+rFCXSW+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXSW)+"£.");
		System.out.println("The average cost of Flucloxacillin in North East is: "+rFCXNE+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXNE)+"£.");
		System.out.println("The average cost of Flucloxacillin in North West is: "+rFCXNW+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXNW)+"£.");
		System.out.println("The average cost of Flucloxacillin in Yorkshire & The Humber is: "
		+rFCXYAH+"£ and vary from the the national mean: "+java.lang.Math.abs(avNat - rFCXYAH)+"£.");
		System.out.println("The average cost of Flucloxacillin in East Midlands is: "+rFCXEM+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXEM)+"£.");
		System.out.println("The average cost of Flucloxacillin in West Midlands is: "+rFCXWM+"£ and vary"
				+ " from the the national mean: "+java.lang.Math.abs(avNat - rFCXWM)+"£.");
		
		clock();
	}
	
	//Find all the practices of all the centers not registered in the CVS, with the number of practices
	//of each ones. About 1m 30s - 2m 40s
	public void ex5(BufferedReader bufferCent, BufferedReader bufferPrac){
		
		ArrayList<String> centersID = new ArrayList<String>();
		ArrayList<String> centersNDB = new ArrayList<String>();//centers no database
		
		List<Center> centers = bufferCent.lines()
				.map(mapToCenter)
				.collect(Collectors.toList());
		
		for(int i=0; i<centers.size();i++){
			centersID.add(centers.get(i).getId());
		}
		
		List<Practice> practicesNDB = bufferPrac.lines()
				.map(mapToPractice)
				.filter(practice -> !centersID.contains(practice.getCenter()))
				.collect(Collectors.toList());
		
		System.out.println("Practices with center not identified: "+practicesNDB.size());
		int auxI = -1;
		ArrayList<Integer> numPracNDB = new ArrayList<Integer>();//array for get the number of practices of each ones
		
		for(int i=0; i<practicesNDB.size();i++){

			if(!centersNDB.contains(practicesNDB.get(i).getCenter())){
				if(i>0){
					numPracNDB.add(i - auxI);
				}
				auxI = i;
				centersNDB.add(practicesNDB.get(i).getCenter());
			}
			if(practicesNDB.size()-1==i){
				if(i==auxI){
					numPracNDB.add(1);
				}
				else{
					numPracNDB.add(i - auxI);
				}
			}
		}
		System.out.println("Centers not registered in DB: "+centersNDB.size());

		for(int i=0; i<centersNDB.size();i++){
			System.out.println("Center: "+centersNDB.get(i)+" with nºPractices: "+numPracNDB.get(i));
		}
		
		clock();
	}
	
}
