package com.mylearning.interviewquestions;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GlobalRelayService extends Thread {
	
     GlobalRelayServiceConfig config;
     
     
   @Override
	public void run(){
		
	    try {
			
	    	while(true)
	    		listen();
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		
	}
	
    private ServerSocket server;
    
    public GlobalRelayService(GlobalRelayServiceConfig cfg) throws Exception {
    	
              this.config = cfg;
    }
    
    private void listen() throws Exception {
    	
         //Service is listening
    	 this.server = new ServerSocket(this.config.getPort(), 1, InetAddress.getByName(this.config.getHost()));
         
    	 
         //After every 30 second service will go down so as to test for liveness
    	 {
    		 this.server.setSoTimeout(30000);
    	 }
         
         
         Socket client = null;
         
         try{
        	 //Service is coming up . Set the state in the global map
        	 GlobalRelayTaskAsgnmt.setServiceState(config.getServiceName(), GlobalRelayTaskAsgnmt.SERVICE_UP);
        	        	 
        	 client = this.server.accept();
        	 
        	 
         }catch(SocketTimeoutException ex){
        	 
        	 System.out.println("Socket Timeout Exception");
        	 //Sleep on time out. Means //Service will be down for 5 seconds
        	 Thread.sleep(5000);
            	
         }
    	//Now lets sleep for 5 seconds
         GlobalRelayTaskAsgnmt.setServiceState(config.getServiceName(), GlobalRelayTaskAsgnmt.SERVICE_DOWN);
         server.close();
         
         //Sleep for 5 seconds so that service remains down
         //monitor liveness can check up and down both
         if(this.config.shouldSleep())
        	 Thread.sleep(5000);
         
        
    }
 
 

}
