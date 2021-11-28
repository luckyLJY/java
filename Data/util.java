package 数据转换;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class util {
	 public int save(Students student) {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "insert into student values(?,?,?,?,?,?)";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, student.getStudentId());
	            ps.setString(2, student.getStudentName());
	            ps.setString(3, student.getSex());
	            ps.setString(4, student.getPhoneNo());
	            ps.setString(5, student.getAddress());
	            ps.setDate(6, (Date) student.getBirthday());
	            int row = ps.executeUpdate();
	            return row;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, null);
	        }
	        return 0;
	    }
       //删除
	
	    public int delete(String tablename) {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "delete from tablename ";
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

	 //查找所有数据
	    public List<Students> getAll(String tablename) {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        try {
	            conn = JdbcUtils.getConnection();
	            String sql = "select * from tablename";
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();
	            List<Students> studentsList = new ArrayList<>();
	            while(rs.next()) {
	                Students student = new Students();
	                student.setStudentId(rs.getInt("studentId"));
	                student.setStudentName(rs.getString("studentName"));
	                student.setSex(rs.getString("sex"));
	                student.setPhoneNo(rs.getString("phoneNo"));
	                student.setAddress(rs.getString("address"));
	                student.setBirthday(rs.getDate("birthday"));
	                studentsList.add(student);
	            }
	            return studentsList;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            JdbcUtils.close(conn, ps, rs);
	        }
	        return null;
	    }
	    
}


//更新
/* @Override
 public int update(int studentId, Students student,String tablename) {
     Connection conn = null;
     PreparedStatement ps = null;
     try {
         conn = JdbcUtils.getConnection();
         String sql = "update tablename set studentName=?,sex=?,phoneNo=?,address=?,birthday=? where studentId=?";
         ps = conn.prepareStatement(sql);
         ps.setString(1, student.getStudentName());
         ps.setString(2, student.getSex());
         ps.setString(3, student.getPhoneNo());
         ps.setString(4, student.getAddress());
         ps.setDate(5, ((Date) student.getBirthday()));
         ps.setInt(6, studentId);
         int row = ps.executeUpdate();
         return row;
     } catch (Exception e) {
         e.printStackTrace();
     }finally {
         JdbcUtils.close(conn, ps, null);
     }
     return 0;
 }*/
//查找一条数据

// public Students getByStudentId(int studentId,String tablename) {
//     Connection conn = null;
//     PreparedStatement ps = null;
//     ResultSet rs = null;
//     try {
//         conn = JdbcUtils.getConnection();
//         String sql = "select * from students where studentId=?";
//         ps = conn.prepareStatement(sql);
//         ps.setInt(1, studentId);
//         rs = ps.executeQuery();
//         if(rs.next()) {
//             Students student = new Students();
//             student.setStudentId(rs.getInt("studentId"));
//             student.setStudentName(rs.getString("studentName"));
//             student.setSex(rs.getString("sex"));
//             student.setPhoneNo(rs.getString("phoneNo"));
//             student.setAddress(rs.getString("address"));
//             student.setBirthday(rs.getDate("birthday"));
//             return student;
//         }
//     } catch (Exception e) {
//         e.printStackTrace();
//     }finally {
//         JdbcUtils.close(conn, ps, rs);
//     }
//     return null;
// }
