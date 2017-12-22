package com.mina.alive;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.mina.packet.ObjectPack;
import com.mina.utils.MinaUtils;

public class KeepAliveMessageFactoryImp implements KeepAliveMessageFactory {

	/*
	 * ���ظ��������˵�����
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
		System.out.println("���͸��Է������˵�����:"+pack.getName());
		return pack;
	}

	/*
	 * ���ܷ���������������
	 * */
	@Override
	public Object getResponse(IoSession session, Object request) {
		// TODO Auto-generated method stub
		ObjectPack pack=(ObjectPack) request;
		String name=pack.getName();
		if (name.equals("sheart")){
			System.out.println("�������Է������˵�����:"+pack.getName());
		}
		return pack;
	}

	/*
	 * �жϷ������˷��͹���������
	 * */
	@Override
	public boolean isRequest(IoSession session, Object message) {
		// TODO Auto-generated method stub
		ObjectPack pack = (ObjectPack) message;

		String name = pack.getName();
		
		if (name.equals("sheart")) {
			System.out.println("�жϷ������˷��͹���������:"+pack.getName());
			return true;
		}

		return false;
	}

	/*
	 * ��ǰ�ͻ��˷������ݵ����������������ж�
	 * */
	@Override
	public boolean isResponse(IoSession session, Object message) {
		// TODO Auto-generated method stub
		ObjectPack pack = (ObjectPack) message;

		String name = pack.getName();
		
		if (name.equals("cheart")) {
			System.out.println("�жϵ�ǰ�ͻ��˷��ͳ�ȥ������:"+pack.getName());
			return true;
		}
		return false;
	}

}
