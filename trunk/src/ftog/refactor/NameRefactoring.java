package ftog.refactor;

import org.apache.log4j.Logger;

import japa.parser.ast.Node;
import japa.parser.ast.expr.NameExpr;

public class NameRefactoring implements Refactoring {

	private String from;
	private String to;
	private Logger log;
	
	public NameRefactoring(String from, String to) {
		log = Logger.getLogger(NameRefactoring.class);
		this.from = from;
		this.to = to;
	}
	
	public String execute(String n) {
			return replace(n);
	}
	
	private String replace(String ne) {
		log.debug("Old name:"+ne);
		String newName = ne.replaceAll(escapeDots(from), escapeDots(to));
		log.debug("New name:"+newName);
		return  newName;
	}
	
	private String escapeDots(String in) {
		return in.replaceAll("\\.", "\\\\.");
	}

}
