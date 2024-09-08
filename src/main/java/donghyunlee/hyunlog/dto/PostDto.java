package donghyunlee.hyunlog.dto;

import donghyunlee.hyunlog.domain.ImageFile;
import donghyunlee.hyunlog.domain.Post;
import donghyunlee.hyunlog.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String authorName;
    private String content;
    private String categoryName;
    private List<Tag> tags = new ArrayList<>();
    private List<ImageFile> imageFiles = new ArrayList<>();

    public static PostDto of(Post post, List<Tag> tags){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getMember().getName())
                .categoryName(post.getCategory().getName())
                .imageFiles(post.getImageFiles())
                .tags(tags)
                .build();
    }
}
