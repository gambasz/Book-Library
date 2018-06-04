package test;

public class Driver {

	public static void main(String[] args) {
		
		DataManager course = new DataManager();
		
		course.readSqlFile();
		course.showContents();
		
		
		
	}
}
