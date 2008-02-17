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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

	public class ClassFileUtils {
		
		private File destDir;
		private Logger log;
		
		public ClassFileUtils() {
			log = Logger.getLogger(ClassFileUtils.class);
		}
		
			public Writer createDirectoriesAndOpenStream(String packageName, String className) throws IOException {
				try {
				String replacement;
				if("\\".equals(File.separator))
					replacement = "\\\\";
				else
					replacement = "/";
				
				String packageDir = packageName.replaceAll("\\.",replacement);
				log.debug("Package dir:"+packageDir);
				String directory = File.separator+packageDir;
				File f = new File(destDir, directory);
				log.debug("creating dir:"+f.mkdirs());
				log.debug("dir:"+destDir.toString());
				File g = new File(destDir, directory+File.separator+className+".as");
				
				return new OutputStreamWriter(new FileOutputStream(g), "UTF-8");
				}
				catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}

			public File getDestDir() {
				return destDir;
			}

			public void setDestDir(File destDir) {
				this.destDir = destDir;
			}
	}
