package work;

import java.util.Date;

public class EmployeeDto {

	private String id;
	private String name;
	private String phonetic;
	private int gender;
	private Date birthDay;
	private Date enteringDate;
	private int fresherFlag;
	private String divisionCd;
	private String divisionName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonetic() {
		return phonetic;
	}

	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getEnteringDate() {
		return enteringDate;
	}

	public void setEnteringDate(Date enteringDate) {
		this.enteringDate = enteringDate;
	}

	public int getFresherFlag() {
		return fresherFlag;
	}

	public void setFresherFlag(int fresherFlag) {
		this.fresherFlag = fresherFlag;
	}

	public String getDivisionCd() {
		return divisionCd;
	}

	public void setDivisionCd(String divisionCd) {
		this.divisionCd = divisionCd;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

}
