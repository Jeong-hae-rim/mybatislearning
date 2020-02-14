package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import vo.NewsVO;

@Repository
public class NewsDAO {

	private Connection connectDB() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "jdbctest", "jdbctest");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void close(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			} else if (stmt != null) {
				stmt.close();
			} else if (conn != null) {
				conn.close();
			} else {
				pstmt.close();
			}
		} catch (SQLException e) {

		}
	}

	public List<NewsVO> listAll() {
		List<NewsVO> list = new ArrayList<>();

		String sql = "select id, writer, title, content, to_char(writedate, 'yyyy-mm-dd') writedate, cnt from news";
		try (Connection conn = connectDB();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			NewsVO vo;
			while (rs.next()) {
				vo = new NewsVO();
				vo.setId(rs.getInt(1));
				vo.setWriter(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setWritedate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				list.add(vo);
			}
			close(conn, stmt, null, rs);
		} catch (Exception e) {
			System.err.println("오류 발생 : " + e);
		}
		return list;

	}

	public NewsVO listOne(int id) {
		String SQL = "select * from news where id = ?";
		ResultSet rs = null;
		NewsVO vo = null;
		PreparedStatement pstmt = null;
		try (Connection conn = connectDB();) {

			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new NewsVO();
				vo.setId(rs.getInt(1));
				vo.setWriter(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setWritedate(rs.getString(5));
				vo.setCnt(rs.getInt(6) + 1);

				pstmt = conn.prepareStatement("update news set cnt = ? where id = ?");
				pstmt.setInt(1, vo.getCnt());
				pstmt.setInt(2, id);
				pstmt.executeQuery();

			}
			close(conn, null, pstmt, rs);
		} catch (Exception e) {
			System.err.println("오류 발생 : " + e);
		}
		return vo;

	}
	
	public List<NewsVO> listWriter(String writer) {
		List<NewsVO> list = new ArrayList<>();
		try (Connection conn = connectDB()) {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select id, writer, title, content, to_char(writedate, 'yyyy-mm-dd') writedate, cnt "
								+ "from news where writer like '%" + writer + "%'"); {
			NewsVO vo;
			while (rs.next()) {
				vo = new NewsVO();
				vo.setId(rs.getInt(1));
				vo.setWriter(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setWritedate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				list.add(vo);
			}
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
		

	public List<NewsVO> search(String key, String searchType){
		List<NewsVO> list = new ArrayList<>();
		if (searchType.contentEquals("listwriter")) {
			list = listWriter(key);
		} else {
		try (Connection conn = connectDB()) {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"select id, writer, title, content, to_char(writedate, 'yyyy-mm-dd') writedate, cnt "
								+ "from news where "+searchType+" like '%" + key + "%'"); {
			NewsVO vo;
			while (rs.next()) {
				vo = new NewsVO();
				vo.setId(rs.getInt(1));
				vo.setWriter(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setWritedate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				list.add(vo);
			}
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		return list;
	}

	public boolean insert(NewsVO vo) {
		boolean result = true;
		try (Connection conn = connectDB();
				PreparedStatement pstmt = conn
						.prepareStatement("insert into news values(news_seq.nextval, ?, ?, ?, sysdate, 0)");) {
			pstmt.setString(1, vo.getWriter());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.executeUpdate();
			close(conn, null, pstmt, null);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;

	}

	public boolean update(NewsVO vo) {
		boolean result = true;
		try (Connection conn = connectDB();
				PreparedStatement pstmt = conn.prepareStatement("update news set " + "writer = ?, " + "title = ?, "
						+ "content = ?, " + "cnt = ? " + "where id = ?");) {
			pstmt.setString(1, vo.getWriter());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getCnt());
			pstmt.setInt(5, vo.getId());
			pstmt.executeUpdate();
			close(conn, null, pstmt, null);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;

	}

	public boolean delete(int id) {
		boolean result = true;
		try (Connection conn = connectDB();
				PreparedStatement pstmt = conn.prepareStatement("delete from news where id=?");) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			close(conn, null, pstmt, null);
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;

	}





}
