package com.abide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Data {

	private String fileCenter = "";
	private String filePractice = "";
	private Exercise exercise = new Exercise();
	
	public Data(String fileCenter, String filePractice) {
		
		this.fileCenter=fileCenter;
		this.filePractice=filePractice;
	}
	
	public void setFilePractice(String fpractice){
		filePractice=fpractice;
	}
	
	public void read(int Nexercise) throws IOException{
			
		InputStream pracStream,centStream;
		BufferedReader bufferPrac = null, bufferCent = null;
		try {
			
			pracStream = new FileInputStream(new File(filePractice));
			centStream = new FileInputStream(new File(fileCenter));
			bufferPrac = new BufferedReader(new InputStreamReader(pracStream));
			bufferCent = new BufferedReader(new InputStreamReader(centStream));
		
			bufferPrac.readLine();
			
			switch(Nexercise){
				case 1: 	exercise.ex1(bufferCent, bufferPrac);
							break;
				case 2:		exercise.ex2(bufferPrac);
							break;
				case 3:		exercise.ex3(bufferCent, bufferPrac);
							break;
				case 4:		exercise.ex4(bufferCent, bufferPrac);
							break;
				case 5:		exercise.ex5(bufferCent, bufferPrac);
							break;
				default: 	System.out.println("Specify number of exercise 1-5");
							break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
			
		System.out.println("Finished.");
	}
}
