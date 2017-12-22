package com.mina.codec.object;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ObjectEncode implements ProtocolEncoder{

	private Charset set;
	public ObjectEncode(Charset charset){
		set=charset;
	}
	
	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
