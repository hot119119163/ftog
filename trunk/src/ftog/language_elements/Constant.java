package ftog.language_elements;

import ftog.main.FlexCodeFormatting;

public class Constant extends Property {
	public Object value;
	
	public String toFlexCode() {
		StringBuffer sb = new StringBuffer();
		sb.append((isPrivate ? "private " : "public ") +"static const "+name+":"+flexClass+" = "+value+";");		
		return sb.toString();
	}
}
