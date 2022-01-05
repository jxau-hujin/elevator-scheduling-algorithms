package model;

import constants.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Req {

    /**
     * 当前请求用户所在楼层
     */
    private Integer currentFloor;

    /**
     * 当前请求用户目标楼层
     */
    private Integer targetFloor;

    /**
     * 1：上行
     * -1: 下行
     */
    private Integer type;

    /**
     * 当前请求的时间
     */
    private Long startTime;

    public Req(Integer currentFloor, Integer targetFloor) {
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
        this.type = Direction.getDirection(currentFloor, targetFloor).getType();
        this.startTime = System.nanoTime();
    }
}
