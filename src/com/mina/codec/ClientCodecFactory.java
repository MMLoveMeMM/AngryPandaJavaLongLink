package com.mina.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ClientCodecFactory implements ProtocolCodecFactory{

	private Charset mCharset;
	public ClientCodecFactory(Charset charset){
		
		mCharset=charset;
		
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return new ClientDecode(mCharset);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return new ClientEncode(mCharset);
	}

}
