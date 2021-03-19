package com.example.mq1.util;

import org.assertj.core.util.Lists;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeightAlgorithmUtil {

    public static  void main(String[] args) {
        String[] weight = {"A", "A", "A", "A", "A", "B", "B", "B", "C", "C"};

        final int times = 500000;

        final long hashStart = System.currentTimeMillis();

        List hashRes = getList4Hash(weight, times);

        printRes("Hash", hashStart, hashRes);

        final long randomStart = System.currentTimeMillis();

        List randomRes = getList4Random(weight, times);

        printRes("Random", randomStart, randomRes);


    }

    private static void printRes(String method, long start, List resList) {
        final Map collect = (Map) resList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final double a = ((Long)collect.get("A")).doubleValue();

        final double b = ((Long)collect.get("B")).doubleValue();

        final double c = ((Long)collect.get("C")).doubleValue();

        final double sum = a + b + c;

        NumberFormat nt = NumberFormat.getPercentInstance();

        nt.setMinimumFractionDigits(2);

        System.out.println(method + "\t use millis: " + (System.currentTimeMillis() - start));

        System.out.println("A" + ":" + nt.format(a / sum));

        System.out.println("B" + ":" + nt.format(b / sum));

        System.out.println("C" + ":" + nt.format(c / sum));

    }

    private static List getList4Hash(String[] weight, int times) {
        List result = Lists.newArrayList();

        for (int i = 0; i < times; i++) {
            final String a = UUID.randomUUID().toString() + System.currentTimeMillis();

            final int hash = a.hashCode();

            final int index = hash > 0 ? hash : -hash;

            final String res = weight[index % weight.length];

            result.add(res);

        }

        return result;

    }

    private static List getList4Random(String[] weight, int times) {
        List result = Lists.newArrayList();

        Random random = new Random();

        for (int i = 0; i < times; i++) {
            final int index = random.nextInt(weight.length);

            final String res = weight[index];

            result.add(res);

        }

        return result;

    }

}
