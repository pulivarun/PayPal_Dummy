package com.webapps2022.ejb;

import com.webapps2022.thrift.TimeService;
import javax.ejb.Stateless;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

@Stateless
public class TimeServiceEJB {

    public TimeServiceEJB() {
    }
    
    public static long getTime() {
        long time = 0;

        try {
            TTransport transport;

            transport = new TSocket("localhost", 10001);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            TimeService.Client client = new TimeService.Client(protocol);

            transport.open();
            time = client.time();
            
            transport.close();

        } catch (TException x) {
            System.err.println(x);
        }
        return time;
    }
}
