package com.example.Article.Controller;

import com.example.Article.Entity.Article;
import com.example.Article.Exception.RessourceNotFoundException;
import com.example.Article.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
   private ArticleRepository articleRep;
    @GetMapping("/articles")
    public List<Article> getAllArticle() {
        return articleRep.findAll();
    }
    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable(value = "id")Long id)
    throws  RessourceNotFoundException{
    Article article= articleRep.findById(id)
            .orElseThrow(()-> new RessourceNotFoundException("Article Not Found"+id));
    return ResponseEntity.ok().body(article);
    }
    @PostMapping("/Article")
    public Article CreateArticle (@Validated @RequestBody Article article)
    {
        return articleRep.save(article);
    }
    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle (@PathVariable(value = "id")Long Id,
                                                  @Validated @RequestBody Article articleDetails )
        throws RessourceNotFoundException {
        Article article=articleRep.findById(Id)
                .orElseThrow(()-> new RessourceNotFoundException("Article not found for this id :" +Id));
        article.setTitle(articleDetails.getTitle());
        article.setDescription(articleDetails.getDescription());
        article.setContent(articleDetails.getContent());
        final Article updatedArticle =articleRep.save(article);
        return ResponseEntity.ok(updatedArticle);
    }
    @DeleteMapping("/article/{id}")
    public Map<String,Boolean> deleteArticle (@PathVariable(value="id")Long Id)
        throws  RessourceNotFoundException        {
        Article article=articleRep.findById(Id)
                .orElseThrow(()->new RessourceNotFoundException("Article not found for this id:"+Id));
        articleRep.delete(article);
            Map<String,Boolean> response =new HashMap<>();
            response.put("Article Has been deleted:",Boolean.TRUE);
            return response;
    }


}
