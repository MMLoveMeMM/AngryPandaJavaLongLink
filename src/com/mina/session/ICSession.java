package com.mina.session;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;

public interface ICSession {
	
	public void addSession(int port,CSession session);
	public void updateSession(int port,CSession session);
	public void removeSession(int port);
	public CSession getSession(int port);
	public CSession getSession();

}
