package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class TestProjectVO implements Serializable {

	public TestProjectVO() {

	}

	public TestProjectVO(String name) {
		this();
		this.name = name;
	}
	
	private final static long serialVersionUID = 2;
	public long projectId;
	public String name ;
	public ArrayList<TestActionVO> actions;
	public float pricePerHour;
}
