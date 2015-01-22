package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientVO extends CompanyVO implements Serializable {
	private final static long serialVersionUID = 2;

	public ArrayList<ProjectVO> projects;
	public ConsultantVO accountManager;
}
