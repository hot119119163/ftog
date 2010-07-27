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

package ftog.main;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.Type;

import java.util.*;

import org.apache.log4j.Logger;

import ftog.language_elements.Constant;
import ftog.language_elements.Property;



public class JavaToFlexClassConverter {
	private HashMap convertionTable;
	private static Logger log;
	
	static {
		log = Logger.getLogger(JavaToFlexClassConverter.class);
	}
	
	public JavaToFlexClassConverter() {
		convertionTable = new HashMap();
		convertionTable.put("boolean", "Boolean");
		
		convertionTable.put("float", "Number");
		convertionTable.put("Float", "Number");
		convertionTable.put("double", "Number");
		convertionTable.put("Double", "Number");
		convertionTable.put("long", "Number");
		convertionTable.put("Long", "Number");
		
        convertionTable.put("int", "int");
        convertionTable.put("Integer", "int");
        convertionTable.put("short", "int");
        convertionTable.put("Short", "int");
        
        convertionTable.put("String", "String");
        convertionTable.put("char", "String");
        convertionTable.put("Char", "String");
		convertionTable.put("BigDecimal", "String");
		convertionTable.put("BigInteger", "String");        
        
		convertionTable.put("Collection", "ArrayCollection");
		convertionTable.put("Set", "ArrayCollection");
		convertionTable.put("HashSet", "ArrayCollection");
		convertionTable.put("List", "ArrayCollection");
		convertionTable.put("ArrayList", "ArrayCollection");
		convertionTable.put("Map", "Object");
		convertionTable.put("HashMap", "Object");
		convertionTable.put("Exception", "Error");
	}
	
	//Getter
	public void convert(Property p, MethodDeclaration md) {
		p.javaClass=md.type.toString();
		findChildTypes(p, 0);
		p.flexClass=convertClassName(p);
	}

	//Setter
	public void convert(Property p, Parameter param) {
		p.javaClass=param.type.toString();
		findChildTypes(p, param.id.arrayCount);
		p.flexClass=convertClassName(p);
	}

	//Field
	public void convert(Property p, FieldDeclaration f) {
		p.javaClass=f.type.toString();
		findChildTypes(p, p.arrayCount);
		p.flexClass=convertClassName(p);
	}
	
	//Constant
	public void convert(Constant c, FieldDeclaration f) {
		c.javaClass=f.type.toString();
		c.flexClass=convertClassName(c);
	}
	
	private String convertClassName(Property p) {
		if(p.arrayCount>0) {
            String returnString = "Array";
   
            return returnString;
        }

		String flexClassName = (String)convertionTable.get(p.javaClass);	
		
		if(flexClassName!=null)
			return flexClassName;
		
		return p.javaClass;
	}
	
	public String convertClassClass(String name) {
		String flexClass =  (String)convertionTable.get(name);
		if(flexClass!=null)
			return flexClass;
		
		return name;
	}
	
	private void findChildTypes(Property p, int idArrayCount) {
		String typeName = p.javaClass;
		String[] genericTypes = typeName.split("[<,>]");
		String[] typeBrackets = typeName.split("\\["); 
		int typeArrayCount = typeBrackets.length-1;
		int generic = genericTypes.length-1;
		if(typeArrayCount+idArrayCount+generic==0)
			return;

		p.arrayComment=typeName;
		log.debug("Generics:"+Arrays.toString(genericTypes));
		p.arrayCount=typeArrayCount+idArrayCount;
		log.debug("type-ArrayCount:"+typeArrayCount);
		log.debug("Total array count:"+p.arrayCount);
		if(generic>0) {
			p.childType=genericTypes[genericTypes.length-1].trim();
			p.javaClass=genericTypes[0];
		}
		else
			p.childType=typeBrackets[0];
		
		String childFlexType=(String)convertionTable.get(p.childType);
		if(childFlexType!=null) {
			p.childType=childFlexType;
			//if the class was in the convertionTable it doesn't 
			//need to be included as a child reference.
			p.addReferenceToChildType=false;
	    }
	}
}
