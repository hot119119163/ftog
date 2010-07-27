/*
 * Copyright (C) 2008 Mattias Ånstrand.
 * 
 * This file is part of Flex DTO Generator.
 *
 * Flex DTO Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Flex DTO Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Flex DTO Generator.  If not, see <http://www.gnu.org/licenses/>.
 */

package ftog.language_elements;

import java.util.ArrayList;
import java.util.Iterator;

import ftog.main.FlexCodeFormatting;


public class Property implements Comparable {
	public static int childNumber=0;
	
	public String name;
	public String javaClass;
	public String flexClass;
	public String childType;
	public String arrayComment;
	public String initValue;
	public int arrayCount;
	public boolean addReferenceToChildType=true;
	
	
	
	public boolean isPrivate=false;
	
	public String toFlexCode() {
		StringBuffer sb = new StringBuffer();
		sb.append((isPrivate ? "private " : "public ") +"var "+name+":"+flexClass);
		if(initValue!=null)
			sb.append('=').append(initValue);
		
		if(arrayComment!=null) {
			sb.append("; //"+arrayComment);
			if(addReferenceToChildType && childType!=null) {
				sb.append(FlexCodeFormatting.PC_LINE_TERMINATOR+
						FlexCodeFormatting.SPACE_IDENTION+
						FlexCodeFormatting.SPACE_IDENTION);
				sb.append( "private var child"+childNumber+":"+childType+";");		
				childNumber++;
			}			
		}
		else
			sb.append(";");
		
		return sb.toString();
	}
	public boolean equals(Object o) {
		if(!(o instanceof Property))
			return false;
		
		Property p = (Property) o;
		boolean e = true;
		e = (name==null ? p.name==null : name.equals(p.name)); 
		if(e==false) return e;
		e = javaClass==null ? p.javaClass==null : javaClass.equals(p.javaClass);  
		if(e==false) return e;
		e = flexClass==null ? p.flexClass==null : flexClass.equals(p.flexClass);  
		if(e==false) return e;
		e = childType==null ? p.childType==null : childType.equals(p.childType);  
		if(e==false) return e;
		e = (p!=null && arrayCount==p.arrayCount); 
		
		return e;
	}
	
	public int compareTo(Object otherProperty) {
		Property p = (Property) otherProperty;
		return name.compareTo(p.name);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name:").append(name).append('\n');
		sb.append("javaClass:").append(javaClass).append('\n');
		sb.append("flexClass:").append(flexClass).append('\n');
		sb.append("childType:").append(childType).append('\n');
		sb.append("arrayCount:").append(arrayCount).append('\n');

		return sb.toString();
	}
}
