package donghyunlee.hyunlog.domain;

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
    private String tagName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<ImageFile> imageFiles = new ArrayList<>();

    @Builder
    public Post(String title, String content, String tagName, Member member, List<ImageFile> imageFiles) {
        this.title = title;
        this.content = content;
        this.tagName = tagName;
        this.member = member;
        this.imageFiles = imageFiles;
    }
}
