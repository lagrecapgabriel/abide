package com.abide;

import java.util.ArrayList;

public class Center {

	private String id;
	private String department;
	private String name;
	private String address;
	private String neighborhood;
	private String city;
	private String postalcode;
	ArrayList<Practice> practices = new ArrayList<Practice>();
	private float totalSpend;
	
	public Center(String id, String department, String name, String address,
			String neighborhood, String city, String postalcode) {
		this.id=id;
		this.department=department;
		this.name=name;
		this.address=address;
		this.neighborhood=neighborhood;
		this.city=city;
		this.postalcode=postalcode;
	}
	
	public String getId(){
		return this.id;
	}
	
	public ArrayList<Practice> getPractices(){
		return this.practices;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public String getPostalCode(){
		return this.postalcode;
	}
	
	public float getSpend(){
		return this.totalSpend;
	}
	
	public void setPractice(Practice practice){
		this.practices.add(practice);
	}
	
	public void sumSpend(float totalSpend){
		this.totalSpend+=totalSpend;
	}
}
