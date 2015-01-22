package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ProjectVO implements Serializable {
	
	public ProjectVO() {
		
	}
	
	public ProjectVO(String name) {
		this();
		this.name = name;
	}
	
	private final static long serialVersionUID = 2;
	public long projectId;
	public String name ;
	public ArrayList<ActionVO> actions;
	public float pricePerHour;
	public float fixedPrice;
	public boolean priceIsFixed;
	public CategoryVO category;
    public Date lastDay;
	
	public boolean equals(Object project)
	{
		if(! (project instanceof ProjectVO))
			return false;
		else
			if(((ProjectVO)project).projectId == projectId)
				return true;
			else 
				return false;

			
	}
}
