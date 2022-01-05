package algorithms.impl;

import algorithms.ISchedulingAlgorithms;
import model.Elevator;
import model.Req;

import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
public class SatfSchedulingAlgorithms implements ISchedulingAlgorithms {
    @Override
    public List<Req> scheduling(List<Req> reqList, Elevator elevator) {
        Collections.sort(reqList, (r1, r2) -> {
            int r1Cost = Math.abs(r1.getCurrentFloor() - r1.getTargetFloor());
            int r2Cost = Math.abs(r2.getCurrentFloor() - r2.getTargetFloor());
            return r1Cost - r2Cost;
        });
        return reqList;
    }
}
