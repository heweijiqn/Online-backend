package com.hwj.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * @Description:
 * 需要手动抛出异常
 * @Version: 1.0
 */
public class HwjException extends RuntimeException {
    private Integer code;
    private String msg;
}
