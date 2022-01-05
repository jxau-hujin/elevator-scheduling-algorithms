package algorithms;

import model.Elevator;
import model.Req;

import java.util.List;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
public interface ISchedulingAlgorithms {

    /**
     * 调度
     * @param reqList
     * @param elevator
     * @return
     */
    List<Req> scheduling(List<Req> reqList, Elevator elevator);
}
