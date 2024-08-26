package donghyunlee.hyunlog.domain;

import donghyunlee.hyunlog.dto.PostDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<ImageFile> imageFiles = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member member, Category category, List<ImageFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
        this.imageFiles = imageFiles;
    }

    public void update(String title, String content, Category category, List<ImageFile> imageFiles){
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageFiles = imageFiles;
    }

    public static PostDto to(Post post, List<Tag> tags){
        return PostDto.builder()
                .id(post.getId())
                .authorName(post.getMember().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .categoryName(post.getCategory().getName())
                .imageFiles(post.getImageFiles())
                .tags(tags)
                .build();
    }
}
