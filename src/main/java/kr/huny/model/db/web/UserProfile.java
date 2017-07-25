package kr.huny.model.db.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sousic on 2017-07-10.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfile
{
    @NotNull
    @Size(min=1, max = 255)
    private String email;

    @NotNull
    @Size(min=1, max = 25)
    private String username;

    private String about;

    /*
    닉네임 중복 체크
     */
    private String nickNameState = "none";
}
