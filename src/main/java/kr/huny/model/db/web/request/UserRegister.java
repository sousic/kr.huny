package kr.huny.model.db.web.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sousic on 2017-07-10.
 */
@Builder
@Data
public class UserRegister
{
    @NotNull
    @Size(min=1, max = 255)
    private String email;

    @NotNull
    @Size(min = 6, max = 30)
    private String password;

    @NotNull
    @Size(min=6, max = 30)
    private String passwordConfirm;

    @NotNull
    @Size(min=1, max = 25)
    private String username;

    private String about;

    /*
    이메일 중복 체크
     */
    private String emailState = "none";

    /*
    닉네임 중복 체크
     */
    private String nickNameState = "none";
}
