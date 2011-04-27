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

import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.visitor.VoidVisitor;

import org.apache.log4j.Logger;

import ftog.refactor.RefactorSingleton;
import ftog.visitor.BaseVisitor;

public class NameVisitor extends BaseVisitor implements VoidVisitor<Object> {

	private Logger log;
	
	private StringBuffer name;
	private String from;
	private String to;
	
	public NameVisitor() {
		log = Logger.getLogger(NameVisitor.class);
		name = new StringBuffer();
	}
	
	public void visit(NameExpr n, Object o) {
		name.append(n.toString());
	}
		
	public String getName() {
		return name.toString();
	}
}
