package vo;

import java.io.Serializable;

public class WorkTimeVO implements Serializable{
	private final static long serialVersionUID = 2;
	public long workTimeId;
    
	public ClientVO client;
	public ProjectVO project;
	public ActionVO action;
	public WorkDayVO workDay;
}




