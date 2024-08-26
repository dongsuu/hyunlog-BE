package donghyunlee.hyunlog.service;

import donghyunlee.hyunlog.domain.Post;
import donghyunlee.hyunlog.domain.PostTag;
import donghyunlee.hyunlog.domain.Tag;
import donghyunlee.hyunlog.dto.PostDto;
import donghyunlee.hyunlog.repository.PostRepository;
import donghyunlee.hyunlog.repository.PostTagRepository;
import donghyunlee.hyunlog.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    @Transactional
    public void savePost(PostRequest postRequest){
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .member(postRequest.getMember())
                .content(postRequest.getContent())
                .category(postRequest.getCategory())
                .imageFiles(postRequest.getImageFiles())
                .build();
        postRepository.save(post);
    }
    public List<PostDto> findRecentPosts(){
        List<Post> recentPosts = postRepository.findAllByOrderByCreatedDateDesc();
        return recentPosts.stream()
                .map((recentPost) -> {
                    List<PostTag> postTags = postTagRepository.findAllByPost(recentPost);
                    List<Tag> tags = postTags.stream()
                            .map(PostTag::getTag)
                            .toList();

                    return Post.to(recentPost, tags);
                }).toList();
    }

    public PostDto findPostById(Long postId){
        List<Tag> tags = postTagRepository.findAllByPostId(postId)
                .stream()
                .map(PostTag::getTag)
                .toList();
        return postRepository.findById(postId)
                .map(post -> Post.to(post, tags))
                .orElseThrow(RuntimeException::new);
    }

    public List<PostDto> findPostsByCategoryId(Long categoryId){
        return postRepository.findAllByCategoryId(categoryId)
                .stream().map(post -> {
                    List<Tag> tags = postTagRepository.findAllByPostId(post.getId())
                            .stream()
                            .map(PostTag::getTag)
                            .toList();
                    return Post.to(post, tags);
                })
                .toList();
    }

    @Transactional
    public void updatePost(Long postId, PostRequest postRequest){
        Post updatePost = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);
        updatePost.update(
                postRequest.getTitle(),
                postRequest.getContent(),
                postRequest.getCategory(),
                postRequest.getImageFiles());
    }

    @Transactional
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }
}
