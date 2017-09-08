package kr.huny.model.db.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardFreeRegister {
    @NotNull
    @Min(1)
    private long categorySeq;
    @NotNull
    private String title;
    @NotNull
    private String context;

    private String galleryQueueList;
    private String attachQueueList;
    private String tagQueueList;
}
