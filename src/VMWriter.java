import java.io.FileWriter;
import java.io.IOException;

public class VMWriter {
	private FileWriter xmlFile;
    
	public VMWriter(String basename) {
		try {
		    xmlFile = new FileWriter(basename + ".vm");
		 } catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		 }
	}
	
	 public void writePush(String segment, String integer) {
			try {
				xmlFile.write("push "+segment + " "+ integer + "\n");
			}catch (IOException e) {
			    e.printStackTrace();
			    System.exit(1);
			}
		    }
	public void writePop(String segment, String integer) {
		try {
			xmlFile.write("pop "+segment + " "+ integer + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeArithmetic(String command) {
		try {
			xmlFile.write(command + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeLabel(String label) {
		try {
			xmlFile.write("label "+label + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeGoto(String label) {
		try {
			xmlFile.write("goto "+label + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeIf(String label) {
		try {
			xmlFile.write("if-goto "+label + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeCall(String name, String nArgs) {
		try {
			xmlFile.write("call "+name + " " + nArgs + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeFunction(String name, String nArgs) {
		try {
			xmlFile.write("call "+name + " " + nArgs + "\n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	
	public void writeReturn() {
		try {
			xmlFile.write("return \n");
		}catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		}
	}
	public void close() {
			try {
			    xmlFile.close();
			} catch (IOException e) {
			    e.printStackTrace();
			    System.exit(1);
			}
		    }
}
