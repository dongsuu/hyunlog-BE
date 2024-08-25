package donghyunlee.hyunlog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String nickname;
    private String email;
    private String introduce;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_file_id")
    private ImageFile profileImage;
    private String instagramUrl;
    private String githubUrl;

    @Builder
    private Member(String name, String nickname, String email, String introduce, ImageFile profileImage, String instagramUrl, String githubUrl) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.introduce = introduce;
        this.profileImage = profileImage;
        this.instagramUrl = instagramUrl;
        this.githubUrl = githubUrl;
    }
}
