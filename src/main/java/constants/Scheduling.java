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
public enum Scheduling {

    FCFS(0, "FCFS"),
    SSTF(1, "SSTF"),
    SCAN(2, "SCAN"),
    LOOK(3, "LOOK"),
    SATF(4, "SATF");

    private Integer type;
    private String desc;

    public Integer getType() {
        return this.type;
    }

    public static String getDesc(Integer type) {
        Scheduling[] values = values();
        for(Scheduling value : values) {
            if(value.type.equals(type)) {
                return value.desc;
            }
        }
        return "UNKNOWN";
    }
}
