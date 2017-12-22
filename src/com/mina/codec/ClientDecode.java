package com.mina.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.mina.packet.ObjectPack;

public class ClientDecode extends CumulativeProtocolDecoder{

	private Charset charset;
	public ClientDecode(Charset charset){
		this.charset=charset;
	}
	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer message, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		CharsetDecoder ce = charset.newDecoder();
		ObjectPack pack=new ObjectPack();
		
		pack.setYear(message.getInt());
		pack.setDataLen(message.getInt());
		System.out.println("len : "+pack.getDateLen());
		byte[] bt=new byte[pack.getDateLen()];
		message.get(bt,0,pack.getDateLen()/*4*/);
		pack.setBt(bt);
		pack.setName(message.getString(ce));
		
		out.write(pack);
		return true;
	}

}
