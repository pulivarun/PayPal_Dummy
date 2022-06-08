package com.webapps2022.thrift;

import org.apache.thrift.TException;
public class TimeServerHandler implements TimeService.Iface {
    @Override
    public long time() throws TException {
        long time = System.currentTimeMillis();
        System.out.println("time() called: " + time);
        return time;
    }
}