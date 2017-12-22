package com.mina.codec.sim;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.mina.packet.ObjectPack;
import com.mina.packet.SimPacket;

public class ServerSimEncode implements ProtocolEncoder {

	private Charset charset;
	public ServerSimEncode(Charset charset){
		this.charset=charset;
	}
	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
		CharsetEncoder ce = charset.newEncoder();
		/*ObjectPack mPack=(ObjectPack) message;
		
		IoBuffer buffer=IoBuffer.allocate(100).setAutoExpand(true);
		buffer.putInt(mPack.getYear());
		buffer.putInt(mPack.getDateLen());
		System.out.println("len : "+mPack.getDateLen());
		buffer.put(mPack.getBt());
		buffer.putString(mPack.getName(),ce);
		buffer.flip();
		out.write(buffer);*/
		
		SimPacket mpack=(SimPacket) message;
		IoBuffer simbuffer=IoBuffer.allocate(100).setAutoExpand(true);
		simbuffer.put(mpack.getHeader());
		simbuffer.put(mpack.getFrame());
		simbuffer.put(mpack.getLength());
		simbuffer.put(mpack.getCommand());
		simbuffer.put(mpack.getVersion());
		
		byte[] datas=mpack.getData();
		if(datas!=null){
			simbuffer.put(mpack.getData());
		}
		simbuffer.putShort(mpack.getCheck());
		simbuffer.flip();
		out.write(simbuffer);
		
	}

}
