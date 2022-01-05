package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author：gezellighied.h
 * @date: 2022/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    long totalCost;

    long avgCost;

    long throughput;

    long avgTurnaroundTime;

    long avgWeightTurnaroundTime;

    long avgWaitTime;

    long avgResponse;
}
