package algorithms.impl;

import algorithms.ISchedulingAlgorithms;
import constants.Direction;
import model.Elevator;
import model.Req;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
public class ScanSchedulingAlgorithms implements ISchedulingAlgorithms {
    @Override
    public List<Req> scheduling(List<Req> reqList, Elevator elevator) {

        Integer direction = elevator.getType();

        // 电梯初始化默认为【上行】
        if(Direction.EQUALS.getType().equals(direction)) {
            direction = Direction.UP.getType();
        }

        Integer elevatorCurrentFloor = elevator.getCurrentFloor();
        Map<Integer, List<Req>> groupByDirection = reqList.stream().collect(
                Collectors.groupingBy(r -> {
                    Integer directionType = Direction.getDirection(r.getCurrentFloor(), r.getTargetFloor()).getType();
                    if(elevatorCurrentFloor.compareTo(r.getCurrentFloor()) > 0) {
                        directionType = Direction.getReverseDirection(directionType);
                    }
                    return directionType;
                }));

        List<Req> res = new ArrayList<>(reqList.size());

        List<Req> directionReqList = groupByDirection.get(direction);
        Collections.sort(directionReqList, Comparator.comparingInt(Req::getCurrentFloor));

        Integer reverseDirection = Direction.getReverseDirection(direction);
        List<Req> reverseDirectionReqList = groupByDirection.get(reverseDirection);
        Collections.sort(reverseDirectionReqList, (r1, r2) -> r2.getCurrentFloor() - r1.getCurrentFloor());

        for(Req req : directionReqList) {
            res.add(req);
        }

        for(Req req : reverseDirectionReqList) {
            res.add(req);
        }


        return res;
    }
}
