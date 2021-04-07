package com.example.mq1.util;

import java.util.concurrent.atomic.AtomicInteger;
import com.example.mq1.bean.App;

public class LogSequence{
	private static final int SEQ_START = 100000;
	private static final int SEQ_MAX = 999999;
	private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(SEQ_START);
	public static final int nextValue(){
		ATOMIC_INTEGER.compareAndSet(SEQ_MAX + 1, SEQ_START);
        return ATOMIC_INTEGER.getAndIncrement();
    }
	public static final String get() {
		return String.valueOf(System.currentTimeMillis()) + App.ID + nextValue();
	}
}