package com.yhr.cleanCM.service;

import com.yhr.cleanCM.dao.ArticleRepository;
import com.yhr.cleanCM.domain.Article;
import com.yhr.cleanCM.domain.Member;
import com.yhr.cleanCM.dto.article.ArticleDTO;
import com.yhr.cleanCM.dto.article.ArticleModifyForm;
import com.yhr.cleanCM.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Member member) {

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        article.setMember(member);
        articleRepository.save(article);

    }

    public ArticleDTO getArticle(Long id) throws NoSuchElementException {

        Article findArticle = getById(id);

        ArticleDTO articleDTO = new ArticleDTO(findArticle);

        return articleDTO;
    }

    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }

    public Article getById(Long id){

        Optional<Article> articleOptional = findById(id);

        articleOptional.orElseThrow(
                () -> new
                        NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        return articleOptional.get();
    }

    @Transactional
    public void modifyArticle(ArticleModifyForm articleModifyForm, Long id){
        Article findArticle = getById(id);

        findArticle.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getBody()
        );
    }

    public List<ArticleDTO> getList() {

        List<Article> articleList = articleRepository.findAll();

        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for (Article article : articleList) {

            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTOList.add(articleDTO);
        }

        return articleDTOList;

    }

    public void delete(Long id){
        Article findArticle = getById(id);
        articleRepository.delete(findArticle);
    }
}
