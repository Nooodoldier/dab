package com.bling.dab.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

/**
 * @author: hxp
 * @date: 2019/4/18 14:27
 * @description:得到主机名和ip
 */
public class HostUtil {

    private static final Logger logger = LoggerFactory.getLogger(HostUtil.class);

    /**
     * 获取流水号
     *
     * @return
     * @throws Exception
     */
    public static String getFlowId(){
        String flowId="";
        try{
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateFormatString = dateFormat.format(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHMMssSSS");
            String simpleDateFormatString = simpleDateFormat.format(date);
            Random random = new Random();
            int n = random.nextInt(90) + 10;
            flowId = dateFormatString.substring(2) + HostUtil.getHostName().substring(0, 2) + "dab" + simpleDateFormatString + n;
        }catch(Exception e){
            logger.error("generate transId error ", e);
        }
        return flowId;
    }

    /**
     * 获取主机名
     *
     * @return
     * @throws Exception
     */
    public static String getHostName() throws Exception {
        InetAddress localAddress = HostUtil.getLocalInterAddress();
        String hostName = "";
        try {

            InetAddress localHost = InetAddress.getLocalHost();
            hostName = localHost.getHostName();
        } catch (UnknownHostException e) {
            logger.error("getHostName method throw UnknownHostException.message:", e);
        }
        if (!"".equals(hostName)) {
            return hostName;
        }
        if (localAddress != null) {
            return localAddress.getHostName();
        }
        return null;
    }

    public static String getIp() throws Exception {
        InetAddress localAddress = HostUtil.getLocalInterAddress();
        String hostAddress = "";
        try {

            InetAddress localHost = InetAddress.getLocalHost();
            hostAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("getHostName method throw UnknownHostException.message:", e);
        }
        if (!"".equals(hostAddress)) {
            return hostAddress;
        }
        if (localAddress != null) {
            return localAddress.getHostAddress();
        }
        return null;
    }

    /**
     * getLocalInterAddress
     *
     * @return
     * @throws Exception
     */
    public static InetAddress getLocalInterAddress() throws Exception {
        Enumeration<NetworkInterface> enu = NetworkInterface.getNetworkInterfaces();
        while (enu.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) enu.nextElement();
            if (!ni.isLoopback()) {
                Enumeration<InetAddress> addressEnumeration = ni.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    InetAddress address = (InetAddress) addressEnumeration.nextElement();
                    if ((!address.isLinkLocalAddress()) && (!address.isLoopbackAddress())
                            && (!address.isAnyLocalAddress())) {
                        return address;
                    }
                }
            }
        }
        return null;
    }

}
