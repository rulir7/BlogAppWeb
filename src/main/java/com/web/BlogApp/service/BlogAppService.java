package com.web.BlogApp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;

public interface BlogAppService {
	
	List<PostModel> findAll();
	Optional<PostModel> findById(UUID id);
	PostModel save(PostModel post);
	void delete(PostModel post);
	
	//TRATA COMENTÁRIOS DOS POSTS
//	List<PostComentarioModel> findAllComentarios();
//	Optional<PostComentarioModel> findIdComentario(UUID id);
//	PostComentarioModel saveComentario(PostComentarioModel postComentarioModel);
//	void deleteComentarios( Iterable<PostComentarioModel> postComentarioModel);
//	Iterable<PostComentarioModel> findComentariosByPost(PostModel PostModel);

}
