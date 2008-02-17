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

public class FlexCodeFormatting {
	public static final String TAB_IDENTION="\t";
	public static final String SPACE_IDENTION="    ";
	public String indentionToken;
	
	
	public static final String PC_LINE_TERMINATOR="\r\n";
	public static final String UNIX_LINE_TERMINATOR="\n";
	public String lineTerminator;
	
	public FlexCodeFormatting() {
		indentionToken = SPACE_IDENTION;
		lineTerminator = System.getProperties().getProperty("line.separator");
	}
}
