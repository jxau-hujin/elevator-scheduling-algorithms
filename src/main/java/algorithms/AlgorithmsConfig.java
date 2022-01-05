package algorithms;

import algorithms.impl.*;
import constants.Scheduling;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/4
 */
public class AlgorithmsConfig {

    public static Map<Integer, ISchedulingAlgorithms> schedulingAlgorithmsMap = new HashMap<>();

    static {
        schedulingAlgorithmsMap.put(Scheduling.FCFS.getType(), new FcfsSchedulingAlgorithms());
        schedulingAlgorithmsMap.put(Scheduling.SSTF.getType(), new SstfSchedulingAlgorithms());
        schedulingAlgorithmsMap.put(Scheduling.SCAN.getType(), new ScanSchedulingAlgorithms());
        schedulingAlgorithmsMap.put(Scheduling.LOOK.getType(), new LookSchedulingAlgorithms());
        schedulingAlgorithmsMap.put(Scheduling.SATF.getType(), new SatfSchedulingAlgorithms());
    }
}
