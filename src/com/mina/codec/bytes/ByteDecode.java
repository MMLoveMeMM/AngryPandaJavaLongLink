package com.mina.codec.bytes;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ByteDecode extends CumulativeProtocolDecoder {

	private Charset set;
	public ByteDecode(Charset charset){
		set=charset;
	}
	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer arg1, ProtocolDecoderOutput arg2) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
