package kr.huny.common;

/**
 * Created by sousic on 2017-06-28.
 */
public class CommonConst {

    public enum SocialType
    {
        BASIC("BASIC"),
        FACEBOOK("FACEBOOK");

        private String name;

        SocialType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
