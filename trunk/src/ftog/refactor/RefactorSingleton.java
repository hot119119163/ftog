package ftog.refactor;

import java.util.ArrayList;

import japa.parser.ast.Node;

public class RefactorSingleton {
	
	private static RefactorSingleton instance;
	
	public static RefactorSingleton getInstance() {
		if(instance==null)
			instance=new RefactorSingleton();
	
		return instance;
	}
	
	private ArrayList<Refactoring> refactorings;

	private RefactorSingleton() {
		refactorings = new ArrayList<Refactoring>();
	}
	
	public String applyRefactorings(String n) {
		if(refactorings.size()>0)
			for(Refactoring r :refactorings) {
					n = r.execute(n);
					return n;
			}
		
		return n;
	}
	
	public void addRefactoring(Refactoring r) {
		refactorings.add(r);
	}
}
