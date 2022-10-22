package com.example.Article.Repository;

import com.example.Article.ArticleApplication;
import com.example.Article.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ArticleRepository extends JpaRepository <Article,Long> {
}
