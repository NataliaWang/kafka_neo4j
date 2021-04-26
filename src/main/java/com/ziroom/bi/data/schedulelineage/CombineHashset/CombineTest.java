package com.ziroom.bi.data.schedulelineage.CombineHashset;

import java.util.HashSet;
import java.util.Set;

public class CombineTest {
    public static void main(String[] args) {
        Message message = new Message();
        Set<String> set = new HashSet<>();
        set.add("inse");
        set.add("update");
        message.setSql(set);
        test(message);
    }
    public static void test(Message message) {
        String sql ="inset into";
        message.getSql().add(sql);
        System.out.println(message.getSql());
    }
}
