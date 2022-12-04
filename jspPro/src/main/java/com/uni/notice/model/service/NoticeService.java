package com.uni.notice.model.service;

import static com.uni.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.uni.notice.model.dao.NoticeDao;
import com.uni.notice.model.vo.Notice;

public class NoticeService {

	public ArrayList<Notice> selectList() {
		Connection conn = getConnection();//connection 연결
		
		ArrayList<Notice> list = new NoticeDao().selectList(conn);//모든것을 조회하기때문에 넘겨줄꺼는 없고 Connection 정보만 넘겨주고 모둑다 조회하면된다.
		close(conn); // 완려한후 close
		return list;
	}

	public Notice selectNotice(int nno) {
		Connection conn = getConnection();//connection 연결
		
		int result = new NoticeDao().increaseCount(conn, nno);
		
		Notice n = null; 
		if(result>0) {
			commit(conn);
			n = new NoticeDao().selectNotice(conn, nno);
		}else {
			rollback(conn);
		}
		close(conn);
		return n;
	}

	public int deleteNotice(int nno) {
		Connection conn = getConnection();//connection 연결
		
		int result = new NoticeDao().deleteNotice(conn, nno);
		
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int insertNotice( Notice n) {
		Connection conn = getConnection();//connection 연결
		
		int result = new NoticeDao().insertNotice(conn, n);
		
		
		if(result>0) {
			commit(conn);	
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int updateNotice(Notice notice) {
		Connection conn = getConnection();//connection 연결
		int result = new NoticeDao().updateNotice(conn, notice);//NoticeDao 에 보낸다.
		
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;//result 값을 반환해준다.
	}



	

	
}
