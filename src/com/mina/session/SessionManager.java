package com.mina.session;

import java.util.HashMap;

import org.apache.mina.core.session.IoSession;

import com.mina.utils.MinaUtils;

public class SessionManager implements ICSession{

	private static SessionManager instances;
	
	public static SessionManager getInstance(){
		
		if(instances==null){
			synchronized (SessionManager.class) {
				instances= new SessionManager();
			}
		}
		
		return instances;
		
	}
	
	private HashMap<Integer,CSession> mSessions=new HashMap<Integer,CSession>();
	
	@Override
	public void addSession(int port,CSession session) {
		// TODO Auto-generated method stub
		mSessions.put(port, session);
	}

	@Override
	public void removeSession(int port) {
		// TODO Auto-generated method stub
		mSessions.remove(port);
	}

	@Override
	public CSession getSession(int port) {
		// TODO Auto-generated method stub
		CSession session=mSessions.get(port);
		
		return session;//.getSession();
		
	}

	@Override
	public void updateSession(int port, CSession session) {
		// TODO Auto-generated method stub
		mSessions.remove(port);
		mSessions.put(port, session);
	}

	@Override
	public CSession getSession() {
		// TODO Auto-generated method stub
		CSession session=mSessions.get(MinaUtils.MINA_PORT);
		return null;
	}

}
