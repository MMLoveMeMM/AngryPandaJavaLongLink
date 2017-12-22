package com.mina.impl;

import com.mina.packet.SimPacket;

public interface CRequestHandler {
	public abstract SimPacket processSentPacket(SimPacket message) throws Exception;
	public abstract SimPacket processEventPacket(SimPacket message);
}
