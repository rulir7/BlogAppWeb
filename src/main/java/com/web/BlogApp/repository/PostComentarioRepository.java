package com.web.BlogApp.repository;
import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostComentarioRepository extends JpaRepository<PostComentarioModel, UUID> {
    Iterable<PostComentarioModel> findByPostModel(PostModel postModel);
}