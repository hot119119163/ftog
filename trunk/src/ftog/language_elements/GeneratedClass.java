package ftog.language_elements;

import java.io.IOException;

import ftog.main.ClassFileUtils;
import ftog.main.FlexCodeFormatting;

public interface GeneratedClass {

	public abstract String getPackage();

	public abstract void setPackage(String p);

	public abstract String getClassName();

	public abstract void setClassName(String c);

	public abstract void addProperty(Property p);

	public abstract void addConstant(Constant c);

	public abstract void addImport(Import i);

	public abstract void addContructorParameter(Property p);

	public abstract void purgeUnrelatedImports();

	public abstract boolean getBindable();

	public abstract void setBindable(boolean value);

	public abstract String toCode();

	public abstract String toCode(FlexCodeFormatting format);

	public abstract boolean seemsToBeTransferObject();

	public abstract void sortProprties();

	public abstract void expandImports(String fromDir);

	public abstract void writeToDisk(ClassFileUtils cfu) throws IOException;

	public abstract String getSuperClassName();

	public abstract void setSuperClassName(String superClassName);

	public abstract String getRemoteClassPackageName();

	public abstract void setRemoteClassPackageName(String remoteClassName);

	public abstract void clearConstructorParameters();

	public abstract void applyRefactoring();

}