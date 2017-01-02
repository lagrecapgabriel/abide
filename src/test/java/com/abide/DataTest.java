package com.abide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testArgumentsExerciseIncorrect() throws IOException {
		
		String[] args = new String[3];
		args[0] = "T201202ADD REXT.CSV";
		args[1] = "T201109PDP IEXT.CSV";
		args[2] = "0";
		App.main(args);
		Assert.assertTrue(outContent.toString().contains("Specify number of exercise 1-5"));
	}
	
	@Test
	public void testNumberArgsNotEnough() throws IOException {
		
		String[] args = new String[2];
		args[0] = "T201202ADD REXT.CSV";
		args[1] = "T201109PDP IEXT.CSV";
		App.main(args);
		Assert.assertTrue(outContent.toString().contains("You must specify *file centers*"));
	}
	
	@Test
	public void fileCorrectWithOtherCode() throws IOException {
		
		String[] args = new String[3];
		args[0] = "T201202ADD%20REXT.CSV";
		args[1] = "T201109PDP%20IEXT.CSV";
		args[2] = "0";
		App.main(args);
		Assert.assertTrue(outContent.toString().contains("Specify number of exercise 1-5"));
	}
	
	@Test(expected=FileNotFoundException.class)
	public void testFileNotFound() throws IOException{
		String[] args = new String[3];
		args[0] = "T201202ADD%20REXT.CSV";
		args[1] = "T201109PDP%20IEXT2.CSV";
		args[2] = "0";
		App.main(args);
	}

}
