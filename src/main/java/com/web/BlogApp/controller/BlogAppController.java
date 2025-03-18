package com.web.BlogApp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;
import com.web.BlogApp.service.BlogAppService;

@Controller
//@RestController //não funciona para returno de string
public class BlogAppController {

	
	@Autowired
	BlogAppService blogappservice;
	
//	@Autowired
//	private PostComentarioRepository pr; 
	
	
	// LISTA TODOS OS POSTS
	@GetMapping(value = "/posts") // posso utilizar assim também chamando pelo navegador
//	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public ModelAndView  getPosts() {
		 ModelAndView mv = new ModelAndView("posts");
		 List<PostModel> post = blogappservice.findAll();
		 mv.addObject("posts", post);
		 return mv;
	}
	
}
