package com.mylearning.interviewquestions;

//This class represents the Caller for the services 
//They have a notify method which just prints the notification to console out put
public class GlobalRelayServiceCaller {
	
	   //Caller per Service and each caller has frequency of monitoring
	      
	     String  serviceName;
	     Integer pollingFrequency;
	     
	    
	    
	     GlobalRelayServiceCaller(String serviceName, Integer frequency){
	    	 
	    	 this.serviceName = serviceName;
	    	 this.pollingFrequency= frequency;  
	     }
	    
	    public void notifyCaller(String serviceName, String state){
	    	
	    	//System.out.println("NOTIFICATION #####"+serviceName +" Service " +" is "+state);
	    }

}
