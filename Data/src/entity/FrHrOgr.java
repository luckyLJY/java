package entity;

import java.util.List;

public class FrHrOgr
{
  
    private String code;


    private String name;

    private String fathcode;

    private String fathname;

    private String grade;

    private String entitytype;

 
    private String gradename;


    private String entitytypename;
    
    private List<FrHrOgr> children;

    
    
    public List<FrHrOgr> getChildren() {
		return children;
	}

	public void setChildren(List<FrHrOgr> children) {
		this.children = children;
	}

	public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setFathcode(String fathcode) 
    {
        this.fathcode = fathcode;
    }

    public String getFathcode() 
    {
        return fathcode;
    }
    public void setFathname(String fathname) 
    {
        this.fathname = fathname;
    }

    public String getFathname() 
    {
        return fathname;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }
    public void setEntitytype(String entitytype) 
    {
        this.entitytype = entitytype;
    }

    public String getEntitytype() 
    {
        return entitytype;
    }
    public void setGradename(String gradename) 
    {
        this.gradename = gradename;
    }

    public String getGradename() 
    {
        return gradename;
    }
    public void setEntitytypename(String entitytypename) 
    {
        this.entitytypename = entitytypename;
    }

    public String getEntitytypename() 
    {
        return entitytypename;
    }

	@Override
	public String toString() {
		return "FrHrOgr [code=" + code + ", name=" + name + ", fathcode=" + fathcode + ", fathname=" + fathname
				+ ", grade=" + grade + ", entitytype=" + entitytype + ", gradename=" + gradename + ", entitytypename="
				+ entitytypename + ", children=" + children + "]";
	}

   
}
