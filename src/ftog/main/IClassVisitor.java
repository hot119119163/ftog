package ftog.main;

import japa.parser.ast.visitor.VoidVisitor;

import java.util.HashSet;

import ftog.language_elements.GeneratedClass;

public interface IClassVisitor extends  VoidVisitor<Object> {

	public abstract GeneratedClass getGeneratedClass();

	public abstract HashSet getClassIgnoreList();

	public abstract void setClassIgnoreList(HashSet classIgnoreList);

}