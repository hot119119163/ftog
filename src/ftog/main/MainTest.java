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

import java.io.File;
import java.util.ArrayList;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
        
		
		MainTest mt = new MainTest();
		mt.start();
	}

	
	public void start() throws Exception  {
        ArrayList<String> javaFiles = findJavaFiles("dto_src/");
        FlexDTOGen ftog = new FlexDTOGen();
        ftog.setDestDir(new File("generatedClasses"));
        ftog.processJavaFiles(javaFiles);
	}
	
	public ArrayList<String> findJavaFiles(String path) {
		ArrayList<String> al = new ArrayList<String>();
		File dir = new File(path);
		File[] files = dir.listFiles();
		setDummy(files);
		for(int i=0;i<files.length;i++) {
			String fileName = files[i].toString();
			if(fileName.endsWith(".java"))
				al.add(fileName);
			else if(files[i].isDirectory())
				al.addAll(findJavaFiles(fileName));
		}
		return al;
	}
	
	public void setDummy(File dummy[]) {
		
	}
}
