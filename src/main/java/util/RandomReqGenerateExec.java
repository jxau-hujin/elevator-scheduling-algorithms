package util;

import model.Req;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/5
 */
public class RandomReqGenerateExec {

    private static SecureRandom secureRandom = new SecureRandom();


    public static List<Req> generate(Integer maxFloor, Integer minFloor, Integer total) {
        List<Req> reqList = new ArrayList<>(total);
        Integer bound = maxFloor - minFloor + 1;
        for(int i = 0; i < total; i++) {
            Integer currentFloor = secureRandom.nextInt(bound) + minFloor;
            Integer targetFloor = secureRandom.nextInt(bound) + minFloor;
            Req req = new Req(currentFloor, targetFloor);
            reqList.add(req);
        }
        return reqList;
    }
}
