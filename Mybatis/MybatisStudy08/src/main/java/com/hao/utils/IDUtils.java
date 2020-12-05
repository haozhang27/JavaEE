package com.hao.utils;

import org.junit.Test;

import java.util.UUID;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
@SuppressWarnings("all")
public class IDUtils {

    public static String getId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Test
    public void test() {
        System.out.println(IDUtils.getId());
    }
}
