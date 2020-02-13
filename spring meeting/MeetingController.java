package my.spring.springedu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vo.MeetingDAO;
import vo.MeetingVO;


@Controller
public class MeetingController {
	@Autowired
	MeetingDAO dao;
	
	
	@RequestMapping(value = "/meeting", method = RequestMethod.POST)
	public ModelAndView meeting(MeetingVO vo) {
		ModelAndView mav = new ModelAndView();
		int id = vo.getId();
		String name = vo.getName();
		String title = vo.getTitle();
		String date = vo.getMeetingDate();
		
		mav.addObject("id", id);
		mav.addObject("name", name);
		mav.addObject("title", title);
		mav.addObject("date", date);
		boolean result = dao.insert(vo);
		if (result) {
			mav.addObject("msg", name + "님의 글이 성공적으로 입력되었습니다.");

		} else {
			mav.addObject("msg", name + "님의 글이 입력에 실패했습니다.");
		}
		mav.addObject("list", dao.listAll());
		mav.setViewName("meetingView_jstl2");
		return mav;
	}


	@RequestMapping(value = "/meeting", method = RequestMethod.GET)
	public ModelAndView meeting2(MeetingVO vo, String keyword) {
		ModelAndView mav = new ModelAndView();
		int id = vo.getId();
	
		if (id > 0) {
			dao.delete(id);
		} else if (keyword == null) {
			List<MeetingVO> list = dao.listAll();
			//System.out.println(list);
			mav.addObject("list", list);
		} else {
			List<MeetingVO> list = dao.search(keyword);
			//System.out.println(keyword);
			//System.out.println(list);
			if (list.size() == 0) {
				mav.addObject("msg", keyword + "를(을) 담고있는 글이 없습니다.");
			} else {
				mav.addObject("list", list);
			}
		}
		mav.setViewName("meetingView_jstl2");
		return mav;
	}

}