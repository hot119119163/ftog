/*
 * Copyright (C) 2008 Mattias �nstrand.
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

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import ftog.language_elements.Constant;
import ftog.language_elements.FlexClass;
import ftog.language_elements.Import;
import ftog.language_elements.JavascriptClass;
import ftog.language_elements.Property;
import ftog.visitor.BaseVisitor;


public class JavascriptClassVisitor extends BaseVisitor implements IClassVisitor {
	private Logger log;
	private HashMap getters;
	private HashMap setters;
	private JavaToFlexClassConverter converter;
	private HashSet classIgnoreList;

	private JavascriptClass flexClass;
	
	public JavascriptClassVisitor() {
		log = Logger.getLogger(JavascriptClassVisitor.class);
		flexClass = new JavascriptClass();
		getters = new HashMap();
		setters = new HashMap();
		converter = new JavaToFlexClassConverter();
	}
	
	public JavascriptClass getGeneratedClass() {
		return flexClass;
	}
	
	public void visit(PackageDeclaration n, Object o) {
//		MoveRefactoring mr = new MoveRefactoring();
	
		NameVisitor nv = new NameVisitor();
		nv.visit(n.name, null);
		log.debug("PackageDeclaration:"+nv.getName());
		flexClass.setRemoteClassPackageName(nv.getName());
		flexClass.setPackage(nv.getName());
	}

	public void visit(ImportDeclaration n, Object o) {
		NameVisitor nv = new NameVisitor();
		nv.visit(n.name, null);
		String name = nv.getName() + (n.isAsterisk ? ".*" : "");
		log.debug("import:"+name);

		//This is a QUAD solution
		if("java.math.BigInteger".equals(name))
			return;
		if("java.math.BigDecimal".equals(name))
			return;
		if("java.io.Serializable".equals(name))
			return;
		if(classIgnoreList.contains(name))
			return;
		if(name.startsWith("java.util.")) {
			if(!name.endsWith("Date"))
				flexClass.addImport(new Import("mx.collections.ArrayCollection"));
			
			return;
		}
		

		flexClass.addImport(new Import(name));
	}
	
	public void visit(ClassOrInterfaceDeclaration c, Object o) {
		if(c.isInterface)
			return;
		
		String className = c.name;
		String fullName = flexClass.getRemoteClassPackageName()+"."+className;
		if(classIgnoreList.contains(fullName)) {
			log.info("Ignoring: "+fullName);
			return;
		}
		else
			log.info("Parsing: "+fullName);
		
		flexClass.setClassName(converter.convertClassClass(className));
		
		//We are only interested in first element of extendsList since the rest
		//only applies for interfaces.
		String extendsClassName=null;
		if(c.extendsList!=null && c.extendsList.size()==1)
			extendsClassName= c.extendsList.get(0).name; 
		log.debug("extends ClassName:"+extendsClassName);
		flexClass.setSuperClassName(converter.convertClassClass(extendsClassName));
		
		super.visit(c, null);
	}
	
    public void visit(FieldDeclaration f, Object o) {
        //TODO: comma separated declarations ignored!
     	log.debug("searchForProperty: (field)");
    	int modifiers = f.modifiers;
     	if(!accessable(modifiers))
    	   return;
    		   
   	   if(ModifierSet.isStatic(modifiers) && ModifierSet.isFinal(modifiers)) {
   		   addConstant(f);
   		   return;
   	   }
   	   
    	List<VariableDeclarator> variables = f.variables;
    	Iterator<VariableDeclarator> it = variables.iterator();
    	while(it.hasNext()) {
    	  	Property p = new Property();
    	    VariableDeclarator variable = it.next();
        	p.name = variable.id.name;
        	p.arrayCount = variable.id.arrayCount;
        	if(variable.init!=null)
        		p.initValue=variable.init.toString();
        	converter.convert(p, f);
        	log.debug("field type:"+p.flexClass);
        	log.debug("Adding property:"+p.name);
        //	flexClass.addProperty(p);   
    	}
    }

    public void addConstant(FieldDeclaration f) {
//TODO:Check that constant is suitable type    	if(f.type!="String" && f.type!="int" && f.type="Integer" && )
    	List<VariableDeclarator> variables = f.variables;
    	Iterator<VariableDeclarator> it = variables.iterator();
    	while(it.hasNext()) {
    	  	Constant c = new Constant();
    	    VariableDeclarator variable = it.next();
        	c.name = variable.id.name;
        	converter.convert(c, f);
        	c.value = variable.init;
        	//Remove number constant suffix
        	if(isNumberClass(c.flexClass))
        		c.value=c.value.toString().replaceAll("[lfdLFD]", "");
        	
        	log.debug("Adding constant property:"+c.name);
        	flexClass.addConstant(c);   
    	}
    }
    
    private boolean isNumberClass(String s) {
    	return "Number".equals(s) || "int".equals(s) || "uint".equals(s);
    }
    
    public void visit(ConstructorDeclaration c, Object o) {
    	if(c.parameters==null)
    		return;
    	
 //   Jonas Mode 
    	Iterator it = c.parameters.iterator();
    	while(it.hasNext()) {
    		Parameter p = (Parameter) it.next();
    		Property prop = new Property();
    		prop.javaClass=p.type.toString();
    		prop.name=p.id.name;
    		prop.flexClass=converter.convertClassClass(prop.javaClass);
    		flexClass.addContructorParameter(prop);
    	}
    }
    
    public void visit(MethodDeclaration m, Object o) {
    	int modifiers = m.modifiers;
    	String returnType = m.type.toString();
    	log.debug("searchForProperty:  (method)");
    	if(!accessable(modifiers))
		   return;

		 Property p = new Property();
		 if(m.name.startsWith("get")) {
			p.name = getPropertyName(m.name);
			converter.convert(p, m);
			log.debug("FlexClass (getter):"+p.flexClass);
			getters.put(p.name, p);
		  } 
		 	else if(m.name.startsWith("set")) {
				p.name = getPropertyName(m.name);
				converter.convert(p, m.parameters.get(0));			   
				log.debug("FlexClass (setter):"+p.flexClass);
				setters.put(p.name, p);
		  } 
		 	else if(m.name.startsWith("is")) {
			p.name = getPropertyName(m.name);
			if("boolean".equals(m.type.toString()) || "Boolean".equals(m.type.toString())) {					   
				p.flexClass = "Boolean";
				p.javaClass = m.type.toString();
				log.debug("FlexClass (is-getter):"+p.flexClass);
				getters.put(p.name, p);
			}
		  }
		  	else return;
		   
		  Property getter = (Property)getters.get(p.name);
		  Property setter = (Property)setters.get(p.name);
		  if(getter!=null && setter !=null && getter.equals(setter)) {			
			  flexClass.addProperty(getter);
			  log.debug("Adding property:"+p.name);
		   }
		   
		  super.visit(m, null);
    }
   
    
   private boolean accessable(int modifiers) {
   	   if(ModifierSet.isPrivate(modifiers))
   		   return false;
   	   if(ModifierSet.isTransient(modifiers))
   		   return false;
   	   if(ModifierSet.isPublic(modifiers))
   		   return true;
   	   
   	   return false;
  }
   
   private String getPropertyName(String in) {
	   String name="";
	   log.debug("Processing:"+in);
	   int pos = -1;
	   if(in.startsWith("is"))
		   pos = 2;
	   if(in.startsWith("get") || in.startsWith("set"))
		   pos = 3;
	   
	   
	   //This is to deal with that "getCONSTANT" should create the property "CONSTANT" not "cONSTANT";
	   char secondCharacterInPropertyName='a';
	   if(in.length()>pos+1)
		   secondCharacterInPropertyName = in.toCharArray()[pos+1];
	   
	   if(in.length()>pos)
		   if(!Character.isUpperCase(secondCharacterInPropertyName))
		   		name = in.substring(pos, pos+1).toLowerCase();
		   else
	   			name = in.substring(pos, pos+1);
	   else {
		   log.debug("returning empty string! (\"get\" is a valid function name in Actionscript)");
		      return "";
	   }
	   
	   if(in.length()>pos+1)
		   name+=in.substring(pos+1,in.length());
	   
	   log.debug("returning:"+name);
	   return name;
   }

public HashSet getClassIgnoreList() {
	return classIgnoreList;
}

public void setClassIgnoreList(HashSet classIgnoreList) {
	this.classIgnoreList = classIgnoreList;
}

}
