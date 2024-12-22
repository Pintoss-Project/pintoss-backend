package pintoss.giftmall.domains.user.dto;

import lombok.Getter;

@Getter
public class NaverUserResponse {

    private Response response;

    @Getter
    public static class Response {
        private String id;
        private String email;
        /* 아래는 필요하다면 설정
        private String name;
        private String nickname;
        private String gender;
        private String age;
        private String birthday;
        private String profileImage;
        private String birthyear;
        private String mobile;
         */
    }
}
