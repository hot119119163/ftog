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

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.Node;
import japa.parser.ast.visitor.VoidVisitor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.log4j.Logger;

import ftog.language_elements.FlexClass;


public class FlexDTOGen {
	private Logger log;

	private HashMap classTree;
	private JavaParser parser;
	private ClassFileUtils cfu;
	private File destDir;
	
	public FlexDTOGen() {
		log = Logger.getLogger(FlexDTOGen.class);
		cfu = new ClassFileUtils();
	}
	
	public void processJavaFiles(Collection files) throws ParseException, IOException {
		Collection iss = openFiles(files);
		translateClasses(iss);
	}
	
	public Collection openFiles(Collection files) throws FileNotFoundException {
		ArrayList iss = new ArrayList();
		Iterator it = files.iterator();
		while(it.hasNext()) {
			iss.add(new java.io.FileInputStream((it.next().toString())));
		}
		return iss;
 	}
	
	
	private void translateClasses(Collection files) throws IOException, ParseException {
		Iterator it = files.iterator();
		while(it.hasNext()) {
			InputStream is = (InputStream)it.next();
			
			ClassVisitor cv = new ClassVisitor();
			processJavaFile(is, cv);
			
			FlexClass fc = cv.getFlexClass();
			cv=null;
			fc.sortProprties();
			log.debug("AS:\n"+fc.toCode());
			if(fc.seemsToBeTransferObject()) {
				log.info("Writing: "+fc.getPackage()+"."+fc.getClassName());
				fc.writeToDisk(cfu);
			}
		}	
	}
	
	
	private void processJavaFile(InputStream is, VoidVisitor<Object> v) throws ParseException {
		if(parser==null)
			parser = new japa.parser.JavaParser(is);
		else
			parser.ReInit(is);
		
		Node root = parser.CompilationUnit();
		root.accept(v, null);
	}

	public File getDestDir() {
		return cfu.getDestDir();
	}

	public void setDestDir(File destDir) {
		cfu.setDestDir(destDir);
	}
	
}
