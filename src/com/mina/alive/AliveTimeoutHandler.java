package com.mina.alive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class AliveTimeoutHandler implements KeepAliveRequestTimeoutHandler {

	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter arg0, IoSession arg1) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * ����Ƿ����������Ͽ�����
		 * �����ǲ��ᴥ����
		 * ֻ�з������������������жϲŻᴥ��
		 * */
		System.out.println("server maybe close !");
		
	}

}
