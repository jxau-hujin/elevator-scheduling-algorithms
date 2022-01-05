package service;

import algorithms.AlgorithmsConfig;
import algorithms.ISchedulingAlgorithms;
import constants.Direction;
import constants.Scheduling;
import lombok.extern.log4j.Log4j2;
import model.Elevator;
import model.Req;
import model.Res;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Log4j2
public abstract class AbstractElevatorService implements IElevatorService{

    @Override
    public List<Res> handler(Elevator elevator, List<Req> reqList) {
        boolean check = checkArgs(elevator, reqList);
        if(!check) {
            return null;
        }

        Integer algorithmsType = elevator.getAlgorithmsType();
        ISchedulingAlgorithms schedulingAlgorithms = AlgorithmsConfig.schedulingAlgorithmsMap.get(algorithmsType);

        log.info("================= 调度开始 =================");
        log.info("当前调度算法: {}, 请求大小: {}, 当前电梯运行方向: {}", Scheduling.getDesc(elevator.getAlgorithmsType()), reqList.size(), Direction.getDesc(elevator.getType()));
        List<Req> schedulingReqList = schedulingAlgorithms.scheduling(reqList, elevator);
        log.info("================= 调度结束 =================");

        List<Res> resList = new ArrayList<>(schedulingReqList.size());

        for(Req req : schedulingReqList) {
            Res res = new Res();
            res.setRequestTime(req.getStartTime());
            res.setHandlerTime(System.nanoTime());

            Integer receiveCost = receive(elevator, req, res);
            // 模拟电梯移动当前请求所在楼层耗用时间
            try {
                Thread.sleep(10 * receiveCost);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer sendCost = send(elevator, req, res);
            // 模拟电梯移动当前请求到达目标楼层所用时间
            try {
                Thread.sleep(10 * sendCost);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            res.setTimeUnit(TimeUnit.SECONDS);
            res.setCostTime(receiveCost + sendCost);
            res.setDoneTime(System.nanoTime());
            resList.add(res);
        }


        return resList;
    }

    /**
     * 参数校验
     * @param elevator
     * @param reqList
     * @return
     */
    protected abstract boolean checkArgs(Elevator elevator, List<Req> reqList);

    /**
     * 电梯移动请求所在楼层
     */
    protected abstract Integer receive(Elevator elevator, Req req, Res res);

    /**
     * 电梯移动请求目标楼层
     */
    protected abstract Integer send(Elevator elevator, Req req, Res res);


}
