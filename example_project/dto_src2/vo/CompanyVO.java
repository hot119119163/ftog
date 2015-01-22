package vo;

import java.io.Serializable;

public class CompanyVO implements Serializable{
	private final static long serialVersionUID = 2;

	public long companyId;
	public String name;
	public AddressVO address;
	public String companyType;
	public String contactPerson;
	public String telephone;
	public String email;
	public String web;	
}
