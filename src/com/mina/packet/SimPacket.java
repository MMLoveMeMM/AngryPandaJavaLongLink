package com.mina.packet;

import com.mina.utils.MinaUtils;

public class SimPacket extends Packet{

	private byte Header;
	private byte Frame;
	private byte[] Length;
	private byte Command;
	private byte[] Version;
	private byte[] Data;
	private short Check;
	public byte getHeader() {
		return Header;
	}
	public void setHeader(byte header) {
		Header = header;
	}
	public byte getFrame() {
		return Frame;
	}
	public void setFrame(byte frame) {
		Frame = frame;
	}
	
	public byte[] getLength() throws Exception {
		
		int len=7;
		if(Data!=null && Data.length>0){
			len=7+Data.length;//Лђеп Command+Version+Data+Check
		}
		
		Length=MinaUtils.intToBytes(len);
		
		System.out.println("len byte : "+MinaUtils.byteArr2HexStr(Length));
		return Length;
	}
	public void setLength(byte[] length) {
		Length = length;
	}
	public byte getCommand() {
		return Command;
	}
	public void setCommand(byte command) {
		Command = command;
	}
	public byte[] getData() {
		return Data;
	}
	public void setData(byte[] data) {
		Data = data;
	}
	public short getCheck() {
		
		byte[] crc=new byte[]{Frame};
		crc=MinaUtils.byteMerger(crc,Length);
		byte[] cmdarr=new byte[]{Command};
		crc=MinaUtils.byteMerger(crc,cmdarr);
		crc=MinaUtils.byteMerger(crc,Version);
		
		Check=MinaUtils.CRC(crc);
		//System.out.println("Gen CRC : "+Check);
		
		return Check;
	}
	public void setCheck(short check) {
		Check = check;
	}
	public byte[] getVersion() {
		return Version;
	}
	public void setVersion(byte[] version) {
		Version = version;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		try {
			if(Data==null||Data.length<1){
				return "pack : "+MinaUtils.byte2HexStr(Header)+MinaUtils.byte2HexStr(Frame)+MinaUtils.byteArr2HexStr(Length)
				+MinaUtils.byte2HexStr(Command)+MinaUtils.byteArr2HexStr(Version)
				+MinaUtils.shortToByteArray(Check);
			}else{
				return "pack : "+MinaUtils.byte2HexStr(Header)+MinaUtils.byte2HexStr(Frame)+MinaUtils.byteArr2HexStr(Length)
				+MinaUtils.byte2HexStr(Command)+MinaUtils.byteArr2HexStr(Version)
				+MinaUtils.byteArr2HexStr(Data)
				+MinaUtils.shortToByteArray(Check);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
		
}
