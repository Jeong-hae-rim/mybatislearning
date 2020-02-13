package model.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model.vo.MeetingVO;
import model.vo.VisitorVO;

public class MeetingMybatisDAO implements MeetingDAO {
	final String resource = "resource/mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;

	public MeetingMybatisDAO() {
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			 sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MeetingVO> listAll() {
		System.out.println("Mybatis를 사용 DB 연동-listAll : MeetingMybatisDAO");
		List<MeetingVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		String statement = "resource.MeetingMapper.selectMeeting";
		list = session.selectList(statement);
		System.out.println(session.getClass().getName());
		session.close();
		return list;

	}

	public List<MeetingVO> search(String keyword) {
		System.out.println("Mybatis를 사용 DB 연동-search : MeetingMybatisDAO");
		List<MeetingVO> list = null;
		SqlSession session = sqlSessionFactory.openSession();
		String statement = "resource.MeetingMapper.searchMeeting";
		list = session.selectList(statement, keyword);
		session.close();
		return list;
	}

	public boolean insert(MeetingVO vo) {
		System.out.println("Mybatis를 사용 DB 연동-insert : MeetingMybatisDAO");
		boolean result = false;
		SqlSession session = sqlSessionFactory.openSession(true);
		String statement = "resource.MeetingMapper.insertMeeting";
		if (session.insert(statement, vo) == 1) // 1이면 성공한 것으로 보겠다.
			result = true;
		session.close();
		return result;
	}

	public boolean update(MeetingVO vo) {
		System.out.println("Mybatis를 사용 DB 연동-update : MeetingMybatisDAO");
		boolean result = false;
		SqlSession session = sqlSessionFactory.openSession(true);
		String statement = "resource.MeetingMapper.updateMeeting";
		if (session.update(statement, vo) == 1) // 1이면 성공한 것으로 보겠다.
			result = true;
		session.close();
		return result;		
	}

	@Override
	public boolean delete(int eNo) {
		System.out.println("Mybatis를 사용 DB 연동-delete : MeetingMybatisDAO");
		boolean result = false;
		SqlSession session = sqlSessionFactory.openSession(true);
		String statement = "resource.MeetingMapper.deleteMeeting";
		if(session.delete(statement, eNo) == 1)
			result = true;
		session.close();
		return result;
	}
}
