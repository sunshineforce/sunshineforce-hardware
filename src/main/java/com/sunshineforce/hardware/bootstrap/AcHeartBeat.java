package com.sunshineforce.hardware.bootstrap;

import com.sunshineforce.hardware.domain.Wifi;
import com.sunshineforce.hardware.util.ByteUtil;
import com.sunshineforce.hardware.util.UdpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AcHeartBeat {

    private static final String REQUESTTYPE = "b7";

    public static byte[] requestSelect(Wifi wifi) {
        byte[] responseBuf = new byte[200];
        AtomicInteger atomicInteger = new AtomicInteger();
        while(true){
            DatagramSocket socket = null;
            try {
                byte[] heartBeatBuf = new byte[15];
                socket = new DatagramSocket(9595);
                DatagramPacket datagramPacket = new DatagramPacket(heartBeatBuf, heartBeatBuf.length); // 1024
                //调用udp的服务接收数据
                socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
                //解析
                byte[] type = new byte[1];
                log.info("-------" + ByteUtil.ByteToHex(heartBeatBuf));
                System.arraycopy(heartBeatBuf, 2, type, 0, type.length);
                byte[] mac = new byte[6];
                System.arraycopy(heartBeatBuf, 5, mac, 0, mac.length);
                byte[] ip = new byte[4];
                System.arraycopy(heartBeatBuf, 11, ip, 0, ip.length);
                log.info("-------" + ByteUtil.ByteToHex(ip));
                log.info("-------" + wifi.getProbeMac().toLowerCase());

                if(REQUESTTYPE.equals(ByteUtil.ByteToHex(type).toLowerCase()) && wifi.getProbeMac().toLowerCase().equals(ByteUtil.ByteToHex(mac).toLowerCase())) {
                    byte[] heartBeatResponse = AcConfig.getHeartBeatResponse(wifi.getProbeMac());
                    DatagramPacket heartBeatResponsePacket = new DatagramPacket(heartBeatResponse, heartBeatResponse.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), datagramPacket.getPort());
                    socket.send(heartBeatResponsePacket);
                    log.info("握手成功");

                    byte[] serverMsg = AcConfig.requestSelect(wifi);
                    DatagramPacket serverPacket = new DatagramPacket(serverMsg, serverMsg.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(serverPacket);
                    log.info("向ip:{}:{}发送查询探针信息消息：{}成功", UdpUtil.hexToip(ByteUtil.ByteToHex(ip)), heartBeatResponsePacket.getPort(), ByteUtil.ByteToHex(serverMsg));

                    //获取结果
                    while (true) {
                        responseBuf = new byte[200];
                        DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length); // 1024
                        socket.receive(responsePacket);
                        log.info("-------response:-------"+ByteUtil.ByteToHex(responseBuf));
                        atomicInteger.incrementAndGet();
                        break;
                    }
                    log.info("6666666");
                    break;
                }else{
                    log.info("握手失败，重试");
                    atomicInteger.incrementAndGet();
                    if(atomicInteger.get() >= 2){
                        socket.close();
                        break;
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
        return responseBuf;
    }

    public static void restart(Wifi wifi) {
        while(true){
            DatagramSocket socket = null;
            try {
                byte[] heartBeatBuf = new byte[15];
                socket = new DatagramSocket(9595);
                DatagramPacket datagramPacket = new DatagramPacket(heartBeatBuf, heartBeatBuf.length); // 1024
                //调用udp的服务接收数据
                socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
                //解析
                byte[] type = new byte[1];
                System.arraycopy(heartBeatBuf, 2, type, 0, type.length);
                byte[] mac = new byte[6];
                System.arraycopy(heartBeatBuf, 5, mac, 0, mac.length);
                byte[] ip = new byte[4];
                System.arraycopy(heartBeatBuf, 11, ip, 0, ip.length);
                log.info("-------" + ByteUtil.ByteToHex(ip));
                log.info("-------" + wifi.getProbeMac().toLowerCase());

                if(REQUESTTYPE.equals(ByteUtil.ByteToHex(type).toLowerCase()) && wifi.getProbeMac().toLowerCase().equals(ByteUtil.ByteToHex(mac).toLowerCase())) {
                    byte[] heartBeatResponse = AcConfig.getHeartBeatResponse(wifi.getProbeMac());
                    DatagramPacket heartBeatResponsePacket = new DatagramPacket(heartBeatResponse, heartBeatResponse.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), datagramPacket.getPort());
                    socket.send(heartBeatResponsePacket);
                    log.info("握手成功");

                    byte[] restartMsg = AcConfig.getRestartMsg(wifi);
                    DatagramPacket serverPacket = new DatagramPacket(restartMsg, restartMsg.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(serverPacket);
                    log.info("向ip:{}:{}发送探针重启消息：{}成功", UdpUtil.hexToip(ByteUtil.ByteToHex(ip)), heartBeatResponsePacket.getPort(), ByteUtil.ByteToHex(restartMsg));
                    break;
                }else{
                    log.info("握手失败，重试");
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
    }

    public static int responseHeartBeatWithAcConfig(Wifi wifi) {
        AtomicInteger atomicInteger = new AtomicInteger();
        while(true){
            DatagramSocket socket = null;
            try {
                byte[] heartBeatBuf = new byte[15];
                socket = new DatagramSocket(9595);
                DatagramPacket datagramPacket = new DatagramPacket(heartBeatBuf, heartBeatBuf.length); // 1024
                //调用udp的服务接收数据
                socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
                //解析
                byte[] type = new byte[1];
                log.info("-------" + ByteUtil.ByteToHex(heartBeatBuf));
                System.arraycopy(heartBeatBuf, 2, type, 0, type.length);
                byte[] mac = new byte[6];
                System.arraycopy(heartBeatBuf, 5, mac, 0, mac.length);
                byte[] ip = new byte[4];
                System.arraycopy(heartBeatBuf, 11, ip, 0, ip.length);
                if(REQUESTTYPE.equals(ByteUtil.ByteToHex(type).toLowerCase()) && wifi.getProbeMac().toLowerCase().equals(ByteUtil.ByteToHex(mac).toLowerCase())){
                    byte[] heartBeatResponse = AcConfig.getHeartBeatResponse(wifi.getProbeMac());
                    DatagramPacket heartBeatResponsePacket = new DatagramPacket(heartBeatResponse, heartBeatResponse.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), datagramPacket.getPort());
                    socket.send(heartBeatResponsePacket);
                    log.info("握手成功");

                    byte[] configMsg = AcConfig.getConfigMsg(wifi);
                    DatagramPacket configPacket = new DatagramPacket(configMsg, configMsg.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(configPacket);
                    log.info("发送配置消息成功");

                    //重启
                    byte[] restartByte = AcConfig.getRestartMsg(wifi);
                    DatagramPacket restartPacket = new DatagramPacket(restartByte, restartByte.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(restartPacket);
                    log.info("6666666666");
                    break;
                }else{
                    log.info("握手失败，重试");
                    atomicInteger.incrementAndGet();
                    if(atomicInteger.get()>=2){
                        return 0;
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
        return 1;
    }

    public static int responseHeartBeatWithToggleWifi(Wifi wifi) {
        AtomicInteger atomicInteger = new AtomicInteger();
        while(true){
            DatagramSocket socket = null;
            try {
                byte[] heartBeatBuf = new byte[15];
                socket = new DatagramSocket(9595);
                DatagramPacket datagramPacket = new DatagramPacket(heartBeatBuf, heartBeatBuf.length); // 1024
                //调用udp的服务接收数据
                socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
                //解析
                byte[] type = new byte[1];
                log.info("-------" + ByteUtil.ByteToHex(heartBeatBuf));
                System.arraycopy(heartBeatBuf, 2, type, 0, type.length);
                byte[] mac = new byte[6];
                System.arraycopy(heartBeatBuf, 5, mac, 0, mac.length);
                byte[] ip = new byte[4];
                System.arraycopy(heartBeatBuf, 11, ip, 0, ip.length);
                log.info("-------" + ByteUtil.ByteToHex(ip));
                log.info("-------" + wifi.getProbeMac().toLowerCase());

                if(REQUESTTYPE.equals(ByteUtil.ByteToHex(type).toLowerCase()) && wifi.getProbeMac().toLowerCase().equals(ByteUtil.ByteToHex(mac).toLowerCase())){
                    byte[] heartBeatResponse = AcConfig.getHeartBeatResponse(wifi.getProbeMac());
                    DatagramPacket heartBeatResponsePacket = new DatagramPacket(heartBeatResponse, heartBeatResponse.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), datagramPacket.getPort());
                    socket.send(heartBeatResponsePacket);
                    log.info("向ip：{}:{}回复握手成功", datagramPacket.getAddress().getHostAddress(), datagramPacket.getPort());

                    byte[] toggleWifiMsg = AcConfig.getToggleWifiMsg(wifi);
                    DatagramPacket toggleWifiPacket = new DatagramPacket(toggleWifiMsg, toggleWifiMsg.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(toggleWifiPacket);
                    log.info("向ip:{}:{}发送开关消息成功",heartBeatResponsePacket.getAddress().getHostAddress(), heartBeatResponsePacket.getPort());

                    //获取结果
                    while (true) {
                        byte[] responseBuf = new byte[12];
                        DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length); // 1024
                        socket.receive(responsePacket);
                        log.info("-------response:-------"+ByteUtil.ByteToHex(responseBuf));
                        break;
                    }

                    //重启
                    byte[] restartByte = AcConfig.getRestartMsg(wifi);
                    DatagramPacket restartPacket = new DatagramPacket(restartByte, restartByte.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(restartPacket);
                    log.info("6666666666");
                    break;
                }else{
                    log.info("握手失败，重试");
                    atomicInteger.incrementAndGet();
                    if(atomicInteger.get()>=2){
                        return 0;
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
        return 1;
    }

    public static int responseHeartBeatWithSetServer(Wifi wifi) {
        AtomicInteger atomicInteger = new AtomicInteger();
        while(true){
            DatagramSocket socket = null;
            try {
                byte[] heartBeatBuf = new byte[15];
                socket = new DatagramSocket(9595);
                DatagramPacket datagramPacket = new DatagramPacket(heartBeatBuf, heartBeatBuf.length); // 1024
                //调用udp的服务接收数据
                socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
                //解析
                byte[] type = new byte[1];
                log.info("-------" + ByteUtil.ByteToHex(heartBeatBuf));
                System.arraycopy(heartBeatBuf, 2, type, 0, type.length);
                byte[] mac = new byte[6];
                System.arraycopy(heartBeatBuf, 5, mac, 0, mac.length);
                byte[] ip = new byte[4];
                System.arraycopy(heartBeatBuf, 11, ip, 0, ip.length);
                log.info("-------" + ByteUtil.ByteToHex(ip));
                log.info("-------" + wifi.getProbeMac().toLowerCase());

                if(REQUESTTYPE.equals(ByteUtil.ByteToHex(type).toLowerCase()) && wifi.getProbeMac().toLowerCase().equals(ByteUtil.ByteToHex(mac).toLowerCase())) {
                    byte[] heartBeatResponse = AcConfig.getHeartBeatResponse(wifi.getProbeMac());
                    DatagramPacket heartBeatResponsePacket = new DatagramPacket(heartBeatResponse, heartBeatResponse.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), datagramPacket.getPort());
                    socket.send(heartBeatResponsePacket);
                    log.info("握手成功");

                    byte[] serverMsg = AcConfig.setServerConfig(wifi);
                    DatagramPacket serverPacket = new DatagramPacket(serverMsg, serverMsg.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(serverPacket);
                    log.info("向ip:{}:{}发送设置定位服务器消息：{}成功", UdpUtil.hexToip(ByteUtil.ByteToHex(ip)), heartBeatResponsePacket.getPort(), ByteUtil.ByteToHex(serverMsg));

                    //重启
                    byte[] restartByte = AcConfig.getRestartMsg(wifi);
                    DatagramPacket restartPacket = new DatagramPacket(restartByte, restartByte.length, InetAddress.getByName(UdpUtil.hexToip(ByteUtil.ByteToHex(ip))), heartBeatResponsePacket.getPort());
                    socket.send(restartPacket);
                    log.info("6666666666");
                    break;
                }else{
                    log.info("握手失败，重试");
                    atomicInteger.incrementAndGet();
                    if(atomicInteger.get()>=2){
                        return 0;
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        }
        return 1;
    }
}
