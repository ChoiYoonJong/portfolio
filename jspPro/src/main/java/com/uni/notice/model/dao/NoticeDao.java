package com.uni.notice.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import static com.uni.common.JDBCTemplate.*;

import com.uni.notice.model.vo.Notice;

public class NoticeDao {
	
	private Properties prop = new Properties();

	public NoticeDao() {
		String fileName = NoticeDao.class.getResource("/sql/notice/notice-query.properties").getPath();//notice query 갖고오기
		System.out.println("fileName   " + fileName);
		try {
			prop.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public ArrayList<Notice> selectList(Connection conn) {
		
		ArrayList<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectList");

		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Notice(rset.getInt("NOTICE_NO"),
									rset.getString("NOTICE_TITLE"),
									rset.getString("USER_ID"),
									rset.getInt("COUNT"),
									rset.getDate("CREATE_DATE")));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int increaseCount(Connection conn, int nno) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public Notice selectNotice(Connection conn, int nno) {
		Notice n = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;//조회하는거이기때문에 필요함
		String sql = prop.getProperty("selectNotice");//sql 주소 받아오기
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,nno);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				n = new Notice(rset.getInt("NOTICE_NO"),
						rset.getString("NOTICE_TITLE"),
						rset.getString("NOTICE_CONTENT"),
						rset.getString("USER_ID"),
						rset.getInt("COUNT"),
						rset.getDate("CREATE_DATE"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return n;
	}

	public int deleteNotice(Connection conn, int nno) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertNotice(Connection conn, Notice n) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNotice");
		
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter()));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateNotice(Connection conn, Notice notice) {
		int result = 0; //int 값 설정
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateNotice");//sql 주소 설정
		
		try {
			pstmt = conn.prepareStatement(sql); // sql
			pstmt.setString(1, notice.getNoticeTitle());// 형식에 맞춰서 자료를 받아온다.
			pstmt.setString(2, notice.getNoticeContent());
			pstmt.setInt(3, notice.getNoticeNo());
			
			result = pstmt.executeUpdate();//받아오는거이기때문에 executeUpdate를 작성해서 쓴다. 업데이트나 인서트, 딜리리트 수행결과로 Int 타입의 값을 반환
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;//result 값을 return 해준다.
	}

	

	


}
