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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ftog.main.ClassFileUtils;
import ftog.main.FlexCodeFormatting;


public class FlexClass {
	private String packageName;
	private String className;
	private String superClassName;
	private String remoteClassPackageName;
	private ArrayList properties;
	private ArrayList constants;
	private ArrayList imports;
	private boolean bindable;
	
	
	private int indentionLevel;
	private FlexCodeFormatting format;
	
	public FlexClass() {
		properties = new ArrayList();
		constants = new ArrayList();
		imports = new ArrayList();
		bindable=true;
	}
	
	public String getPackage() {
		return packageName;
	}
	
	public void setPackage(String p) {
		packageName = p;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String c) {
		className = c;
	}

	public void addProperty(Property p) {
		properties.add(p);
	}
	
	public void addConstant(Constant c) {
		constants.add(c);
	}
	
	public void addImport(String i) {
		imports.add(i);
	}
	
	public boolean getBindable() {
		return bindable;
	}
	
	public void setBindable(boolean value) {
		bindable=value;
	}
	
	public String toCode() {
		Property.childNumber=0;
		FlexCodeFormatting format = new FlexCodeFormatting();
		return toCode(format);
	}
	
	public String toCode(FlexCodeFormatting format) {
		this.format = format;
		StringBuffer code = new StringBuffer();
		addConstants(code);
		addProperties(code);
		addClassDeclaration(code);
		if(imports.size()>0) {
			insertEmtyRow(code);
			addImports(code);
		}
		insertEmtyRow(code);
		addPackageDeclaration(code);
		return code.toString();
	}
	
	public boolean seemsToBeTransferObject() {
		return (properties.size()>0 || constants.size()>0 || superClassName!=null);
	}
	
	public void sortProprties() {
		Collections.sort(properties);
	}
	
	public void writeToDisk(ClassFileUtils cfu) throws IOException {
		Writer out = cfu.createDirectoriesAndOpenStream(packageName, className);
		out.write(toCode());
		out.flush();
		out.close();
	}
	
	private void addProperties(StringBuffer code) {
		indentionLevel=2;
		Iterator i = properties.iterator();
		while(i.hasNext()) {
			Property p = (Property)i.next();
			code.append(indent(p.toFlexCode()));
		}
	}
	
	private void addConstants(StringBuffer code) {
		indentionLevel=2;
		Iterator i = constants.iterator();
		while(i.hasNext()) {
			Constant c = (Constant)i.next();
			code.append(indent(c.toFlexCode()));
			if(!i.hasNext())
				addEmtyRow(code);
		}
	}
	
	private void addImports(StringBuffer code) {
		Iterator i = imports.iterator();
		while(i.hasNext()) {
			String s = (String)i.next();
			code.insert(0, indent("import "+s+";"));
		}
	}

	private void addBindable(StringBuffer code) {
		indentionLevel=1;
		code.append(indent("[Bindable]"));
	}

	private void addRemoteClass(StringBuffer code) {
		indentionLevel=1;
		if(remoteClassPackageName!=null)
			code.append(indent("[RemoteClass(alias=\""+remoteClassPackageName+"."+className+"\")]"));
	}

	private void addAnnotations(StringBuffer code) {
		if(bindable)
			addBindable(code);
		
		addRemoteClass(code);
	}
	
	private void addClassDeclaration(StringBuffer code) {
		indentionLevel=1;
		code.insert(0, getClassDeclarationString());
		code.append(indent("}"));
	}
	
	public String getSuperClassName() {
		return superClassName;
	}

	public void setSuperClassName(String superClassName) {
		this.superClassName = superClassName;
	}

	private String getClassDeclarationString() {
		StringBuffer sb=new StringBuffer();
		addAnnotations(sb);
		StringBuffer sb2 = new StringBuffer();
		sb2.append("public class "+className);
		if(superClassName!=null && !"Object".equals(superClassName))
			sb2.append(" extends "+superClassName);

		sb2.append(" {");
		sb.append(indent(sb2.toString()));
		
		return sb.toString();
	}

	
	private void addPackageDeclaration(StringBuffer code) {
		indentionLevel=0;
		code.insert(0, indent("package "+packageName+" {"));
		code.append(indent("}"));
	}

	private void addToString(StringBuffer code) {

	}
	
	private String indent(String in) {
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<indentionLevel;i++)
			sb.append(format.indentionToken);
		
		sb.append(in);
		sb.append(format.lineTerminator);
		return sb.toString();
	}
	
	private void insertEmtyRow(StringBuffer sb) {
		sb.insert(0, format.lineTerminator);
	}
	
	private void addEmtyRow(StringBuffer sb) {
		sb.append(format.lineTerminator);
	}

	public String getRemoteClassPackageName() {
		return remoteClassPackageName;
	}

	public void setRemoteClassPackageName(String remoteClassName) {
		this.remoteClassPackageName = remoteClassName;
	}
}
 