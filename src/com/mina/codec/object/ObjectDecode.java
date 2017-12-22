package com.mina.codec.object;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ObjectDecode extends CumulativeProtocolDecoder {

	private Charset set;
	public ObjectDecode(Charset charset){
		set=charset;
	}
	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer arg1, ProtocolDecoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
