package algorithms.impl;

import algorithms.ISchedulingAlgorithms;
import lombok.extern.log4j.Log4j2;
import model.Elevator;
import model.Req;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Log4j2
public class SstfSchedulingAlgorithms implements ISchedulingAlgorithms {

    @Override
    public List<Req> scheduling(List<Req> reqList, Elevator elevator) {
        int n = reqList.size();
        List<Req> res = new ArrayList<>();

        Integer oldCurrentFloor = elevator.getCurrentFloor();
        for(int i = 0; i < n; i++) {
            int min = reqList.get(i).getCurrentFloor();
            int index = i;

            for(int j = i + 1; j < n; j++) {
                Req req = reqList.get(j);
                int newCurrentFloor = Math.abs(req.getCurrentFloor() - oldCurrentFloor);
                if(min > newCurrentFloor) {
                    min = newCurrentFloor;
                    index = j;
                }
            }

            Req req = reqList.get(index);
            res.add(req);
            oldCurrentFloor = req.getTargetFloor();
        }

        return res;
    }
}
