package com.mina.alive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.mina.packet.ObjectPack;
import com.mina.utils.MinaUtils;

public class KeepAliveMessageFactoryImp implements KeepAliveMessageFactory {

	/*
	 * 返回给服务器端的数据
	 * */
	@Override
	public Object getRequest(IoSession arg0) {
		// TODO Auto-generated method stub
		String message = "client heart";
		ObjectPack pack = new ObjectPack();
		pack.setYear(2016);
		pack.setName("cheart");
		pack.setDataLen(message.length());
		byte[] bt = MinaUtils.String2Byte(message);
		pack.setBt(bt);
		System.out.println("发送给自服务器端的数据:"+pack.getName());
		return pack;
	}

	/*
	 * 接受服务器发来的数据
	 * */
	@Override
	public Object getResponse(IoSession session, Object request) {
		// TODO Auto-generated method stub
		ObjectPack pack=(ObjectPack) request;
		String name=pack.getName();
		if (name.equals("sheart")){
			System.out.println("接受来自服务器端的数据:"+pack.getName());
		}
		return pack;
	}

	/*
	 * 判断服务器端发送过来的数据
	 * */
	@Override
	public boolean isRequest(IoSession session, Object message) {
		// TODO Auto-generated method stub
		ObjectPack pack = (ObjectPack) message;

		String name = pack.getName();
		
		if (name.equals("sheart")) {
			System.out.println("判断服务器端发送过来的数据:"+pack.getName());
			return true;
		}

		return false;
	}

	/*
	 * 当前客户端发送数据到服务器端引发的判断
	 * */
	@Override
	public boolean isResponse(IoSession session, Object message) {
		// TODO Auto-generated method stub
		ObjectPack pack = (ObjectPack) message;

		String name = pack.getName();
		
		if (name.equals("cheart")) {
			System.out.println("判断当前客户端发送出去的数据:"+pack.getName());
			return true;
		}
		return false;
	}

}
