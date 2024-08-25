package donghyunlee.hyunlog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "IMAGE_FILE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_file_id")
    private Long id;
    private String name;
    private String originalPath;
    private String s3Url;

    @Builder
    private ImageFile(String name, String originalPath, String s3Url) {
        this.name = name;
        this.originalPath = originalPath;
        this.s3Url = s3Url;
    }
}
