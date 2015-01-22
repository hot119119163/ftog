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

package ftog.ant;

import japa.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import ftog.main.FlexDTOGen;
import ftog.refactor.NameRefactoring;
import ftog.refactor.RefactorSingleton;

public class FTOGTask extends Task{
	
	private ArrayList javaFilesets;	
	private FlexDTOGen generator;
	private String fromTo;
	
	private Logger log;
	
	public FTOGTask() {
		javaFilesets = new ArrayList();
		generator = new FlexDTOGen();
		log = Logger.getLogger(FTOGTask.class);
	}

    @Override
	 public void execute() throws BuildException {
		 ArrayList files = new ArrayList();
		 for(int i = 0; i < javaFilesets.size(); i++) {
			 FileSet fs = (FileSet)javaFilesets.get(i);
			 DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			 File dir = fs.getDir(getProject());
			 log.debug("DIR:"+dir);
			 generator.setFromDir(dir.toString());
			 String[] srcs = ds.getIncludedFiles();
			 for (int j = 0; j < srcs.length; j++) {
				 files.add(dir+File.separator+srcs[j]);
			 }
			 processJavaFiles(files);
		 }
	 }

	 private void processJavaFiles(Collection fileNames) throws BuildException {
		 try {
			 generator.processJavaFiles(fileNames);
		 }
		 catch(ParseException pex) {
			 log.error("Parsing error!", pex);
			 throw new BuildException("Parsing error!", pex);
		 }
		 catch(IOException ioex) {
			 log.error("IO error!", ioex);
			 throw new BuildException("IO error!", ioex);
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public void setTodir(File dir) {
		 generator.setDestDir(dir);
		 log.debug("Setting dir to:"+dir);
	 }
	 
	 public void setClassignorelist(String commaseparatedClassNames) {
		 generator.setClassIgnoreList(commaseparatedClassNames);
		 log.debug("Igonoring classes:"+commaseparatedClassNames);
	 } 
	 
	 public void setRefactorfrom(String from) {
		 if(fromTo==null)
			 fromTo=from;
		 else
			 addNameRefactoring(from, fromTo);
	 }
	 
	 public void setRefactorto(String to) {
		 if(fromTo==null)
			 fromTo=to;
		 else
			 addNameRefactoring(fromTo, to);
	 }
	
	 private void addNameRefactoring(String from, String to) {
		 log.debug("Adding name refactoring!");
		 RefactorSingleton.getInstance().addRefactoring(new NameRefactoring(from, to));
	 }

	 public void setCreateconstructor(boolean value) {
		 generator.setCreateConstructor(value);
	 }
	 
	 public void addFileset(FileSet in) {
		 javaFilesets.add(in);
	 }
	 
	 public void setGenerateJavascript(boolean generateJavascript) {
		generator.generateJavascript(generateJavascript);
	 }
}
