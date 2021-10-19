package com.mylearning.interviewquestions;

public class GlobalRelayServiceConfig {
	
	/************  Global Relay Service  class  *********/
	
	private String serviceName ;
	
	private String host;
	
	private Integer port;
	
	private long plannedOutageStartTime;
	
	private long plannedOutageEndTime;
	
	private Integer pollingFrequency;
	
	private Integer gracePeriod;
	
	private String state;
	
	private long lastDownTime ;
	
	//This is just to simulate the service down behavior
	//if set to true service will go down after some time
	private boolean shouldSleep;
	

	
	public boolean shouldSleep() {
		return shouldSleep;
	}



	public void setShouldSleep(boolean shouldSleep) {
		this.shouldSleep = shouldSleep;
	}



	public long getLastDownTime() {
		return lastDownTime;
	}



	public void setLastDownTime(long lastDownTime) {
		this.lastDownTime = lastDownTime;
	}



	public Integer getGracePeriod() {
		return gracePeriod;
	}



	public void setGracePeriod(Integer gracePeriod) {
		this.gracePeriod = gracePeriod;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public GlobalRelayServiceConfig(String serviceName,
									String host,
									Integer port,
									Integer pollingFrequency,
									Integer gracePeriod,
									boolean shouldSleep)
	{
		
		this.serviceName = serviceName;
		this.host = host;
		this.port = port;
		this.pollingFrequency = pollingFrequency;
		this.gracePeriod = gracePeriod;
		this.shouldSleep = shouldSleep;
	}
	
	
	




	public void setPlannedOutageEndTime(long plannedOutageEndTime) {
		this.plannedOutageEndTime = plannedOutageEndTime;
	}



	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public long getPlannedOutageStartTime() {
		return plannedOutageStartTime;
	}

	public void setPlannedOutageStartTime(long plannedOutageStartTime) {
		this.plannedOutageStartTime = plannedOutageStartTime;
	}

	public long getPlannedOutageEndTime() {
		return plannedOutageEndTime;
	}

	public void setPlannedOutagEndTime(long plannedOutageEndTime) {
		this.plannedOutageEndTime = plannedOutageEndTime;
	}

	public Integer getPollingFrequency() {
		return pollingFrequency;
	}

	public void setPollingFrequency(Integer pollingFrequency) {
		this.pollingFrequency = pollingFrequency;
	}


	
	
	

}
