
public class Class {
	
	//declare variables
	private  int CRN;
	private String professorName;
	private String className;
	private String department;
	private String book;
	
	
	//constructors
	public Class(int CRN) {
		this.CRN=CRN;
	}
	public Class(int CRN, String professorName,String className, String department) {
		this.CRN = CRN;
		this.professorName = professorName;
		this.className = className;
		this.department = department;				
	}
	public Class(String professorName,String className, String department) {
		this.professorName = professorName;
		this.className = className;
		this.department = department;	
	}
	
	
	//getters
	public int getCRN() {
		return CRN;
	}
	public String getProfessorName() {
		return professorName;
	}
	public String getClassName() {
		return className;
	}
	public String getDepartment() {
		return department;
	}
	public String getRecentBook() {
		return book;
	}
	
	//setters
	public void setCRN(int CRN) {
		this.CRN =CRN;
	}
	public void setProfessorName(String professorName) {
		this.professorName =professorName;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setRecentBook(String book) {
		this.book = book;
	}
	
	
	//update book
	public void setNewBook(String newBook) {
		book = newBook;
	}
	
}
