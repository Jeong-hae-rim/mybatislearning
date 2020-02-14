package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.NewsVO;

@Repository
public class NewsDAO {
	@Autowired
	SqlSession session = null;

	

	public List<NewsVO> listAll() {
		System.out.println("Mybatis를 사용 DB 연동-listAll : NeswMybatisDAO");
		List<NewsVO> list = null;
		String statement = "resource.NewsMapper.selectNews";
		list = session.selectList(statement);
		return list;

	}

	public NewsVO listOne(int id) {
		System.out.println("Mybatis를 사용 DB 연동-listOne : NeswMybatisDAO");
		NewsVO vo = null;
		String statement = "resource.NewsMapper.selectNewsOne";
		vo = session.selectOne(statement, id);
		
		boolean result = false;
		if(session.update("resource.NewsMapper.updateCount", id) ==1) {
			result = true;
		}
		return vo;

	}
	
	public List<NewsVO> listWriter(String writer) {
		System.out.println("Mybatis를 사용 DB 연동-listWriter : NeswMybatisDAO");
		List<NewsVO> list = null;
		String statement = "resource.NewsMapper.selectNewsWriter";
		list = session.selectList(statement, writer);
		return list;
	}
		

	public List<NewsVO> search(String key, String searchType){
		System.out.println("Mybatis를 사용 DB 연동-search : NeswMybatisDAO");
		List<NewsVO> list = null;
		String statement = "resource.NewsMapper.searchNews";
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		map.put("searchType", searchType);
		list = session.selectList(statement, map);
		return list;
	}

	public boolean insert(NewsVO vo) {
		System.out.println("Mybatis를 사용 DB 연동-insert : NewsMybatisDAO");
		boolean result = false;
		String statement = "resource.NewsMapper.insertNews";
		if (session.insert(statement, vo) == 1)
			result = true;
		return result;

	}

	public boolean update(NewsVO vo) {
		System.out.println("Mybatis를 사용 DB 연동-update : NewsMybatisDAO");
		boolean result = false;
		String statement = "resource.NewsMapper.updateNews";
		if (session.update(statement, vo) == 1)
			result = true;
		return result;		

	}


	
	public boolean delete(int id) {
		System.out.println("Mybatis를 사용 DB 연동-delete : NewsMybatisDAO");
		boolean result = false;
		String statement = "resource.NewsMapper.deleteNews";
		if(session.delete(statement, id) == 1)
			result = true;
		return result;
	}
	
	
	
	}






