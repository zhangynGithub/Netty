package com.itmayiedu;
import java.io.IOException;  
import java.net.Socket;  
import java.net.UnknownHostException;  
import java.nio.ByteBuffer;  
  
/** 
 * @author Chalmers 2016年2月24日 下午2:35:39 
 */  
public class Client {  
  
    public static void main(String[] args) throws UnknownHostException,  
            IOException {  
        Socket socket = new Socket("127.0.0.1", 9090);  
  
        String message = "hello";  
  
        byte[] bytes = message.getBytes();  
  
        // 设置空间大小为一个存储了长度的int型数据（长度）加上转换后的byte数组  
        ByteBuffer buffer = ByteBuffer.allocate(4 + bytes.length);  
        // 将长度存入  
        buffer.putInt(bytes.length);  
        // 将数据存入  
        buffer.put(bytes);  
  
        // 转换成字节数组  
        byte[] array = buffer.array();  
  
        // 向服务端发送1000次  
        for (int i = 0; i < 1000; i++) {  
            socket.getOutputStream().write(array);  
        }  
  
        // 关闭  
        socket.close();  
    }  
}  