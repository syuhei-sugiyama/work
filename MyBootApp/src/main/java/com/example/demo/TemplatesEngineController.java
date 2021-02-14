package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// @Controllerアノテーションにより、webページを扱う際のコントローラークラスであることを示す
@Controller
public class TemplatesEngineController {
	@RequestMapping("/tec")
	public ModelAndView ognl1(ModelAndView mav) {
		mav.setViewName("index2");
		return mav;
	}
}
