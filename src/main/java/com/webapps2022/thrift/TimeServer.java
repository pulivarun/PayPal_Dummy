package com.webapps2022.thrift;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.server.TServer;

@Startup
@Singleton 
public class TimeServer {
    
    public void TimeServer(){
        try {
            TServerSocket serverTransport = new TServerSocket(10001);
            TimeServerHandler handler = new TimeServerHandler();
            TimeService.Processor processor = new TimeService.Processor(new TimeServerHandler());
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            server.serve();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    
    @PostConstruct
    public void init() {
       
    }

    @PreDestroy
    public void clean() {
    
    }
}