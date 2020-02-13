package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.MeetingJDBCDAO;
import model.dao.MeetingMybatisDAO;
import model.dao.VisitorDAO;
import model.vo.MeetingVO;
import model.vo.VisitorVO;

@WebServlet("/meetingmybatis")
public class MeetingMybatisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String keyword = request.getParameter("keyword");
		String id = request.getParameter("id");

		MeetingMybatisDAO dao = new MeetingMybatisDAO();
		if (id != null) {
				dao.delete(Integer.parseInt(id));
		}
		else if (keyword == null) {
			List<MeetingVO> list = dao.listAll();
			for (MeetingVO vo : list) {
				System.out.println(vo.getTitle());
				System.out.println(vo.getName());
				System.out.println(vo.getMeetingDate());
			}
			request.setAttribute("list", list);
		} else {
			List<MeetingVO> list = dao.search(keyword);
			if (list.size() == 0) {
				request.setAttribute("msg", keyword + "를 담고있는 글이 없습니다.(mybatis.ver)");
			} else {
				request.setAttribute("list", list);
			}
		}
		request.getRequestDispatcher("/jspexam/meetingView_jstl2.jsp").forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");		
		String name = request.getParameter("name");
		String title = request.getParameter("title");
		String date = request.getParameter("meetingDate");
		System.out.println(name);
		System.out.println(title);
		System.out.println(date);
		MeetingMybatisDAO dao = new MeetingMybatisDAO();
		MeetingVO vo = new MeetingVO();
		vo.setName(name);
		vo.setTitle(title);
		vo.setMeetingDate(date);
		boolean result = dao.insert(vo);
		if(result) {
			request.setAttribute("msg", name+"님의 글이 성공적으로 입력되었습니다.(mybatis.ver)");
			
		} else {
			request.setAttribute("msg", name+"님의 글이 입력에 실패했습니다.(mybatis.ver)");
		}
		List<MeetingVO> list = dao.listAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/jspexam/meetingView_jstl2.jsp")
		           .forward(request, response);

	}

}

