+-------------------------------------------------------------------------------+
| Flex Data Transfer Object Generator.                                   	|
+-------------------------------------------------------------------------------+
|  Copyright (C) 2008 Mattias Ånstrand                                  	|
|  mattias@loveone.se                                                           |
+-------------------------------------------------------------------------------+
|  This program is free software: you can redistribute it and/or modify         |
|  it under the terms of the GNU Lesser General Public License as published by  |
|  the Free Software Foundation, either version 3 of the License, or            |
|  (at your option) any later version.                                          |
|                                                                               |
|  This program is distributed in the hope that it will be useful,              |
|  but WITHOUT ANY WARRANTY; without even the implied warranty of               |
|  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                |
|  GNU Lesser General Public License for more details.                          |
|                                                                               |
|  You should have received a copy of the GNU Lesser General Public License     |
|  along with this program.  If not, see <http://www.gnu.org/licenses/>.        |
+-------------------------------------------------------------------------------+ 

This program is built on Júlio Vilmar Gesser's excellent Java parser which is 
released under LGPLv3. Included in this distribution is Log4j which is released
under the Apache License 2.0. Copies of the licenses are included under the
"example_project/licenses"-folder.   

This program with its source can be downloaded from: http://ftog.googlecode.com

Don't hesitate to contact me, using the above email, if you have questions, 
bugs to report or you need help with your Flex-project. 

For instructions how to use the generator read the instructions in "running.txt" 
in the included "example_project"-folder.


Source distribution:
--------------------
example_project/licenses/	- The related licenses for this distribution.
example_project/lib    	- The built jar (ftog.jar) goes here. The Log4j-libraries needed 
							for compilation and execution is also stored here.
classes/  	- Placeholder for .class-files
conf/    	- Log4j-configuration.
script/  	- Ant build script for building Flex DTO Generator (ftog).
src/ftog 	- Java source for the program.
src/japa 	- Source for Gesser's Java parser.

example_project/- An example project that uses the generator.

.settings/	
.classpath
.project	- Eclipse project files
README.txt	- This file.


Requirements:
-------------
Java version 5 or later. Ant. The generator can parse any version of java.


Building the program:
---------------------
Make sure you have ant installed and in your path. Help with that can be found 
at: http://ant.apache.org. Place yourself in the "script"-folder and run ant. 
This will create a "ftog.jar"-file under the "example-project/lib"-folder. That's it! 
For instructions how to use the program, please read the "RUNNING.txt"-file under 
the "example_project"-folder.  


Version history
---------------
2010-07-27 Unrelated classes are now pruned from the generated imports. Increased logging. 
			Some support for constructor initializers. Bug fixes.
2008-04-13 Corrected some errors in texts.
2008-02-29 New ant attribute "classignorelist" for the ftog-task implemented.
2008-02-18 "static final"-constants are now translated to "static const". Bugfix with BigInteger/BigDecimal 
2008-02-17 Released at Google Code.
2008-01-16 First version.

