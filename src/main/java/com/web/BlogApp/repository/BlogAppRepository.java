package com.web.BlogApp.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.BlogApp.model.PostModel;

public interface BlogAppRepository extends JpaRepository<PostModel, UUID>{
	

}
