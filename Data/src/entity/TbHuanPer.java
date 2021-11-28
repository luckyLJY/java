package entity;


public class TbHuanPer 
{
 
    private String orgname;
    private Long age;
    private Long workage;
    private String userCode;
    private String userName;
    private String polityname;
    private String profname;
    private String psnclname;
    private String eduname;
    private Long sex;

    public void setOrgname(String orgname) 
    {
        this.orgname = orgname;
    }

    public String getOrgname() 
    {
        return orgname;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setWorktime(Long worktime) 
    {
        this.workage = worktime;
    }

    public Long getWorktime() 
    {
        return workage;
    }
    public void setUserCode(String userCode) 
    {
        this.userCode = userCode;
    }

    public String getUserCode() 
    {
        return userCode;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    
    public void setPolityname(String polityname) 
    {
        this.polityname = polityname;
    }

    public String getPolityname() 
    {
        return polityname;
    }
    public void setProfname(String profname) 
    {
        this.profname = profname;
    }

    public String getProfname() 
    {
        return profname;
    }
    public void setPsnclname(String psnclname) 
    {
        this.psnclname = psnclname;
    }

    public String getPsnclname() 
    {
        return psnclname;
    }

    public void setEduname(String eduname) 
    {
        this.eduname = eduname;
    }

    public String getEduname() 
    {
        return eduname;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }

	@Override
	public String toString() {
		return "TbHuanPer [orgname=" + orgname + ", age=" + age + ", worktime=" + workage + ", userCode=" + userCode
				+ ", userName=" + userName + ", polityname=" + polityname + ", profname=" + profname
				+ ", psnclname=" + psnclname + ",  eduname="
				+ eduname + ", sex=" + sex + "]";
	}

    
}
