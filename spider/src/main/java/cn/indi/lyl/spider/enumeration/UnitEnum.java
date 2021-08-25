package cn.indi.lyl.spider.enumeration;

import lombok.Getter;

@Getter
public enum UnitEnum {

    GRAM(1,"克"), MILLILITER(2, "毫升"),UNKNOWN(0, "未知的");

    UnitEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;
    private String value;
}
