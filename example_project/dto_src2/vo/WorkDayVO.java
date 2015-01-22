package vo;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class WorkDayVO implements Serializable{
	private final static long serialVersionUID = 2;
	public Date beginDate;
	public ArrayList<ActionVO> actions;
}
