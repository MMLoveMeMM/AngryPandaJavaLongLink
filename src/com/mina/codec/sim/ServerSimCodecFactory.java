package com.mina.codec.sim;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ServerSimCodecFactory implements ProtocolCodecFactory{

	private Charset mCharset;
	public ServerSimCodecFactory(Charset charset){
		mCharset=charset;
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return new ServerSimDecode(mCharset);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return new ServerSimEncode(mCharset);
	}

}
