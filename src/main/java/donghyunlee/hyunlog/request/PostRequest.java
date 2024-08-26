package donghyunlee.hyunlog.request;

import donghyunlee.hyunlog.domain.Category;
import donghyunlee.hyunlog.domain.ImageFile;
import donghyunlee.hyunlog.domain.Member;
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
public class PostRequest {
    private String title;
    private String content;
    private Category category;
    private Member member;
    private List<ImageFile> imageFiles = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
}
