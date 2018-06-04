package test;


public class Courses {
 
	private int crn;
	private String name;
	private String classname;
	private String department;
	private String book;
	private String latest;
	
	public Courses(int crn, String name, String classname, String department, String book, String latest) {
	     this.crn = crn;
	     this.name = name;
	     this.classname = classname;
	     this.department = department;
	     this.book = book;
	     this.latest = latest;
	}
	
	public int getCRN(){
		return this.crn;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getClassName(){
		return this.classname;
	}
	
	public String getDepartment(){
		return this.classname;
	}
	
	public String getBooks(){
		return this.book;
	}
	
	public String getLatest(){
		return this.latest;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setClassName(String classname){
		this.classname = classname;
	}
	
	public void setDepartment(String department){
		this.department = department;
	}
	
	public void setBooks(String book){
		this.book = book;
	}
	
	public void setLatest(String latest){
		this.latest = latest;
	}
	
	
}