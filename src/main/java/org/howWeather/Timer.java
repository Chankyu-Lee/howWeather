package org.howWeather;

public class Timer {
    private long startTime;
    private long executionTime;

    // 시작 시간 기록
    public void start() {
        startTime = System.currentTimeMillis();
    }

    // 종료 시간 기록 및 실행 시간 반환
    public void end() {
        long endTime = System.currentTimeMillis();
        executionTime = endTime - startTime;
    }

    @Override
    public String toString() {
        String str = "작업 실행 시간: " + executionTime + " 밀리초";
        return str;
    }

    public void printTime() {
        System.out.println(toString());
    }
}
