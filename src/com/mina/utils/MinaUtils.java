package com.mina.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MinaUtils {

	public static final int MINA_PORT = 7799;
	public static final String MINA_IP = "127.0.0.1";

	public static int MINA_IO_PORT;
	
	public static byte[] intToBytes( int value )   
	{   
	    byte[] src = new byte[4];  
	    src[3] =  (byte) (value & 0xFF);  
	    src[2] =  (byte) ((value>>8) & 0xFF);  
	    src[1] =  (byte) ((value>>16) & 0xFF);  
	    src[0] =  (byte) ((value>>24) & 0xFF);                  
	    return src;   
	}  
	
	public static byte[] String2Byte(String message) {

		byte[] byBuffer = new byte[200];
		String strInput = message;// "abcdefg";
		byBuffer = strInput.getBytes();

		return byBuffer;

	}

	public static String Byte2String(byte[] buffer) {

		byte[] byBuffer = buffer;// new byte[20];
		String strRead = new String(byBuffer);
		strRead = String.copyValueOf(strRead.toCharArray(), 0, byBuffer.length);

		return strRead;
	}

	public static String Byte2String(byte[] buffer, int len) {

		byte[] byBuffer = buffer;// new byte[20];
		String strRead = new String(byBuffer);
		strRead = String.copyValueOf(strRead.toCharArray(), 0, len);

		return strRead;
	}

	public static String byteArr2HexStr(byte[] arrB) throws Exception {

		if(arrB==null){
			return "";
		}
		int iLen = arrB.length;

		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);

		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}

			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16) + " ");
		}
		return sb.toString().toUpperCase();
	}

	public static String byte2HexStr(byte arrB) throws Exception {

		int iLen = 1;

		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);

		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB;
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}

			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16) + " ");
		}
		return sb.toString().toUpperCase();
	}

	public static String intToByteArray(int i) throws Exception {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		
		
		return byteArr2HexStr(result);
	}
	
	public static String shortToByteArray(short s) throws Exception {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return byteArr2HexStr(targets);  
    }  
	
	public static byte[] longToByteArray(long s) {
        byte[] targets = new byte[6];
        for (int i = 0; i < 6; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }
	
	public static byte[] longToByteArrays(long s,int len) {
        byte[] targets = new byte[len];
        for (int i = 0; i < len; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }
	
	public static byte[] byteMerger(byte[] bt1, byte[] bt2){  
        byte[] bt3 = new byte[bt1.length+bt2.length];  
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);  
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);  
        return bt3;  
    } 
	
	public static short CRC(byte[] arr){
		return CRC16.checkByBit(arr, 0, arr.length);/*(short)CRC16.calcCrc16(arr);*/
	}
	
	public static long byteArrayToLong(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        long result = 0;
        try {
            int len = dis.available();
            if (len == 1) {
                result = dis.readByte();
            } else if (len == 2) {
                result = dis.readShort();
            } else if (len == 4) {
                result = dis.readInt();
            } else if (len == 8) {
                result = dis.readLong();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
	
	private static String MD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private static String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}

	public static String getSysTime(){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		
		return df.format(new Date());
	}
	
	/*
	 * 獲取時間戳
	 * */
	/* 
     * 将时间转换为时间戳
     */    
    public static byte[] getStamp(String s) throws Exception{
    	String deftime="2010-01-01 00:00:00";
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date defdate = simpleDateFormat.parse(deftime);
        long defts = defdate.getTime();
        
        String currenttime=simpleDateFormat.format(new Date());
        Date date = simpleDateFormat.parse(currenttime);
        long curts = date.getTime();
        
        res = String.valueOf(curts-defts);
        System.out.println("stamp : "+res);
        
        byte[] stamp=longToByteArray(curts-defts);
        System.out.println("stamp hex : "+byteArr2HexStr(stamp));
        return stamp;
    }
    
    public static byte[] getPassword(String ip,String password){
    	
    	String md5=MD5(password);
    	md5=ip.toUpperCase()+md5;
    	 	
    	System.out.println(md5.getBytes());
    	
    	return md5.getBytes();
    }
    
	
}
