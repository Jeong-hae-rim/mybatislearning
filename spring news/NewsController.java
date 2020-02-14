package my.spring.springnews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.NewsDAO;
import vo.NewsVO;


@Controller
public class NewsController{
	@Autowired
	NewsDAO dao;
	

	@RequestMapping(value = "/newsmain", method = RequestMethod.GET)
	public ModelAndView news1(NewsVO vo, String action, String key, String searchtype) {
		ModelAndView mav = new ModelAndView();
		List<NewsVO> list = null;
		int id = vo.getId();
		String writer = vo.getWriter();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		/*
		 * System.out.println(searchtype); System.out.println(action);
		 * System.out.println(key); System.out.println(vo);
		 */
		if (action != null) {
			if (action.equals("read")) {
                NewsVO my = dao.listOne(id);
				
				mav.addObject("writer", writer);
				mav.addObject("title" , title);
				mav.addObject("content", content);

				//System.out.println(my);  
				//mav.addObject("read", list);
				mav.addObject("read", my);
				
			} 
		}
		
		if (key == null) {
		    list = dao.listAll();
		    mav.addObject("list", list);
		} else if (key != null) {
			mav.addObject("searchtype", searchtype);
			mav.addObject("list", list);
			if(searchtype.equals("writer")) { 
				list = dao.listWriter(key);
				mav.addObject("list", list);
			}else if(searchtype.equals("title")) {
				list = dao.search(key,searchtype);
				mav.addObject("list", list);
			} else if (searchtype.equals("content")) {
				list = dao.search(key,searchtype);
				mav.addObject("list", list);
			}
			
			if (list.size() == 0) {
				list = dao.listAll();
				mav.addObject("list", list);
				mav.addObject("msg", key + "를 담고있는 글이 없습니다.");
			} else {
				mav.addObject("list", list);
			}
			
		}
		
		mav.setViewName("news");
		return mav;

	}
	
	@RequestMapping(value = "/newsdelete", method = RequestMethod.GET)
	public ModelAndView newsdelete(NewsVO vo, String action) {
		ModelAndView mav = new ModelAndView();
		List<NewsVO> list = null;
		int id = vo.getId();
		String writer = vo.getWriter();
		
		mav.addObject("writer", writer);
		
		boolean result = dao.delete(id);
		if (result) {
			mav.addObject("msg", writer + "님의 글이 성공적으로 삭제되었습니다.");
			mav.addObject("list", list);

		} else {
			mav.addObject("msg", writer + "님의 글이 삭제에 실패했습니다.");
			mav.addObject("list", list);
		}
		mav.addObject("list", dao.listAll());
		mav.setViewName("news");
		return mav;
	}
	
	@RequestMapping(value = "/newsinsert", method = RequestMethod.POST)
	public ModelAndView newsinsert(NewsVO vo, String action) {
		ModelAndView mav = new ModelAndView();
		List<NewsVO> list = null;
		int id = vo.getId();
		String writer = vo.getWriter();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		mav.addObject("writer", writer);
		mav.addObject("title", title);
		mav.addObject("content", content);
		
		System.out.println(writer);
		System.out.println(title);
		System.out.println(content);
		
		boolean result = dao.insert(vo);
		if (result) {
			mav.addObject("msg", writer + "님의 글이 성공적으로 입력되었습니다.");
			mav.addObject("list", list);

		} else {
			mav.addObject("msg", writer + "님의 글이 입력에 실패했습니다.");
			mav.addObject("list", list);
		}
		mav.addObject("list", dao.listAll());
		mav.setViewName("news");
		return mav;
	}
		@RequestMapping(value = "/newsupdate", method = RequestMethod.POST)
		public ModelAndView newsupdate(NewsVO vo, String action) {
			ModelAndView mav = new ModelAndView();
			List<NewsVO> list = null;
			int id = vo.getId();
			String writer = vo.getWriter();
			String title = vo.getTitle();
			String content = vo.getContent();
			
			mav.addObject("writer", writer);
			mav.addObject("title", title);
			mav.addObject("content", content);
			
		    boolean result = dao.update(vo);
		    if (result) {
			    mav.addObject("msg", writer + "님의 글이 성공적으로 수정되었습니다.");
			    mav.addObject("list", list);

		    } else {
			    mav.addObject("msg", writer + "님의 글이 수정에 실패했습니다.");
			    mav.addObject("list", list);
		    }
		    mav.addObject("list", dao.listAll());
			mav.setViewName("news");
			return mav;
	}

}

