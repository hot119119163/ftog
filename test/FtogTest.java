import ftog.ant.FTOGTask;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.junit.Test;

import java.io.File;

public class FtogTest {

  @Test
  public void testGenJS() {
    FTOGTask task = new FTOGTask();
    Project p = new Project();
    p.setBaseDir(new File("."));
    task.setProject(p);
    task.setCreateconstructor(true);
    task.setGenerateJavascript(true);
    task.setTodir(new File("generated"));
    FileSet fs = new FileSet();
    fs.setDir(new File("example_project/dto_src"));
    fs.setIncludes("**/*.java");
    task.addFileset(fs);
    task.execute();
  }
}
