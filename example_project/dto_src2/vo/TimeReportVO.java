package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeReportVO implements Serializable {
	private final static long serialVersionUID = 2;
	public long timeReportId;
	public ProjectVO project;
	public ArrayList<WorkDayVO> workDays;
}
