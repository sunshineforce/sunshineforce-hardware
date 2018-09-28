package com.sunshineforce.hardware.util;

import java.io.IOException;
import java.net.*;

public class UdpUtil {

    public static String getLocalIp() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostAddress().toString());
        return InetAddress.getLocalHost().getHostAddress().toString();
    }

    public static void sendUdpRequest(byte[] buf) {
        DatagramSocket datagramSocket = null;
        try{
            datagramSocket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost() , 3333);
            datagramSocket.send(packet);
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (SocketException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            datagramSocket.close();
        }
    }

    public static byte[] getUdpResponse(int port) {
        DatagramSocket socket = null;
        byte[] buf = new byte[65507];
        try {
            //建立udp的服务 ，并且要监听一个端口
            socket = new DatagramSocket(port);

            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // 1024
            //调用udp的服务接收数据
            socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
            String ip = datagramPacket.getAddress().getHostAddress();
            int port2 = datagramPacket.getPort();
            System.out.println(ip);
            System.out.println(port2);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
        return buf;
    }

    public static String ipTpHex(String ip){
        String [] ipArr = ip.split(".");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < ipArr.length; i++){
            builder.append(Integer.toHexString(Integer.parseInt(ipArr[i])));
        }
        return builder.toString();
    }

    public static String macTpHex(String mac){
        return mac.replace(":","");
    }
}
