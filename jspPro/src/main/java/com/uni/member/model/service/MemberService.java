package com.uni.member.model.service;

import static com.uni.common.JDBCTemplate.*;

import java.sql.Connection;

import com.uni.member.model.Dao.MemberDao;
import com.uni.member.model.vo.Member;

public class MemberService {

	public Member loginMember(String userId, String userPwd) {
		Connection conn = getConnection();
		
		Member loginUser = new MemberDao().loginMember(conn, userId, userPwd);
		close(conn);
		
		return loginUser;
	}

	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = new MemberDao().insertMember(conn,m);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member selectMember(String userId) {
		Connection conn = getConnection();
		Member mem = new MemberDao().selectMember(conn, userId);
		
		close(conn);
		return mem;
	}

	public int deleteMember(String userId) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, userId);
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member updateMember(Member m) {
		Connection conn = getConnection();
		Member updateMem = null;
		int result = new MemberDao().updateMember(conn, m);
		if(result>0) {
			commit(conn);
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		}else {
			rollback(conn);
		}
		close(conn);
		
		return updateMem;
	}

	public Member updatePwd(String userId, String userPwd, String newPwd) {
		Connection conn = getConnection();
		Member updateMem = null;
		int result = new MemberDao().updatePwd(conn, userId, userPwd, newPwd);
		System.out.println(result + " Service======= " );
		if(result > 0) {
			commit(conn);
			updateMem = new MemberDao().selectMember(conn, userId);
		}else{
			rollback(conn);
		}
		close(conn);
		return updateMem;
	}



	}


