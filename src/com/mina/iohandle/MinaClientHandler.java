package com.mina.iohandle;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.mina.impl.CommandManger;
import com.mina.packet.ObjectPack;
import com.mina.packet.SimPacket;
import com.mina.session.CSession;
import com.mina.session.SessionManager;
import com.mina.utils.MinaUtils;

public class MinaClientHandler extends IoHandlerAdapter{

	public interface UpdateViewListener{
		
		public void onUpdateView(String message);
		
	}
	
	private UpdateViewListener mUpdateViewListener;
	public void setUpdateViewListener(UpdateViewListener listener){
		mUpdateViewListener=listener;
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
		System.out.println(""+message);
		/*ObjectPack pack=(ObjectPack) message;
		byte[] bt=pack.getBt();
		String bytemesg=MinaUtils.Byte2String(bt);
		mUpdateViewListener.onUpdateView("age : "+pack.getYear()+" bytemesg : "+bytemesg+" byte : "+bt[1]);*/
		
		SimPacket simpack=(SimPacket)message;
		byte[] bt=simpack.getLength();
		String bytemesg=MinaUtils.byteArr2HexStr(bt);
		System.out.println("length : "+bytemesg);
		System.out.println(simpack.toString());
		mUpdateViewListener.onUpdateView(MinaUtils.getSysTime()+":"+simpack.toString());
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
		SimPacket simpack=(SimPacket)message;
		System.out.println(simpack.toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
		/*
		 * 这个地方在服务器端主动断开连接的时候会被调用
		 * 但是这样的话,心跳处理机制将不会处理
		 * */
		
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
		
		int port=((InetSocketAddress)session.getLocalAddress()).getPort();
		CSession wrapper=new CSession(session,port,10003);
		MinaUtils.MINA_IO_PORT=port;
		SessionManager.getInstance().addSession(port, wrapper);
		System.out.println("client port : "+port);

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
		
		/*if (status == IdleStatus.BOTH_IDLE) {
			String message="this is heart";
			ObjectPack pack=new ObjectPack();
			pack.setYear(102);
			pack.setName("ahearta");
			pack.setDataLen(message.length());
			byte[] bt=MinaUtils.String2Byte(message);
			pack.setBt(bt);
			session.write(pack);
		}*/
		CommandManger.getInstance().beatHeartMsg();
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
		// 設置心跳
		// session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}

}
