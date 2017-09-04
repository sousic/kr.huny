package kr.huny.model.db.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoryRegister {
    private long categorySeq = 0;
    @NotNull
    @Size(max = 50)
    private String categoryName;
    @NotNull
    @Size(max = 20)
    private String restName;
    private boolean isUsed;
    private int restChecked;
}
