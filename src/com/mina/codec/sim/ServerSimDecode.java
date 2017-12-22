package com.mina.codec.sim;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.mina.packet.ObjectPack;
import com.mina.packet.SimPacket;
import com.mina.utils.MinaUtils;

public class ServerSimDecode extends CumulativeProtocolDecoder{

	private Charset charset;
	public ServerSimDecode(Charset charset){
		this.charset=charset;
	}
	
	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer message, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		CharsetDecoder ce = charset.newDecoder();
		/*ObjectPack pack=new ObjectPack();
		
		pack.setYear(message.getInt());
		pack.setDataLen(message.getInt());
		System.out.println("len : "+pack.getDateLen());
		byte[] bt=new byte[pack.getDateLen()];
		message.get(bt,0,pack.getDateLen());
		pack.setBt(bt);
		pack.setName(message.getString(ce));
		
		out.write(pack);*/
		
		SimPacket simpack=new SimPacket();
		simpack.setHeader(message.get());
		simpack.setFrame(message.get());
		byte[] lens=new byte[4];
		message.get(lens,0,4);
		simpack.setLength(lens);
		
		simpack.setCommand(message.get());
		
		byte[] vers=new byte[4];
		message.get(vers,0,4);
		simpack.setVersion(vers);
		
		int dataslen=(int) (MinaUtils.byteArrayToLong(simpack.getLength())-7);
		if(dataslen>0){
			byte[] datas=new byte[dataslen];
			message.get(datas,0,dataslen);
			simpack.setData(datas);
		}
		
		simpack.setCheck(message.getShort());
		
		out.write(simpack);
		
		return true;
	}

}
