package com.mina.packet;

public class ObjectPack {
	
	private int year;
	private int datalen;
	private byte[] bt;
	private String name;
	
	public void setYear(int y){
		year=y;
	}
	
	public int getYear(){
		return year;
	}
	
	public void setDataLen(int len){
		datalen=len;
	}
	
	public int getDateLen(){
		return datalen;
	}
	
	public void setBt(byte[] b){
		bt=b;
	}
	
	public byte[] getBt(){
		return bt;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}

}
