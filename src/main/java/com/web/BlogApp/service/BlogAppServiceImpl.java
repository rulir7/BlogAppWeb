package com.web.BlogApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;
import com.web.BlogApp.repository.BlogAppRepository;
//import com.web.BlogApp.repository.PostComentarioRepository;

import jakarta.transaction.Transactional;

//regras de negocio podem serem colocadas nessa classe de serviço
//ela serve como uma camada intermediaria entre o controler e repository e
//diminuir o acoplamento

@Service
public class BlogAppServiceImpl implements BlogAppService {
	
	@Autowired
	BlogAppRepository blogapprepository;
	
//	@Autowired
//	PostComentarioRepository postComentarioRepository;

	@Override
	public List<PostModel> findAll() {
		// TODO Auto-generated method stub
		return blogapprepository.findAll();
	}
	
	@Override
	public Optional<PostModel> findById(UUID id) {
		// TODO Auto-generated method stub
		return blogapprepository.findById(id);
	}

	@Override
	public PostModel save(PostModel post) {
		// TODO Auto-generated method stub
		return blogapprepository.save(post);
	}

	@Override
	@Transactional
	public void delete(PostModel post) {
		// TODO Auto-generated method stub
		blogapprepository.delete(post);
		
	}
	
	//  TRATA COMENTÁRIOS DOS POSTS
		
//	@Override
//	public Optional<PostComentarioModel> findIdComentario(UUID id) {
//		// TODO Auto-generated method stub
//		return postComentarioRepository.findById(id);
//	}
//
//	@Override
//	public void deleteComentarios(Iterable<PostComentarioModel> postComentarioModel) {
//		// TODO Auto-generated method stub
//		postComentarioRepository.deleteAll(postComentarioModel);
//	}
//
//	@Override
//	public Iterable<PostComentarioModel> findComentariosByPost(PostModel PostModel) { // busca os comentarios de um post
//		// TODO Auto-generated method stub
//		return postComentarioRepository.findByPostModel(PostModel);
//	}
//
//	@Override
//	public List<PostComentarioModel> findAllComentarios() {
//		// TODO Auto-generated method stub
//		 return postComentarioRepository.findAll();	
//	}
//
//	@Override
//	public PostComentarioModel saveComentario(PostComentarioModel postComentarioModel) {
//		// TODO Auto-generated method stub
//		return postComentarioRepository.save(postComentarioModel);
//	}

}
