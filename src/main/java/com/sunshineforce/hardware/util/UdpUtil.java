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
        byte[] buf = new byte[10240];
        try {
            //建立udp的服务 ，并且要监听一个端口
            socket = new DatagramSocket(port);
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // 1024
            //调用udp的服务接收数据
            socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
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
        String[] ipArr = ip.split("\\.");
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < ipArr.length; i++){
            if(Integer.toHexString(Integer.parseInt(ipArr[i])).length() <= 1){
                builder.append("0").append(Integer.toHexString(Integer.parseInt(ipArr[i])));
            }else{
                builder.append(Integer.toHexString(Integer.parseInt(ipArr[i])));
            }
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    public static String hexToip(String hex){
        String ip1 = hex.substring(0, 2);
        Integer ip1Int = Integer.parseInt(ip1, 16);
        String ip2 = hex.substring(2, 4);
        Integer ip2Int = Integer.parseInt(ip2, 16);
        String ip3 = hex.substring(4, 6);
        Integer ip3Int = Integer.parseInt(ip3, 16);
        String ip4 = hex.substring(6, 8);
        Integer ip4Int = Integer.parseInt(ip4, 16);
        return ip1Int+"."+ip2Int+"."+ip3Int+"."+ip4Int;
    }

    public static String macTpHex(String mac){
        return mac.replace(":","");
    }

    public static void main(String args[]){
        hexToip("c0a80068");
    }
}
