package weblayer;

public class EmployeeInfo {
	private int empid ;
	private String emprole;
	private String address ;
	private int phone;
	private String email ;
	private String fname ;
		
	 public EmployeeInfo(int empid, String emprole, String address, int phone, String email, String fname){
		super();
		this.empid = empid;
		this.emprole = emprole;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.fname = fname;
	}
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public String getEmprole() {
		return emprole;
	}
	public void setEmprole(String emprole) {
		this.emprole = emprole;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	@Override
	public String toString() {
		return "EmployeeInfo [empid=" + empid + ", emprole=" + emprole + ", address=" + address + ", phone=" + phone
				+ ", email=" + email + ", fname=" + fname + "]";
	}
	
}
