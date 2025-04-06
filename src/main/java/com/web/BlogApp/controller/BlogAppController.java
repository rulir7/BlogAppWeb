package com.web.BlogApp.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;
import com.web.BlogApp.service.BlogAppService;

import jakarta.validation.Valid;

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

    // LISTA UM POST E SEUS COMENTÁRIOS
    @GetMapping(value = "/posts/{id}")
    public ModelAndView getPostDetails(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        ModelAndView mv = new ModelAndView("postsDetails");
        Optional<PostModel> blogappModelOptional = blogappservice.findById(id);
        if (blogappModelOptional.isPresent()) {
            PostModel post = blogappModelOptional.get();
            mv.addObject("posts", post);
            
            // Busca e adiciona os comentários do post
            Iterable<PostComentarioModel> comentarios = blogappservice.findComentariosByPost(post);
            mv.addObject("comentarios", comentarios);
        } else {
            attributes.addFlashAttribute("mensagem", "Post não encontrado!");
            return new ModelAndView("redirect:/posts");
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
            return new ModelAndView("redirect:/posts");
        }
        return mv;
    }


    @PostMapping(value = "/editpost")
    public String updatePost(@Valid PostModel post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/editpost/" + post.getId();
        }
        // Manter a data original do post
        Optional<PostModel> existingPost = blogappservice.findById(post.getId());
        if (existingPost.isPresent()) {
            post.setData(existingPost.get().getData());
        } else {
            post.setData(LocalDate.now());
        }
        
        blogappservice.save(post);
        attributes.addFlashAttribute("mensagem", "Post editado com sucesso!");
        return "redirect:/posts/" + post.getId();
    }

    // Deletar um post
    @GetMapping(value = "/deletepost/{id}")
    public String deletePost(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        Optional<PostModel> post = blogappservice.findById(id);
        if (post.isPresent()) {
            // Primeiro remove os comentários associados ao post
            Iterable<PostComentarioModel> comentarios = blogappservice.findComentariosByPost(post.get());
            blogappservice.deleteComentarios(comentarios);
            
            // Depois remove o post
            blogappservice.delete(post.get());
            attributes.addFlashAttribute("mensagem", "Post deletado com sucesso!");
        } else {
            attributes.addFlashAttribute("mensagem", "Post não encontrado!");
        }
        return "redirect:/posts";
    }

    // Método para adicionar comentário
    @PostMapping(value = "/addcomment")
    public String addComment(PostComentarioModel comment, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/posts/" + comment.getPostModel().getId();
        }
        
        // Recupera o post para garantir a associação correta
        Optional<PostModel> post = blogappservice.findById(comment.getPostModel().getId());
        if (post.isPresent()) {
            comment.setPostModel(post.get());
            blogappservice.saveComentario(comment);
            attributes.addFlashAttribute("mensagem", "Comentário adicionado com sucesso!");
        } else {
            attributes.addFlashAttribute("mensagem", "Post não encontrado para adicionar comentário!");
        }
        
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
            return new ModelAndView("redirect:/posts");
        }
        return mv;
    }

    @PostMapping(value = "/editcomment")
    public String updateComment(@Valid PostComentarioModel comment, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
            return "redirect:/editcomment/" + comment.getId();
        }
        
        // Recupera o comentário existente para manter a referência ao post
        Optional<PostComentarioModel> existingComment = blogappservice.findIdComentario(comment.getId());
        if (existingComment.isPresent()) {
            // Mantém a referência ao post original
            comment.setPostModel(existingComment.get().getPostModel());
        }
        
        blogappservice.saveComentario(comment);
        attributes.addFlashAttribute("mensagem", "Comentário editado com sucesso!");
        return "redirect:/posts/" + comment.getPostModel().getId();
    }

    // Adicionar método para deletar um comentário
    @GetMapping(value = "/deletecomment/{id}")
    public String deleteComment(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        Optional<PostComentarioModel> comment = blogappservice.findIdComentario(id);
        if (comment.isPresent()) {
            UUID postId = comment.get().getPostModel().getId();
            blogappservice.deleteComentarios(List.of(comment.get()));
            attributes.addFlashAttribute("mensagem", "Comentário deletado com sucesso!");
            return "redirect:/posts/" + postId;
        } else {
            attributes.addFlashAttribute("mensagem", "Comentário não encontrado!");
            return "redirect:/posts";
        }
    }
}
