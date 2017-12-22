package com.mina.impl;

import org.apache.mina.core.session.IoSession;

import com.mina.packet.SimPacket;
import com.mina.session.SessionManager;
import com.mina.utils.MinaUtils;

public class CommandManger implements CRequestHandler{

	private volatile static CommandManger instance;
	public static CommandManger getInstance() {
		if (instance == null) {
			synchronized (CommandManger.class) {
				if (instance == null) {
					instance = new CommandManger();
				}
			}
		}
		return instance;
	}
	
	
	
	
	@Override
	public SimPacket processSentPacket(SimPacket message) throws Exception {
		// TODO Auto-generated method stub
		
		SimPacket sendpacket=new SimPacket();
		sendpacket.setHeader(message.getHeader());
		sendpacket.setFrame(message.getFrame());
		sendpacket.setLength(message.getLength());
		sendpacket.setCommand(message.getCommand());
		sendpacket.setVersion(message.getVersion());
		/*if(message.getData()!=null && message.getData().length>0){
			sendpacket.setData(message.getData());
		}*/
				
		byte cmd=message.getCommand();
		
		switch(cmd){
		case CommandResource.BEATHEART_CMD:{
			sendpacket.setCheck(message.getCheck());
			sendClientMsg(sendpacket);
		}
			break;
		case CommandResource.VERIFYTIME_CMD:{
			sendpacket.setData(message.getData());
			sendpacket.setCheck(message.getCheck());
			sendClientMsg(sendpacket);
		}
			break;
		case CommandResource.AUTH_CMD:
			break;
		case CommandResource.TAG_CMD:
			break;
		case CommandResource.GET_TAG_CMD:
			break;
			default:
				break;
		}
		
		return sendpacket;
	}

	@Override
	public SimPacket processEventPacket(SimPacket message) {
		// TODO Auto-generated method stub
		
		SimPacket simpack=(SimPacket)message;
		System.out.println(simpack.toString());
		
		return simpack;
	}
	
	public void sendClientMsg(SimPacket rp) {
		if ( null != SessionManager.getInstance().getSession(MinaUtils.MINA_PORT)) {
			SessionManager.getInstance().getSession(MinaUtils.MINA_PORT).getSession().write(rp);
		}
	}
	
	public void beatHeartMsg(){
		
		System.out.println("send beat heart ...");
		SimPacket simpack=new SimPacket();
		simpack.setHeader((byte) 0xEE);
		simpack.setFrame((byte) 0x00);
		byte[] lens=new byte[]{0x00,0x00,0x00,0x07};
		
		simpack.setCommand((byte) 0x01);
		simpack.setVersion(new byte[]{0x01,0x00,0x00,0x00});
		//simpack.setData(new byte[]{0x09});
		try {
			simpack.setLength(simpack.getLength()/*lens*/);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			simpack.setCheck(simpack.getCheck()/*(short) 0x8B3D*/);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			simpack.getCheck();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IoSession session=SessionManager.getInstance().getSession(MinaUtils.MINA_IO_PORT).getSession();
		if(session!=null){
			SessionManager.getInstance().getSession(MinaUtils.MINA_IO_PORT).getSession().write(simpack);
		}else{
			System.out.println("iosession is null !");
		}
		
	}
	
	public void verifyTimeMsg() throws Exception{
		
		System.out.println("verify stamp ...");
		SimPacket simpack=new SimPacket();
		simpack.setHeader((byte) 0xEE);
		simpack.setFrame((byte) 0x00);
		byte[] lens=new byte[]{0x00,0x00,0x00,0x07+0x06};
		simpack.setLength(simpack.getLength()/*lens*/);
		simpack.setCommand(CommandResource.VERIFYTIME_CMD);
		simpack.setVersion(new byte[]{0x01,0x00,0x00,0x00});
		simpack.setData(MinaUtils.getStamp(""));
		try {
			simpack.setCheck(simpack.getCheck()/*(short) 0x8B3D*/);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			simpack.getCheck();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IoSession session=SessionManager.getInstance().getSession(MinaUtils.MINA_IO_PORT).getSession();
		if(session!=null){
			SessionManager.getInstance().getSession(MinaUtils.MINA_IO_PORT).getSession().write(simpack);
		}else{
			System.out.println("iosession is null !");
		}
		
	}
	
	public void authMsg(long Id,String Password,String Coord,long starttime,String version,byte tagflag){
		
		byte[] ids=MinaUtils.longToByteArrays(Id, 16);
		byte[] datas=ids;
		
		
	}
	

}
