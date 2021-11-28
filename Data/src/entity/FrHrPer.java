package entity;


public class FrHrPer 
{
 
    private String orgname;
    private String userCode;
    private String userName;
    private String id;
    private String polityname;
    private String profname;
    private String psnclname;
    private String postname;
    private String deptname;
    private String eduname;
    private String begindate;
    private Long allnum;
    private String joinworkdate;
    private Long sex;

    public void setOrgname(String orgname) 
    {
        this.orgname = orgname;
    }

    public String getOrgname() 
    {
        return orgname;
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
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
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
    public void setPostname(String postname) 
    {
        this.postname = postname;
    }

    public String getPostname() 
    {
        return postname;
    }
    public void setDeptname(String deptname) 
    {
        this.deptname = deptname;
    }

    public String getDeptname() 
    {
        return deptname;
    }
    public void setEduname(String eduname) 
    {
        this.eduname = eduname;
    }

    public String getEduname() 
    {
        return eduname;
    }
    public void setBegindate(String begindate) 
    {
        this.begindate = begindate;
    }

    public String getBegindate() 
    {
        return begindate;
    }
    public void setAllnum(Long allnum) 
    {
        this.allnum = allnum;
    }

    public Long getAllnum() 
    {
        return allnum;
    }
    public void setJoinworkdate(String joinworkdate) 
    {
        this.joinworkdate = joinworkdate;
    }

    public String getJoinworkdate() 
    {
        return joinworkdate;
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
		return "TbHuanPer [orgname=" + orgname + ",  userCode=" + userCode
				+ ", userName=" + userName + ", id=" + id + ", polityname=" + polityname + ", profname=" + profname
				+ ", psnclname=" + psnclname + ", postname=" + postname + ", deptname=" + deptname + ", eduname="
				+ eduname + ", begindate=" + begindate + ", allnum=" + allnum + ", joinworkdate=" + joinworkdate
				+ ", sex=" + sex + "]";
	}

    
}
