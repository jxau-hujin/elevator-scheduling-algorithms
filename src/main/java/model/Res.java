package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Res {

    /**
     * 本次调度出发层
     */
    private Integer fromFloor;

    /**
     * 本次调度到达层
     */
    private Integer toFloor;

    /**
     * 本次调度花费时间
     */
    private Integer costTime;

    /**
     * 本次调度时间单位
     */
    private TimeUnit timeUnit;

    /**
     * 请求发送时间
     */
    private Long requestTime;

    /**
     * 请求开始处理时间
     */
    private Long handlerTime;

    /**
     * 请求处理完成时间
     */
    private Long doneTime;
}
