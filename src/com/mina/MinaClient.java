package com.mina;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.mina.alive.AliveTimeoutHandler;
import com.mina.alive.KeepAliveMessageFactoryImp;
import com.mina.codec.ClientCodecFactory;
import com.mina.codec.sim.ServerSimCodecFactory;
import com.mina.impl.CommandManger;
import com.mina.iohandle.MinaClientHandler;
import com.mina.packet.ObjectPack;
import com.mina.packet.SimPacket;
import com.mina.session.CSession;
import com.mina.session.SessionManager;
import com.mina.utils.MinaUtils;

public class MinaClient extends JFrame implements MinaClientHandler.UpdateViewListener {

	private JPanel mPanel;
	private JPanel mStatusPanel;
	private JButton mSendBtn;
	private JButton mCleanBtn;

	private JButton mStartBtn;
	private JButton mStopBtn;

	private JTextField mSendMsg;
	private JTextArea mRecArea;

	private JLabel mImage;

	public MinaClient() {

		setVisible(true);
		setSize(600, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Client");
		setLayout(new BorderLayout());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (JOptionPane.showConfirmDialog(MinaClient.this, "你确定退出程序嘛?", "Exit  Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.out.println("exit program");
					StopClient();
					System.exit(0);
				}
			}
		});

		mPanel = new JPanel(new FlowLayout());
		mStatusPanel = new JPanel(new FlowLayout());

		/*ImageIcon icon = createImageIcon("images/middle.gif", "a pretty but meaningless splat");
		mImage.setIcon(icon);*/

		mSendMsg = new JTextField();
		mRecArea = new JTextArea();

		mStatusPanel.add(mRecArea);

		mSendBtn = new JButton("send");
		mSendBtn.setEnabled(false);
		mSendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// SessionManager.getInstance().getSession(MinaUtils.MINA_PORT).getSession().write("random
				// : ");
				/*ConnectFuture futrue = connector.connect(new InetSocketAddress("localhost", MinaUtils.MINA_PORT));
				// 等待连接创建完成
				futrue.awaitUninterruptibly();

				String message = "client heart";
				ObjectPack pack = new ObjectPack();
				pack.setYear(2016);
				pack.setName("client send");
				pack.setDataLen(message.length());
				byte[] bt = MinaUtils.String2Byte(message);
				pack.setBt(bt);

				futrue.getSession().write(pack);*/
				// futrue.getSession().getCloseFuture().awaitUninterruptibly();
				
				
				SimPacket simpack=new SimPacket();
				simpack.setHeader((byte) 0xEE);
				simpack.setFrame((byte) 0x00);
				byte[] lens=new byte[]{0x00,0x00,0x00,0x07};
				simpack.setLength(lens);
				simpack.setCommand((byte) 0x01);
				simpack.setVersion(new byte[]{0x01,0x00,0x00,0x00});
				//simpack.setData(new byte[]{0x09});
				try {
					simpack.setCheck(simpack.getCheck()/*(short) 0x8B3D*/);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					simpack.getCheck();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*IoSession session=SessionManager.getInstance().getSession(MinaUtils.MINA_IO_PORT).getSession();
				if(session!=null){
					System.out.println("iosession is OK !");
					SessionManager.getInstance().getSession(MinaUtils.MINA_IO_PORT).getSession().write(simpack);
				}else{
					System.out.println("iosession is null !");
				}*/
				
				/*try {
					MinaUtils.getStamp("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				//CommandManger.getInstance().beatHeartMsg();
				
				try {
					CommandManger.getInstance().verifyTimeMsg();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		mPanel.add(mSendBtn);

		mCleanBtn = new JButton("clean");
		mCleanBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mSendMsg.setText("");
				mRecArea.setText("");

			}

		});

		mPanel.add(mCleanBtn);

		mStartBtn = new JButton("start");
		mStartBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new Thread(new StartTask()).start();
				mStartBtn.setEnabled(false);
				mStopBtn.setEnabled(true);
				mSendBtn.setEnabled(true);

			}

		});

		mPanel.add(mStartBtn);

		mStopBtn = new JButton("stop");
		mStopBtn.setEnabled(false);
		mStopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread(new StopTask()).start();
				mStopBtn.setEnabled(false);
				mStartBtn.setEnabled(true);
				mSendBtn.setEnabled(false);
			}

		});
		mPanel.add(mStopBtn);

		// mPanel.add(mRecArea);

		add(mPanel, BorderLayout.PAGE_START);
		add(mStatusPanel, BorderLayout.CENTER);

	}

	protected ImageIcon createImageIcon(String path, String description) {
		
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				MinaClient c = new MinaClient();

			}
		});

	}

	private class StartTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			StartClient();
		}

	}

	private class StopTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			StopClient();
		}

	}

	private NioSocketConnector connector;
	private ConnectFuture futrue;
	private MinaClientHandler mClientHandler;
	//private ClientKeepAliveMessageFactoryImp mKeepAlive;

	private void StartClient() {

		// 创建一个socket连接
		connector = new NioSocketConnector();
		// 获取过滤器链
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		ProtocolCodecFilter filter = new ProtocolCodecFilter(new ServerSimCodecFactory(Charset.forName("UTF-8"))/*new ClientCodecFactory(Charset.forName("UTF-8"))*/);
		// 添加编码过滤器 处理乱码、编码问题
		chain.addLast("objectFilter", filter);

		// connector.getFilterChain().addLast("codec", new
		// ProtocolCodecFilter(new TextLineCodecFactory()));
		/*
		 * mKeepAlive=new ClientKeepAliveMessageFactoryImp(); KeepAliveFilter
		 * heartBeat = new KeepAliveFilter(mKeepAlive, IdleStatus.BOTH_IDLE);
		 * heartBeat.setForwardEvent(true); heartBeat.setRequestInterval(5);
		 * connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		 */
		// connector.getFilterChain().addLast("keepalive", heartBeat);
		/*connector.getFilterChain().addLast("keepalive", new KeepAliveFilter(new KeepAliveMessageFactoryImp(),
				IdleStatus.BOTH_IDLE, new AliveTimeoutHandler()
																 * KeepAliveRequestTimeoutHandler
																 * .DEAF_SPEAKER
																 , 10, 5));*/
		/*
		 * 开始心跳
		 * */
		
		
		// 消息核心处理器
		mClientHandler = new MinaClientHandler();
		mClientHandler.setUpdateViewListener(this);
		connector.setHandler(mClientHandler/* new MinaClientHandler() */);
		// 设置链接超时时间
		connector.setConnectTimeoutCheckInterval(30);
		// 连接服务器，知道端口、地址
		futrue = connector.connect(new InetSocketAddress("localhost", MinaUtils.MINA_PORT));
		// 等待连接创建完成
		futrue.awaitUninterruptibly();
		//futrue.getSession().getCloseFuture().awaitUninterruptibly();

		//CSession wrapper = new CSession(futrue.getSession(), MinaUtils.MINA_PORT, 10005);
		//SessionManager.getInstance().addSession(MinaUtils.MINA_PORT, wrapper);

		/*
		 * for(int i=0;i<2;i++){
		 * 
		 * futrue = connector.connect(new InetSocketAddress("localhost",
		 * MinaUtils.MINA_PORT)); // 等待连接创建完成 futrue.awaitUninterruptibly();
		 * String message = "client heart"; ObjectPack pack = new ObjectPack();
		 * pack.setYear(2016); pack.setName("client init");
		 * pack.setDataLen(message.length()); byte[] bt =
		 * MinaUtils.String2Byte(message); pack.setBt(bt);
		 * futrue.getSession().write(pack);
		 * //futrue.getSession().getCloseFuture().awaitUninterruptibly();
		 * 
		 * try { Thread.sleep(500); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 */

	}

	private void StopClient() {

		try {
			if (connector != null) {
				if (!connector.isDisposed()) {
					connector.dispose();
				}
			}
		} catch (Exception e) {

		}

	}

	@Override
	public void onUpdateView(String message) {
		// TODO Auto-generated method stub
		mRecArea.setText(mRecArea.getText() + "\n" + message);

	}

}
