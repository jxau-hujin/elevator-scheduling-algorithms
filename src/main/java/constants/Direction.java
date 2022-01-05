package constants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@AllArgsConstructor
@NoArgsConstructor
public enum Direction {

    UP(1, "上行"),
    EQUALS(0, "不移动"),
    DOWN(-1, "下行");

    private Integer type;

    private String desc;

    public static Direction getDirection(Integer currentFloor, Integer targetFloor) {
        if(currentFloor > targetFloor) {
            return DOWN;
        } else if(currentFloor < targetFloor) {
            return UP;
        }
        return EQUALS;
    }

    public static String getDesc(Integer type) {
        Direction[] values = values();
        for(Direction value : values) {
            if(value.type.equals(type)) {
                return value.desc;
            }
        }
        return "UNKNOWN";
    }

    public static Integer getReverseDirection(Integer direction) {
        if(EQUALS.type.equals(direction)) {
            return UP.type;
        }
        return direction * -1;
    }

    public Integer getType() {
        return this.type;
    }
}
