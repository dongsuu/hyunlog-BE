package donghyunlee.hyunlog.repository;

import donghyunlee.hyunlog.domain.Post;
import donghyunlee.hyunlog.domain.PostTag;
import donghyunlee.hyunlog.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findAllByPost(Post post);
    List<PostTag> findAllByTag(Tag tag);
    List<PostTag> findAllByPostId(Long postId);
    void deleteByPostId(Long postId);
}
