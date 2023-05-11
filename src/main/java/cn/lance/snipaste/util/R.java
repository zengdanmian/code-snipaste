package cn.lance.snipaste.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class R<T> {
    private Integer code = 0;
    private String msg = "success";
    private T data;
    private Long count;

    public static <T> R<T> ok() {
        return new R<>();
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data);
    }

    public static <T> R<T> error(Integer code, String message) {
        return new R<>(code, message, null);
    }

    private R() { }

    private R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        if (data instanceof List) {
            this.count = Long.valueOf(((List) data).size());
        }
    }

    private R(T data) {
        this.data = data;
        if (data instanceof List) {
            this.count = Long.valueOf(((List) data).size());
        }
    }
}
