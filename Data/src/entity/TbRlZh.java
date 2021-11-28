package entity;

public class TbRlZh {
	
	private String code;
	private String fathcode;
	private String name;
	private String gradename;
	private int persum;
	private int jssum;
	private int agesum;
	private int worksum;
	
	
	public String getFathcode() {
		return fathcode;
	}
	public void setFathcode(String fathcode) {
		this.fathcode = fathcode;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public int getJssum() {
		return jssum;
	}
	public void setJssum(int jssum) {
		this.jssum = jssum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPersum() {
		return persum;
	}
	public void setPersum(int persum) {
		this.persum = persum;
	}
	public int getAgesum() {
		return agesum;
	}
	public void setAgesum(int agesum) {
		this.agesum = agesum;
	}
	public int getWorksum() {
		return worksum;
	}
	public void setWorksum(int worksum) {
		this.worksum = worksum;
	}
	@Override
	public String toString() {
		return "TbRlZh [code=" + code + ", fathcode=" + fathcode + ", name=" + name + ", gradename=" + gradename
				+ ", persum=" + persum + ", jssum=" + jssum + ", agesum=" + agesum + ", worksum=" + worksum + "]";
	}
	
}
