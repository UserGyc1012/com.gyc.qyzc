package com.offcn.vo;

/**
 * @ClassName BaseVo
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/3 18:34
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BaseVo {
    private  String accessToken;

    public BaseVo() {
    }

    public BaseVo(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
