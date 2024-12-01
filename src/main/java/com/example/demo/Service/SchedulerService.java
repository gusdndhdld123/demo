//package com.example.demo.Service;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SchedulerService {
//    private int count1 = 0;
//    private final int maxcount = 3600 * 24;
//
//    @Scheduled(fixedRate = 1000)
//    public void countHour() {
//        if (count1 < maxcount) {
//            count1++;
//            System.out.println("현재 count1: " + count1 + " / 목표 maxcount: " + maxcount);
//        } else {
//            System.out.println(maxcount + "초가 경과하였습니다. 네이버 로그인 시작");
//            // 네이버 로그인 메서드 호출 메서드 작성 필요
//            count1 = 0;
//        }
//    }
//}