package ftog.main;

import java.io.File;
import java.io.FilenameFilter;

public class JavaFilenameFilter implements FilenameFilter {

	public JavaFilenameFilter() {
	}

	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".java");
	}

}
