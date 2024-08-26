package donghyunlee.hyunlog.dto;

import donghyunlee.hyunlog.domain.Category;
import donghyunlee.hyunlog.domain.ImageFile;
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
}
