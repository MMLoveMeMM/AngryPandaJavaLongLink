package com.mina.alive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class AliveTimeoutHandler implements KeepAliveRequestTimeoutHandler {

	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter arg0, IoSession arg1) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * 如果是服务器主动断开连接
		 * 这里是不会触发的
		 * 只有服务器遇到崩溃自行中断才会触发
		 * */
		System.out.println("server maybe close !");
		
	}

}
