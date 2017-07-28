package kr.huny.common;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sousic on 2017-07-28.
 */
public class SocialTypeTest {

    @Test
    public void getNameToFacebook() throws Exception {
        CommonConst.SocialType socialType = CommonConst.SocialType.FACEBOOK;

        assertThat(socialType.getName(), is("FACEBOOK"));
    }

    @Test
    public void getnameToBasic() throws Exception{
        CommonConst.SocialType socialType = CommonConst.SocialType.BASIC;

        assertThat(socialType.getName(), is("BASIC"));
    }
}
