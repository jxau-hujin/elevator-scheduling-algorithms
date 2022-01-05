package service;

import model.Elevator;
import model.Req;
import model.Res;

import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
public interface IElevatorService {

    /**
     * 电梯服务处理
     * @param elevator
     * @return
     */
    List<Res> handler(Elevator elevator, List<Req> reqList);
}
