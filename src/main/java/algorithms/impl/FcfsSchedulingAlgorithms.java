package algorithms.impl;

import algorithms.ISchedulingAlgorithms;
import lombok.extern.log4j.Log4j2;
import model.Elevator;
import model.Req;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Log4j2
public class FcfsSchedulingAlgorithms implements ISchedulingAlgorithms {

    @Override
    public List<Req> scheduling(List<Req> reqList, Elevator elevator) {
        Collections.sort(reqList, (r1, r2) -> {
            Long r1StartTime = r1.getStartTime();
            Long r2StartTime = r2.getStartTime();
            if(r1StartTime > r2StartTime) {
                return 1;
            } else if(r1StartTime < r2StartTime) {
                return -1;
            }
            return 0;
        });
        return reqList;
    }
}
