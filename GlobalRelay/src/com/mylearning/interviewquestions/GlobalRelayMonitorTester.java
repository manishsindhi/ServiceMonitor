package com.mylearning.interviewquestions;

import java.util.Hashtable;
import java.util.Map;

public class GlobalRelayMonitorTester {

	
	//Global Map which Holds Service Configuration and Service Current State
	//HashTabe , so as to keep it synchronized
	static Map<String, GlobalRelayServiceConfig> services = new Hashtable<String, GlobalRelayServiceConfig>();
	
	//TODO Move these to Constants file
	static String SERVICE_MICROSOFT = "Microsoft";
	static String SERVICE_GOOGLE = "Google";
	static String LOCALHOST = "127.0.0.1";
	static String SERVICE_UP = "UP";
	static String SERVICE_DOWN = "DOWN";
	static Integer SERVICE_MICROSOFT_PORT = 3331;
	static Integer SERVICE_GOOGLE_PORT = 3332;
	
	//Services  -->  Microsoft and Google
	static {
		
		//There are two services e.g. Microsoft and Google
		services.put(SERVICE_MICROSOFT, new GlobalRelayServiceConfig(SERVICE_MICROSOFT, LOCALHOST, SERVICE_MICROSOFT_PORT, 2000, 300, false ));
		//services.put(SERVICE_GOOGLE, new GlobalRelayServiceConfig(SERVICE_GOOGLE, LOCALHOST, SERVICE_GOOGLE_PORT, 3000, 300, false ));
	}
	
	public static void main(String[] args) throws Exception {
		
		//Only run one test case at a time. Comment out rest of the test cases.
		
		/*  Test    Cases     1 */
          everythingOk();
         
        /*  Test Case   2       */
         //  serviceDownTest();
         
        /*  Test Case   3      */
       //   serviceDownWithGraceTime();
           
        /*  Test Case   4       */
          //serviceInPlannedOutage(); 
	}

	
	
	public static void everythingOk() throws Exception{
		
		//Start the test 
			
		//Start Global Relay Services. One Thread for each Service
    	for( String serviceName : services.keySet()){
    	
    	  GlobalRelayService app = new GlobalRelayService(services.get(serviceName));
		  //Start the Services
    	  app.start();
    	    
    	}  
    	//Global Relay Service Monitors. One Monitor Per Service
    	for( String serviceName : services.keySet()){
 //   		
    		GlobalRelayServiceMonitor monitor = new GlobalRelayServiceMonitor(services.get(serviceName));
    		//Start the Monitor
    		monitor.start();
    	}
		
		
	}
	 
	/*Test Case 2*/
	public static void serviceDownTest() throws Exception{
		
		// Change the sleep flag in the global map so that services will sleep
		//and we can test service down feature
		for(GlobalRelayServiceConfig config : services.values())
			config.setShouldSleep(true);
		
		
		for( String serviceName : services.keySet()){
	    	
	    	  GlobalRelayService app = new GlobalRelayService(services.get(serviceName));
			  //Start the Services
	    	  app.start();
	    	    
	    	}  
	    	//Global Relay Service Monitors. One Monitor Per Service
	    	for( String serviceName : services.keySet()){
	    		
	    		GlobalRelayServiceMonitor monitor = new GlobalRelayServiceMonitor(services.get(serviceName));
	    		//Start the Monitor
	    		monitor.start();
	    	}
		
		
	}
	
	/*  Test Case 3   */ 
	 public static void serviceDownWithGraceTime() throws Exception{
		 
		 for(GlobalRelayServiceConfig config : services.values()){
				config.setGracePeriod(100000);
				config.setShouldSleep(true);
		 }
			
			
			for( String serviceName : services.keySet()){
		    	
		    	  GlobalRelayService app = new GlobalRelayService(services.get(serviceName));
				  //Start the Services
		    	  app.start();
		    	    
		    	}  
		    	//Global Relay Service Monitors. One Monitor Per Service
		    	for( String serviceName : services.keySet()){
		    		
		    		GlobalRelayServiceMonitor monitor = new GlobalRelayServiceMonitor(services.get(serviceName));
		    		//Start the Monitor
		    		monitor.start();
		    	}
		 
	 }
	 
	 /*Test Case 4*/
	 public static void servceInPlannedOutage() throws Exception{
		 
		 
		 for(GlobalRelayServiceConfig config : services.values()){
				config.setPlannedOutageStartTime(478889890);
				config.setPlannedOutageEndTime(478899890);
				config.setShouldSleep(true);
		 }
			
			
			for( String serviceName : services.keySet()){
		    	
		    	  GlobalRelayService app = new GlobalRelayService(services.get(serviceName));
				  //Start the Services
		    	  app.start();
		    	    
		    	}  
		    	//Global Relay Service Monitors. One Monitor Per Service
		    	for( String serviceName : services.keySet()){
		    		
		    		GlobalRelayServiceMonitor monitor = new GlobalRelayServiceMonitor(services.get(serviceName));
		    		//Start the Monitor
		    		monitor.start();
		    	}
		 
	 }
	 
	
}
