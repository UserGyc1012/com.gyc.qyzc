package com.offcn.dycommon.enums;

/**
 * @ClassName OrderStatusEnumes
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 16:06
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */
public enum OrderStatusEnumes {
    UNPAY((byte)0,"未支付"),
    CANCEL((byte)1,"已取消"),
    PAYED((byte)2,"支付成功"),
    WAITING((byte)3,"等待发货"),
    SEND((byte)4,"已发货"),
    SENDED((byte)5,"已送达"),
    SUCCESS((byte)6,"交易完成"),
    FAIL((byte)7,"交易未完成");

    private byte code;

    OrderStatusEnumes(byte code, String status) {
        this.code = code;
        this.status = status;
    }

    private String status;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
