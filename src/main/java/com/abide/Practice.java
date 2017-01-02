package com.abide;

public class Practice {

	private String sha;
	private String pct;
	private String center;
	private String bnfcode;
	private String bnfname;
	private int items;
	private float nic;
	private float pct_cost;
	
	public Practice(){
		
	}
	
	public Practice(String sha, String pct, String center, String bnfcode, String bnfname,
			int items, float nic, float pct_cost) {
		this.sha=sha;
		this.pct=pct;
		this.center=center;
		this.bnfcode=bnfcode;
		this.bnfname=bnfname;
		this.items=items;
		this.nic=nic;
		this.pct_cost=pct_cost;
	}

	public String getCenter(){
		return this.center;
	}
	
	public String getName(){
		return this.bnfname;
	}
	
	public String getCode(){
		return this.bnfcode;
	}
	
	public int getItems(){
		return this.items;
	}
	
	public float getNic(){
		return this.nic;
	}
	
	public float getPCTCost(){
		return this.pct_cost;
	}
	
}
