package com.mylearning.interviewquestions;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class GlobalRelayServiceMonitor extends Thread {

	//monitor will keep service config data in order to check
	//service status
	GlobalRelayServiceConfig config;
	
    
	
	GlobalRelayServiceMonitor(GlobalRelayServiceConfig config){
		 this.config = config;
	}
	
	
	
	@Override
	public void run(){
		
	    try {
			//Lets run this forever now 
	    	//TODO Need to run it only for some time say 2 minutes.
	     	while(true){
	     			pollServiceByName();
	     	}
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		
	}
	
	
	public void pollServiceByName() throws Exception  {
		
		
		try{
			
		  Socket client  = new Socket();
		  //InetAddress inetAddress = new InetAddress(config.getHost(), config.getPort())
		  SocketAddress serverSocketAddress=new InetSocketAddress(config.getHost(), config.getPort());  
           //Check if the service is up
		  
		  //client.connect(arg0);
		  //client.bind(serverSocketAddress);
		  
		  client.connect(serverSocketAddress);
		  
		  if(client.isConnected()) {
			 
			  System.out.println("Global Relay " + config.getServiceName() + " Service is up at "+GlobalRelayTaskAsgnmt.getCurrentTimeStamp());
			 
			  GlobalRelayTaskAsgnmt.setServiceState(config.getServiceName(),GlobalRelayTaskAsgnmt.SERVICE_UP);
		  }
		  
		 // client.close();
		
		  
		  Integer pollingfreq = config.getPollingFrequency();
		  
		  //Never poll service less than 1 second
		  if(pollingfreq < 1000)
			    pollingfreq = 1000;

		// Do not ping before the polling interval  
		  Thread.sleep(pollingfreq);
		  
		}catch(Exception ex)
		{
			
			ex.printStackTrace();
			System.out.println("Global Relay " + config.getServiceName() + " Service is down  " +GlobalRelayTaskAsgnmt.getCurrentTimeStamp());
			
			GlobalRelayTaskAsgnmt.setServiceState(config.getServiceName(),GlobalRelayTaskAsgnmt.SERVICE_DOWN);
			//Store the time when service is down
			config.setLastDownTime(System.currentTimeMillis());
			
			Thread.sleep(config.getPollingFrequency());
		}
		
		
		
		
	}
	
	
	
	
	
}
