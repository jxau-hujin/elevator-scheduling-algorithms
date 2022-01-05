import constants.Direction;
import constants.Scheduling;
import lombok.extern.log4j.Log4j2;
import model.Elevator;
import model.Req;
import model.Res;
import model.Statistics;
import org.junit.Test;
import service.IElevatorService;
import service.impl.ElevatorService;
import util.RandomReqGenerateExec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
@Log4j2
public class ElevatorTest {

    private static Integer reqTotal = 100;

    private static Integer currentFloor = 1;

    private static Integer maxFloor = 32;

    private static Integer minFloor = 1;

    private static List<Req> randomReqList;

    private static IElevatorService elevatorService = new ElevatorService();

    // 评价指标：吞吐量、周转时间、等待时间、响应时间

    private static Integer index = 0;

    private Statistics statistics;

    @Test
    public void test() {
        int total = 10;
        int algorithmsTotal = 5;
        long[][] statisticsArr = new long[algorithmsTotal][7];

        for(int i = 0; i < total; i++) {
            randomReqList = RandomReqGenerateExec.generate(maxFloor, minFloor, reqTotal);

            for(int j = 0; j < algorithmsTotal; j++) {
                scheduling_test();
                statisticsArr[j][0] += statistics.getTotalCost();
                statisticsArr[j][1] += statistics.getAvgCost();
                statisticsArr[j][2] += statistics.getThroughput();
                statisticsArr[j][3] += statistics.getAvgTurnaroundTime();
                statisticsArr[j][4] += statistics.getAvgWeightTurnaroundTime();
                statisticsArr[j][5] += statistics.getAvgWaitTime();
                statisticsArr[j][6] += statistics.getAvgResponse();
            }
            index = 0;
        }

        for(int i = 0; i < algorithmsTotal; i++) {
            log.info("-------------------------------------------------------");
            log.info("【平均移动总距离】{}", statisticsArr[i][0] / total);
            log.info("【平均移动距离】{}",statisticsArr[i][1] / total);
            log.info("【平均处理时延】{} ms/个", statisticsArr[i][2] / total);
            log.info("【平均周转时间】{} ms", statisticsArr[i][3] / total);
            log.info("【平均带权周转时间】{} ns",statisticsArr[i][4] / total);
            log.info("【平均等待时间】{} ms",statisticsArr[i][5] / total);
            log.info("【平均响应时间】{} ms", statisticsArr[i][6] / total);
            log.info("-------------------------------------------------------");
        }
    }

    @Test
    public void scheduling_test() {
        // init
        List<Req> reqList = new ArrayList<>(randomReqList);
        Integer schedulingType = index++;

        log.info("-------------开始 调度算法: {}-------------", Scheduling.getDesc(schedulingType));


        Elevator elevator = new Elevator(currentFloor, maxFloor, minFloor, Direction.EQUALS.getType(), schedulingType);
        // 平均周转时间
        long avgTurnaroundTime = 0L;
        // 平均加权周转时间
        long avgWeightTurnaroundTime = 0L;
        // 平均等待时间
        long avgWaitTime = 0L;
        // 平均响应时间
        long avgResponseTime = 0L;
        // 平均移动距离
        long avgCost = 0L;
        // 移动总距离
        long totalCost = 0L;

        long startTime = System.nanoTime();
        List<Res> resList = elevatorService.handler(elevator, reqList);

        for(Res res : resList) {
            // 周转时间 = 完成时间 - 提交时间
            long turnaroundTime = res.getDoneTime() - res.getRequestTime();
            // 加权周转时间 = 周转时间 / 实际运行时间
            long weightTurnaroundTime = turnaroundTime / (res.getDoneTime() - res.getHandlerTime());
            // 等待时间 = 开始处理时间 - 提交时间
            long waitTime = res.getHandlerTime() - res.getRequestTime();
            // 响应时间 = 开始处理时间 - 提交时间
            long responseTime = res.getHandlerTime() - res.getRequestTime();

            long costTime = res.getCostTime();

            avgTurnaroundTime += turnaroundTime;
            avgWeightTurnaroundTime += weightTurnaroundTime;
            avgWaitTime += waitTime;
            avgResponseTime += responseTime;

            totalCost += costTime;

            log.info("res: {}", res);
        }
        long endTime = System.nanoTime();

        avgCost = totalCost / reqTotal;
        avgWeightTurnaroundTime /= reqTotal;
        avgTurnaroundTime /= reqTotal;
        avgWaitTime /= reqTotal;
        avgResponseTime /= reqTotal;

        long throughput_ms = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) / reqTotal;
        long avgTurnaroundTime_ms = TimeUnit.MILLISECONDS.convert((avgTurnaroundTime), TimeUnit.NANOSECONDS);
        long avgWeightTurnaroundTime_ms = TimeUnit.MILLISECONDS.convert((avgWeightTurnaroundTime), TimeUnit.NANOSECONDS);
        long avgWaitTime_ms = TimeUnit.MILLISECONDS.convert((avgWaitTime), TimeUnit.NANOSECONDS);
        long avgResponse_ms = TimeUnit.MILLISECONDS.convert((avgResponseTime), TimeUnit.NANOSECONDS);

        log.info("【移动总距离】{}", totalCost);
        log.info("【平均移动总距离】{}", avgCost);
        log.info("【平均处理时延】{} ms/个", throughput_ms);
        log.info("【平均周转时间】{} ms", avgTurnaroundTime_ms);
        log.info("【平均带权周转时间】{} ns", avgWeightTurnaroundTime);
        log.info("【平均等待时间】{} ms", avgWaitTime_ms);
        log.info("【平均响应时间】{} ms", avgResponse_ms);


        log.info("-------------结束 调度算法: {}-------------", Scheduling.getDesc(schedulingType));
        statistics =  new Statistics(totalCost, avgCost, throughput_ms, avgTurnaroundTime_ms, avgWeightTurnaroundTime, avgWaitTime_ms, avgResponse_ms);
    }

    @Test
    public void fcfs() {
        Elevator elevator = new Elevator(currentFloor, maxFloor, minFloor, Direction.EQUALS.getType(), Scheduling.FCFS.getType());
        List<Res> resList = elevatorService.handler(elevator, randomReqList);
        for(Res res : resList) {
            System.out.println(res.toString());
        }
    }

    @Test
    public void sstf() {
        Elevator elevator = new Elevator(currentFloor, maxFloor, minFloor, Direction.EQUALS.getType(), Scheduling.SSTF.getType());
        List<Res> resList = elevatorService.handler(elevator, randomReqList);
        for(Res res : resList) {
            System.out.println(res.toString());
        }
    }

    @Test
    public void scan() {
        Elevator elevator = new Elevator(currentFloor, maxFloor, minFloor, Direction.EQUALS.getType(), Scheduling.SCAN.getType());
        List<Res> resList = elevatorService.handler(elevator, randomReqList);
        for(Res res : resList) {
            System.out.println(res.toString());
        }
    }

    @Test
    public void look() {
        Elevator elevator = new Elevator(currentFloor, maxFloor, minFloor, Direction.EQUALS.getType(), Scheduling.LOOK.getType());
        List<Res> resList = elevatorService.handler(elevator, randomReqList);
        for(Res res : resList) {
            System.out.println(res.toString());
        }
    }

    @Test
    public void satf() {
        Elevator elevator = new Elevator(currentFloor, maxFloor, minFloor, Direction.EQUALS.getType(), Scheduling.SATF.getType());
        List<Res> resList = elevatorService.handler(elevator, randomReqList);
        for(Res res : resList) {
            System.out.println(res.toString());
        }
    }


}
