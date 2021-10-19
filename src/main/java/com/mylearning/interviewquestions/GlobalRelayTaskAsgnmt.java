package com.mylearning.interviewquestions;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.mylearning.interviewquestions.GlobalRelayService;

//  This is the main class . Program starts at the main fucntion here  

public class GlobalRelayTaskAsgnmt {
	
	
	 //Global Map which Holds Service Configuration and Service Current State
	 //HashTabe , so as to keep it synchronized
	static Map<String, GlobalRelayServiceConfig> services = new Hashtable<String, GlobalRelayServiceConfig>();
	static Map<String, GlobalRelayServiceCaller> callers = new HashMap<String, GlobalRelayServiceCaller>();
	
	//TODO Move these to Constants file
	static String SERVICE_MICROSOFT = "Microsoft";
	static String SERVICE_GOOGLE = "Google";
	static String LOCALHOST = "127.0.0.1";
	static String SERVICE_UP = "UP";
	static String SERVICE_DOWN = "DOWN";
	static Integer SERVICE_MICROSOFT_PORT = 3331;
	static Integer SERVICE_GOOGLE_PORT = 3332;
	
	//TODO Create a properties file and at the startup load the configuration and populate map
	static {
		
		//There are two services e.g. Microsoft and Google which will be up for 3 minutes then they will shut down
		// and restarted after 5 seconds (  Thread will sleep and socket will be reopened)
		services.put(SERVICE_MICROSOFT, new GlobalRelayServiceConfig(SERVICE_MICROSOFT, LOCALHOST, SERVICE_MICROSOFT_PORT, 2000, 300, true ));
		services.put(SERVICE_GOOGLE, new GlobalRelayServiceConfig(SERVICE_GOOGLE, LOCALHOST, SERVICE_GOOGLE_PORT, 3000, 300 , true));
		
		//callers for the service
		
		callers.put(SERVICE_GOOGLE, new GlobalRelayServiceCaller(SERVICE_GOOGLE, 2000));
		callers.put(SERVICE_MICROSOFT, new GlobalRelayServiceCaller(SERVICE_MICROSOFT, 1000));
	}
	
    public static void main(String[] args) throws Exception {
    	
      
    	//Start Global Relay Services. One Thread for each Service
    	for( String serviceName : services.keySet()){
    	
    	  GlobalRelayService app = new GlobalRelayService(services.get(serviceName));
           
    	  app.start();
    	    
    	}  
    	  
       
    	//Global Relay Service Monitors. One Monitor Per Service
    	for( String serviceName : services.keySet()){
    		
    		GlobalRelayServiceMonitor monitor = new GlobalRelayServiceMonitor(services.get(serviceName));
    		
    		monitor.start();
    	}
    	
    	
    }
    
    //Common Utility function to set current date time format
    public static String getCurrentTimeStamp() {
    	
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(new Date(System.currentTimeMillis()));
        return strDate;
    }
    
    //update the state of the service in the map
    //Take care of Grace period and planned shutdown
    synchronized public static void setServiceState(String serviceName , String currState){
    	
    	GlobalRelayServiceConfig config =  services.get(serviceName);
    	
    	if(currState == SERVICE_UP){
    		
    	   config.setState(currState);
    	   notifyCaller(config.getServiceName(), currState);
    	}
    	else {

          long curTimeInMili = System.currentTimeMillis();
          
           //Check for the Planned Outage 
          if( config.getPlannedOutageStartTime() <= curTimeInMili &&  config.getPlannedOutageEndTime() >= curTimeInMili )
          {
        	  //This is the planned outage  so we will keep the service state as up
        	  config.setState(SERVICE_UP);
        	  //Do not notify the caller
        	  
          }//Check for grace Period before notifying the Service Down
          else if(curTimeInMili - config.getLastDownTime() > config.getGracePeriod()) {
        	  
        	  config.setState(SERVICE_UP);
        	  config.setLastDownTime(curTimeInMili);
        	  
          }else {
        	  //Service is down notify the caller 
        	  config.setState(SERVICE_DOWN);
        	  notifyCaller(config.getServiceName(), SERVICE_DOWN);
          }
    		
       }
    	
    	services.put(serviceName, config);
    	
    }
    
    //***********************    Update the Map which Keeps the State *******
    synchronized public static void setServiceDownTime(String serviceName , long downTime){
    	
    	//Update service down time in the Global map
    	GlobalRelayServiceConfig config =  services.get(serviceName);
    	config.setLastDownTime(downTime);
    	services.put(serviceName, config);
    }
    
    
    //*************** Notify the caller for the Servic and Down State 
     public static void notifyCaller(String serviceName , String state){
    	 
    	 //TODO handle multiple Caller for same service
    	 GlobalRelayServiceCaller  caller = callers.get(serviceName);
    	 caller.notifyCaller(serviceName, state);
     }
    
    
   }


