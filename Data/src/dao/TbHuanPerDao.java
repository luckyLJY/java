package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.FrHrOgr;
import entity.TbHuanPer;
import util.JdbcUtils;

public class TbHuanPerDao 
{
	 //查找表中所有数据
    public List<TbHuanPer> getAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from tb_huan_per";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<TbHuanPer> tbhuanpersList = new ArrayList<TbHuanPer>();
            while(rs.next()) {
            	TbHuanPer tbhuanper = new TbHuanPer();
            	tbhuanper.setOrgname(rs.getString("orgname"));
            	tbhuanper.setAge(rs.getLong("age"));
            	tbhuanper.setWorktime(rs.getLong("workage"));
            	tbhuanper.setUserCode(rs.getString("userCode"));
            	tbhuanper.setUserName(rs.getString("userName"));
              
                tbhuanper.setPolityname(rs.getString("polityname"));
                tbhuanper.setProfname(rs.getString("profname"));
                tbhuanper.setPsnclname(rs.getString("psnclname"));
                 tbhuanper.setEduname(rs.getString("eduname"));
                 tbhuanper.setSex(rs.getLong("sex"));
                
                
            	tbhuanpersList.add(tbhuanper);
            }
            //System.out.println(tbhuanpersList.size());
            return tbhuanpersList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn, ps, rs);
        }
        return null;
    }
  //查找一类数据

	public List<TbHuanPer> getByTbhuanperName(FrHrOgr name) {
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
     // System.out.println(name);
      try {
          conn = JdbcUtils.getConnection();
          String sql = "select * from tb_huan_per where ORGNAME=?";
          ps = conn.prepareStatement(sql);
          ps.setString(1, name.getName());
          
          //System.out.println(sql);
          rs = ps.executeQuery();
          
          List<TbHuanPer> tbhuanpersList = new ArrayList<TbHuanPer>();
          while(rs.next()) {
        	  TbHuanPer tbhuanper = new TbHuanPer();
          	tbhuanper.setOrgname(rs.getString("orgname"));
          	tbhuanper.setAge(rs.getLong("age"));
          	tbhuanper.setWorktime(rs.getLong("workage"));
          	tbhuanper.setUserCode(rs.getString("user_code"));
          	tbhuanper.setUserName(rs.getString("user_name"));
              tbhuanper.setPolityname(rs.getString("polityname"));
              tbhuanper.setProfname(rs.getString("profname"));
              tbhuanper.setPsnclname(rs.getString("psnclname"));
              tbhuanper.setEduname(rs.getString("eduname"));
              tbhuanper.setSex(rs.getLong("sex"));
             // System.err.println(tbhuanper.getOrgname());
              tbhuanpersList.add(tbhuanper);
              
            //System.out.println('A');
          }
         // System.out.println(tbhuanpersList.size());
          return tbhuanpersList;
      } catch (Exception e) {
          e.printStackTrace();
          //System.out.println('a');
      }finally {
          JdbcUtils.close(conn, ps, rs);
      }
      return null;
    }


}
