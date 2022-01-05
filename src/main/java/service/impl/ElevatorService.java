package service.impl;

import algorithms.AlgorithmsConfig;
import constants.Direction;
import lombok.extern.log4j.Log4j2;
import model.Elevator;
import model.Req;
import model.Res;
import service.AbstractElevatorService;

import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Log4j2
public class ElevatorService extends AbstractElevatorService {

    @Override
    protected boolean checkArgs(Elevator elevator, List<Req> reqList) {
        Integer maxFloor = elevator.getMaxFloor();
        Integer minFloor = elevator.getMinFloor();
        if(maxFloor.intValue() <= minFloor.intValue()) {
            return false;
        }

        Integer currentFloor = elevator.getCurrentFloor();

        if(currentFloor.intValue() > maxFloor.intValue() || currentFloor.intValue() < minFloor.intValue()) {
            return false;
        }

        Integer algorithmsType = elevator.getAlgorithmsType();
        if(!AlgorithmsConfig.schedulingAlgorithmsMap.containsKey(algorithmsType)) {
            return false;
        }

        if(reqList == null || reqList.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    protected Integer receive(Elevator elevator, Req req, Res res) {
        Integer oldElevatorCurrentFloor = elevator.getCurrentFloor();
        Integer reqCurrentFloor = req.getCurrentFloor();
        Direction d1 = Direction.getDirection(oldElevatorCurrentFloor, reqCurrentFloor);

        elevator.setCurrentFloor(reqCurrentFloor);
        res.setFromFloor(oldElevatorCurrentFloor);
        log.info("【receive】当前楼层: {}F, 移动到: {}F 运行方向: {}", oldElevatorCurrentFloor, reqCurrentFloor, d1.name());
        return Math.abs(oldElevatorCurrentFloor - reqCurrentFloor);
    }

    @Override
    protected Integer send(Elevator elevator, Req req, Res res) {
        Integer targetFloor = req.getTargetFloor();
        Integer newElevatorCurrentFloor = elevator.getCurrentFloor();
        Direction d2 = Direction.getDirection(newElevatorCurrentFloor, targetFloor);

        elevator.setCurrentFloor(targetFloor);
        res.setToFloor(newElevatorCurrentFloor);
        log.info("【  send 】当前楼层: {}F, 移动到: {}F 运行方向: {}", newElevatorCurrentFloor, targetFloor, d2.name());
        return Math.abs(newElevatorCurrentFloor - targetFloor);
    }
}
