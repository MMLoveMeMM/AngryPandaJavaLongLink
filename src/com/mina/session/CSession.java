package com.mina.session;

import java.io.Serializable;

import org.apache.mina.core.session.IoSession;

public class CSession implements Serializable{
	
	private int SSID;
	private int PORT;
	
	private IoSession ioSession;
	
	public CSession(IoSession session,int port,int ssid){
		
		ioSession=session;
		PORT=port;
		SSID=ssid;
		
	}
	
	public void setSession(IoSession ioSession){
		this.ioSession=ioSession;
	}
	public IoSession getSession(){
		return ioSession;
	}
	
	public void setSSID(int ssid){
		SSID=ssid;
	}
	
	public int getSSID(){
		return SSID;
	}
	
	public void setPORT(int port){
		PORT=port;
	}
	
	public int getPORT(){
		return PORT;
	}

}
