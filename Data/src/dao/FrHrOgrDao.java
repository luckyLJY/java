package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.FrHrOgr;
import util.JdbcUtils;

public class FrHrOgrDao
{

	 //查找表中所有数据
	    public  List<FrHrOgr> getAll() {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "select * from fr_hr_ogr";
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();
	            List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
	            while(rs.next()) {
	            	FrHrOgr frhrogr = new FrHrOgr();
	            	frhrogr.setCode(rs.getString("code"));
	            	frhrogr.setName(rs.getString("name"));
	            	frhrogr.setFathcode(rs.getString("fathcode"));
	            	frhrogr.setFathname(rs.getString("fathname"));
	            	frhrogr.setGrade(rs.getString("grade"));
	            	frhrogr.setEntitytype(rs.getString("entitytype"));
	            	frhrogr.setGradename(rs.getString("gradename"));
	            	frhrogr.setEntitytypename(rs.getString("entitytypename"));
	            	frhrogrsList.add(frhrogr);
	            }
	            //System.err.println(frhrogrsList.size()+"aa");
	            return frhrogrsList;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, rs);
	        }
	        return null;
	    }
	    
	    
	  //查找表中所有数据为实体组织
	    public List<FrHrOgr> getAll1() {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "select * from fr_hr_ogr where ENTITYTYPE !='~'and ENTITYTYPE is not null and fathcode='1'";
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();
	            List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
	            while(rs.next()) {
	            	FrHrOgr frhrogr = new FrHrOgr();
	            	frhrogr.setCode(rs.getString("code"));
	            	frhrogr.setName(rs.getString("name"));
	            	frhrogr.setFathcode(rs.getString("fathcode"));
	            	frhrogr.setFathname(rs.getString("fathname"));
	            	frhrogr.setGrade(rs.getString("grade"));
	            	frhrogr.setEntitytype(rs.getString("entitytype"));
	            	frhrogr.setGradename(rs.getString("gradename"));
	            	frhrogr.setEntitytypename(rs.getString("entitytypename"));
	            	frhrogrsList.add(frhrogr);
	            }
	            return frhrogrsList;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, rs);
	        }
	        return null;
	    }
	    
	  //查找一类数据

	    public List<FrHrOgr> getByFrHrOrgCode(String code) {
	      Connection conn = null;
	      PreparedStatement ps = null;
	      ResultSet rs = null;
	      try {
	          conn = JdbcUtils.getConnection();
	          String sql = "select * from fr_hr_ogr where fathcode=?";
	          ps = conn.prepareStatement(sql);
	          ps.setString(1, code);
	          rs = ps.executeQuery();
	          List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
	          while(rs.next()) {
	        	  FrHrOgr frhrogr = new FrHrOgr();
	            	frhrogr.setCode(rs.getString("code"));
	            	frhrogr.setName(rs.getString("name"));
	            	frhrogr.setFathcode(rs.getString("fathcode"));
	            	frhrogr.setFathname(rs.getString("fathname"));
	            	frhrogr.setGrade(rs.getString("grade"));
	            	frhrogr.setEntitytype(rs.getString("entitytype"));
	            	frhrogr.setGradename(rs.getString("gradename"));
	            	frhrogr.setEntitytypename(rs.getString("entitytypename"));
	            	frhrogrsList.add(frhrogr);
	          }
	          return frhrogrsList;
	      } catch (Exception e) {
	          e.printStackTrace();
	      }finally {
	          JdbcUtils.close(conn, ps, rs);
	      }
	      return null;
	    }
	    
	    
	  //查找一类数据

	    public FrHrOgr getByFrHrOrgCode1(String code) {
	      Connection conn = null;
	      PreparedStatement ps = null;
	      ResultSet rs = null;
	      try {
	          conn = JdbcUtils.getConnection();
	          String sql = "select * from fr_hr_ogr where code=?";
	          ps = conn.prepareStatement(sql);
	          ps.setString(1, code);
	          rs = ps.executeQuery();
	         
	          if(rs.next()) {
	        	  FrHrOgr frhrogr = new FrHrOgr();
	            	frhrogr.setCode(rs.getString("code"));
	            	frhrogr.setName(rs.getString("name"));
	            	frhrogr.setFathcode(rs.getString("fathcode"));
	            	frhrogr.setFathname(rs.getString("fathname"));
	            	frhrogr.setGrade(rs.getString("grade"));
	            	frhrogr.setEntitytype(rs.getString("entitytype"));
	            	frhrogr.setGradename(rs.getString("gradename"));
	            	frhrogr.setEntitytypename(rs.getString("entitytypename"));
	            	return frhrogr;
	          }
	       
	      } catch (Exception e) {
	          e.printStackTrace();
	      }finally {
	          JdbcUtils.close(conn, ps, rs);
	      }
	      return null;
	    }

	    //查询表中需组织
		public List<FrHrOgr> getAll2() {
			 Connection conn = null;
		        PreparedStatement ps = null;
		        ResultSet rs = null;
		        try {
		            conn = JdbcUtils.getConnection();
		            String sql = "select * from fr_hr_ogr where ENTITYTYPENAME ='虚组织'and ENTITYTYPE is not null and fathcode='1'";
		            ps = conn.prepareStatement(sql);
		            rs = ps.executeQuery();
		            List<FrHrOgr> frhrogrsList = new ArrayList<FrHrOgr>();
		            while(rs.next()) {
		            	FrHrOgr frhrogr = new FrHrOgr();
		            	frhrogr.setCode(rs.getString("code"));
		            	frhrogr.setName(rs.getString("name"));
		            	frhrogr.setFathcode(rs.getString("fathcode"));
		            	frhrogr.setFathname(rs.getString("fathname"));
		            	frhrogr.setGrade(rs.getString("grade"));
		            	frhrogr.setEntitytype(rs.getString("entitytype"));
		            	frhrogr.setGradename(rs.getString("gradename"));
		            	frhrogr.setEntitytypename(rs.getString("entitytypename"));
		            	frhrogrsList.add(frhrogr);
		            }
		            return frhrogrsList;
		        } catch (Exception e) {
		            e.printStackTrace();
		        }finally {
		            JdbcUtils.close(conn, ps, rs);
		        }
		        return null;
		}


}
