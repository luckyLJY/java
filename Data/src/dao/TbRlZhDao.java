package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.TbRlZh;
import util.JdbcUtils;


public class TbRlZhDao {
	
	//存储总人数记录
	 public int savePerSum(TbRlZh tbrlzh) {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "insert into tb_rl_zh (code,name,persum,fathcode,gradename) values(?,?,?,?,?)";
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, tbrlzh.getCode());
	            ps.setString(2, tbrlzh.getName());
	            ps.setInt(3, tbrlzh.getPersum());
	            ps.setString(4, tbrlzh.getFathcode());
	            ps.setString(5, tbrlzh.getGradename());
	            
	            int row = ps.executeUpdate();
	            return row;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, null);
	        }
	        return 0;
	    }
	 
	//存储记录
	 public int save(TbRlZh tbrlzh) {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "insert into tb_rl_zh values(?,?,?,?,?,?,?,?)";
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, tbrlzh.getCode());
	            ps.setString(2, tbrlzh.getName());
	            ps.setInt(3, tbrlzh.getJssum());
	            ps.setInt(4, tbrlzh.getPersum());
	            ps.setInt(5, tbrlzh.getAgesum());
	            ps.setInt(6, tbrlzh.getWorksum());
	            ps.setString(7, tbrlzh.getFathcode());
	            ps.setString(8, tbrlzh.getGradename());
	            int row = ps.executeUpdate();
	            return row;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, null);
	        }
	        return 0;
	    }
	 
    //删除表中记录
	    public int delete() {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "delete from tb_rl_zh ";
	            ps = conn.prepareStatement(sql);
	  
	            int row = ps.executeUpdate();
	            return row;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, null);
	        }
	        return 0;
	    }

	 //查找表中所有数据
	    public  List<TbRlZh> getAll() {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "select * from tb_rl_zh";
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();
	            List<TbRlZh> tbrlzhsList = new ArrayList<TbRlZh>();
	            while(rs.next()) {
	            	TbRlZh tbrlzh = new TbRlZh();
	            	 ps.setString(1, tbrlzh.getCode());
	 	            ps.setString(2, tbrlzh.getName());
	 	            ps.setInt(3, tbrlzh.getJssum());
	 	            ps.setInt(4, tbrlzh.getPersum());
	 	            ps.setInt(5, tbrlzh.getAgesum());
	 	            ps.setInt(6, tbrlzh.getWorksum());
	 	            ps.setString(7, tbrlzh.getFathcode());
	 	            ps.setString(8, tbrlzh.getGradename());
	            	tbrlzhsList.add(tbrlzh);
	            }
	            return tbrlzhsList;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, rs);
	        }
	        return null;
	    }



//更新
public int update(String  code, TbRlZh tbrlzh) {
  Connection conn = null;
  PreparedStatement ps = null;
  try {
      conn = JdbcUtils.getConnection();
      String sql = "update tb_rl_zh set name=?,jisum=?,persum=?,agesum=?,worksum=? where code=?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, tbrlzh.getName());
      ps.setInt(2, tbrlzh.getJssum());
      ps.setInt(3, tbrlzh.getPersum());
      ps.setInt(4, tbrlzh.getAgesum());
      ps.setInt(5, tbrlzh.getWorksum());
      ps.setString(6, tbrlzh.getCode());
      int row = ps.executeUpdate();
      return row;
  } catch (Exception e) {
      e.printStackTrace();
  }finally {
      JdbcUtils.close(conn, ps, null);
  }
  return 0;
}
//查找一条数据

public TbRlZh getByStudentId(String code) {
  Connection conn = null;
  PreparedStatement ps = null;
  ResultSet rs = null;
  try {
      conn = JdbcUtils.getConnection();
      String sql = "select * from tb_rl_zh where code=?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, code);
      rs = ps.executeQuery();
      if(rs.next()) {
    	  TbRlZh tbrlzh = new TbRlZh();
      	tbrlzh.setCode(rs.getString("code"));
      	tbrlzh.setName(rs.getString("name"));
      	tbrlzh.setJssum(rs.getInt("jssum"));
      	tbrlzh.setPersum(rs.getInt("persum"));
      	tbrlzh.setAgesum(rs.getInt("agesum"));
      	tbrlzh.setWorksum(rs.getInt("worksum"));
      	return tbrlzh;
      }
  } catch (Exception e) {
      e.printStackTrace();
  }finally {
      JdbcUtils.close(conn, ps, rs);
  }
  return null;
}

}
