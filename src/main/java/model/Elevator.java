package model;

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
public class Elevator {

    /**
     * 电梯当前楼层
     */
    private Integer currentFloor;

    /**
     * 电梯最高楼层
     */
    private Integer maxFloor;

    /**
     * 电梯最低楼层
     */
    private Integer minFloor;

    /**
     * 电梯当前状态 UP DOWN EQUALS
     */
    private Integer type;

    /**
     * 电梯调度算法
     */
    private Integer algorithmsType;
}
