package com.web.BlogApp.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.BlogApp.model.PostComentarioModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;
import com.web.BlogApp.service.BlogAppService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        List<PostModel> posts = blogappservice.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    // LISTA TODOS OS POSTS
    @GetMapping(value = "/posts/{id}")
    public ModelAndView getPostDetails(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("postsDetails");
        Optional<PostModel> blogappModelOptional = blogappservice.findById(id);
        if (blogappModelOptional.isPresent()) {
            PostModel posts = blogappModelOptional.get();
            mv.addObject("posts", posts);
        } else {
            attributes.addFlashAttribute("mensagem", "Post não encontrado!");
        }
        return mv;
    }

    //RETORNA O FORMS PARA ADICIONAR UM POST
    @GetMapping(value = "/newpost")
    public String getPostForm() {

        return "newpostForm";
    }

    //SALVAR O POST
    @PostMapping(value = "newpost")
    public String savePost(@Valid PostModel post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/newpost";

        }
        post.setData(LocalDate.now());
        blogappservice.save(post);
        return "redirect:/posts";
    }

    // Adicionar método para editar um post
    @GetMapping(value = "/editpost/{id}")
    public ModelAndView getEditPostForm(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("editpostForm");
        Optional<PostModel> post = blogappservice.findById(id);
        if (post.isPresent()) {
            mv.addObject("post", post.get());
        } else {
            attributes.addFlashAttribute("mensagem", "Post não encontrado!");
        }
        return mv;
    }


    @PostMapping(value = "/editpost")
    public String updatePost(@Valid PostModel post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/editpost/" + post.getId();
        }
        blogappservice.save(post);
        return "redirect:/posts";
    }

    // Deletar um post
    @GetMapping(value = "/deletepost/{id}")
    public String deletePost(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        Optional<PostModel> post = blogappservice.findById(id);
        if (post.isPresent()) {
            blogappservice.delete(post.get());
            attributes.addFlashAttribute("mensagem", "Post deletado com sucesso!");
        }
        return "redirect:/posts";
    }

    // Método para adicionar comentário
        @PostMapping(value = "/addcomment")
    public String addComment(@Valid PostComentarioModel comment, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/posts/" + comment.getPostModel().getId();
        }
        blogappservice.saveComentario(comment);
        return "redirect:/posts/" + comment.getPostModel().getId();
    }


    // Adicionar método para editar um comentário
    @GetMapping(value = "/editcomment/{id}")
    public ModelAndView getEditCommentForm(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("editcommentForm");
        Optional<PostComentarioModel> comment = blogappservice.findIdComentario(id);
        if (comment.isPresent()) {
            mv.addObject("comment", comment.get());
        } else {
            attributes.addFlashAttribute("mensagem", "Comentário não encontrado!");
        }
        return mv;
    }

    @PostMapping(value = "/editcomment")
    public String updateComment(@Valid PostComentarioModel comment, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/editcomment/" + comment.getId();
        }
        blogappservice.saveComentario(comment);
        return "redirect:/posts/" + comment.getPostModel().getId();
    }

    // Adicionar método para deletar um comentário
    @GetMapping(value = "/deletecomment/{id}")
    public String deleteComment(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        Optional<PostComentarioModel> comment = blogappservice.findIdComentario(id);
        if (comment.isPresent()) {
            blogappservice.deleteComentarios(List.of(comment.get()));
            attributes.addFlashAttribute("mensagem", "Comentário deletado com sucesso!");
            return "redirect:/posts/" + comment.get().getPostModel().getId();
        } else {
            attributes.addFlashAttribute("mensagem", "Comentário não encontrado!");
            return "redirect:/posts";
        }
    }

}
