package donghyunlee.hyunlog.service;

import donghyunlee.hyunlog.domain.*;
import donghyunlee.hyunlog.dto.PostDto;
import donghyunlee.hyunlog.repository.*;
import donghyunlee.hyunlog.request.PostRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostTagRepository postTagRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    EntityManager em;

    @DisplayName("이미지 없는 게시글을 저장한다.")
    @Test
    void savePost() {
        // given
        Member member = Member.builder()
                .name("admin")
                .email("admin@gmail.com")
                .nickname("FE장인")
                .introduce("짧은 소개입니다.")
                .githubUrl("https://github.com")
                .instagramUrl("@hyunlog")
                .build();
        Member savedMember = memberRepository.save(member);
        Category feCategory = Category.createCategory("FE", 1);
        Category savedCategory = categoryRepository.save(feCategory);
        Tag tag1 = Tag.createTag("JavaScript");
        Tag tag2 = Tag.createTag("FrontEnd");
        List<Tag> tags = tagRepository.saveAll(List.of(tag1, tag2));
        PostRequest postRequest = PostRequest.builder()
                .title("제목1")
                .content("내용1")
                .member(savedMember)
                .imageFiles(null)
                .tags(tags)
                .category(savedCategory)
                .build();


        // when
        PostDto postResponse = postService.savePost(postRequest);
        List<PostTag> postTags = postTagRepository.findAllByPostId(postResponse.getId());

        // then
        assertThat(postResponse.getTitle()).isEqualTo("제목1");
        assertThat(postResponse.getAuthorName()).isEqualTo("admin");
        assertThat(postResponse.getCategoryName()).isEqualTo("FE");
        assertThat(postResponse.getTags()).hasSize(2);
        assertThat(postResponse.getImageFiles()).isNull();
        assertThat(postTags).hasSize(2);
        assertThat(postTags)
                .extracting("tag.name")
                .contains("JavaScript", "FrontEnd");
    }

    @DisplayName("이미지가 있는 게시글을 저장한다.")
    @Test
    public void savePostWithImages(){
        // given

        // when

        // then
        assertThat(1).isNull();
    }



    @DisplayName("최근글을 10개씩 조회한다.")
    @Test
    void findRecentPosts() {
        assertThat(1).isNull();
    }

    @DisplayName("특정 게시글을 조회한다.")
    @Test
    void findPostById() {
        //given
        Member member = createMember("user1", "user1@naver.com", "BE Master", "hello~", "https://github.com", "@be-hyun");
        Member savedMember = memberRepository.save(member);
        Tag tag1 = Tag.createTag("Java");
        Tag tag2 = Tag.createTag("Backend");
        Tag tag3 = Tag.createTag("Spring");
        Category category = Category.createCategory("BE", 2);
        Category savedCategory = categoryRepository.save(category);
        PostRequest postRequest = createPostRequest("제목1", "내용1", null, List.of(tag1, tag2, tag3), savedMember, savedCategory);
        PostDto savedPost = postService.savePost(postRequest);

        // when
        PostDto findPostResponse = postService.findPostById(savedPost.getId());

        // then
        assertThat(findPostResponse)
                .extracting("title", "authorName", "categoryName")
                .containsExactly("제목1", "user1", "BE");
        assertThat(findPostResponse.getTags()).hasSize(3)
                .extracting("name")
                .contains("Java", "Backend", "Spring");
        assertThat(findPostResponse.getImageFiles()).isNull();

    }

    @DisplayName("카테고리별로 게시글을 조회한다.")
    @Test
    void findPostsByCategoryId() {
        // given
        Member member = createMember("user1", "user1@naver.com", "BE Master", "hello~", "https://github.com", "@be-hyun");
        Member savedMember = memberRepository.save(member);
        Tag tag1 = Tag.createTag("Java");
        Category category1 = Category.createCategory("BE", 2);
        Category category2 = Category.createCategory("FE", 3);
        Category category3 = Category.createCategory("Infra", 4);
        List<Category> categories = categoryRepository.saveAll(List.of(category1, category2, category3));

        PostRequest postRequest1 = createPostRequest("제목1", "내용1", null, List.of(tag1), savedMember, categories.get(0));
        PostRequest postRequest2 = createPostRequest("제목2", "내용2", null, List.of(tag1), savedMember, categories.get(0));
        PostRequest postRequest3 = createPostRequest("제목3", "내용3", null, List.of(tag1), savedMember, categories.get(1));
        PostRequest postRequest4 = createPostRequest("제목4", "내용4", null, List.of(tag1), savedMember, categories.get(2));
        PostRequest postRequest5 = createPostRequest("제목5", "내용5", null, List.of(tag1), savedMember, categories.get(2));
        PostRequest postRequest6 = createPostRequest("제목6", "내용6", null, List.of(tag1), savedMember, categories.get(2));
        List.of(postRequest1, postRequest2, postRequest3, postRequest4, postRequest5, postRequest6)
            .forEach(post -> {
                postService.savePost(post);
            });

        // when
        List<PostDto> postsByBeCategory = postService.findPostsByCategoryId(categories.get(0).getId());
        List<PostDto> postsByFeCategory = postService.findPostsByCategoryId(categories.get(1).getId());
        List<PostDto> postsByInfraCategory = postService.findPostsByCategoryId(categories.get(2).getId());


        // then
        assertThat(postsByBeCategory)
                .extracting("title")
                .contains("제목1", "제목2");
        assertThat(postsByBeCategory).hasSize(2);
        assertThat(postsByFeCategory)
                .extracting("categoryName")
                .contains("FE");
        assertThat(postsByFeCategory.size()).isEqualTo(1);
        assertThat(postsByInfraCategory).hasSize(3);

    }

    @DisplayName("게시글을 수정한다.")
    @Test
    void updatePost() {
        // given
        Member member = createMember("user1", "user1@naver.com", "BE Master", "hello~", "https://github.com", "@be-hyun");
        Member savedMember = memberRepository.save(member);
        Tag tag1 = Tag.createTag("Java");
        Tag tag2 = Tag.createTag("C++");
        List<Tag> tags = tagRepository.saveAll(List.of(tag1, tag2));
        Category category1 = Category.createCategory("BE", 2);
        Category category2 = Category.createCategory("Embedded", 2);
        categoryRepository.saveAll(List.of(category1, category2));
        PostRequest postRequest1 = createPostRequest("제목1", "내용1", null, List.of(tags.getFirst()), savedMember, category1);
        PostDto savedPost = postService.savePost(postRequest1);
        PostRequest postUpdateRequest = createPostRequest("제목2", "내용2", null, List.of(tags.getLast()), savedMember, category2);

        // when
        postService.updatePost(savedPost.getId(), postUpdateRequest);
        PostDto updatedPost = postService.findPostById(savedPost.getId());
        // then
        assertThat(updatedPost)
                .extracting("title", "content")
                .containsExactly("제목2", "내용2");
        assertThat(updatedPost.getCategoryName()).isEqualTo("Embedded");
        assertThat(updatedPost.getTags().getFirst().getName()).isEqualTo("C++");
    }

    @DisplayName("게시글을 삭제한다.")
    @Test
    void deletePost() {
        // given
        Member member = createMember("user1", "user1@naver.com", "BE Master", "hello~", "https://github.com", "@be-hyun");
        Member savedMember = memberRepository.save(member);
        Tag tag1 = Tag.createTag("Java");
        Category category1 = Category.createCategory("BE", 2);
        categoryRepository.save(category1);
        PostRequest postRequest1 = createPostRequest("제목1", "내용1", null, List.of(tag1), savedMember, category1);
        PostRequest postRequest2 = createPostRequest("제목2", "내용2", null, List.of(tag1), savedMember, category1);
        PostDto savedPost1 = postService.savePost(postRequest1);
        PostDto savedPost2 = postService.savePost(postRequest2);

        // when
        postService.deletePost(savedPost1.getId());
        List<Post> posts = postRepository.findAll();

        // then
        assertThat(posts).hasSize(1)
                .extracting("title", "content")
                .containsExactly(tuple("제목2", "내용2"));
    }


    private PostRequest createPostRequest(String title, String content, List<ImageFile> imageFiles, List<Tag> tags, Member member, Category category){
        return PostRequest.builder()
            .title(title)
            .content(content)
            .member(member)
            .imageFiles(imageFiles)
            .tags(tags)
            .category(category)
            .build();
    }

    private Member createMember(String name, String email, String nickname, String introduce, String githubUrl, String instagramUrl){
        return Member.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .introduce(introduce)
                .githubUrl(githubUrl)
                .instagramUrl(instagramUrl)
                .build();
    }
}