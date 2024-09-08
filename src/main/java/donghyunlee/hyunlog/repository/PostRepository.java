package donghyunlee.hyunlog.repository;

import donghyunlee.hyunlog.domain.Category;
import donghyunlee.hyunlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedDateDesc();
    List<Post> findByCategory(Category category);
    List<Post> findAllByCategoryId(Long categoryId);
}
