package com.hao.utils;

import org.apache.ibatis.cache.Cache;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
public class MyCacheUtil implements Cache {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public void putObject(Object o, Object o1) {

    }

    @Override
    public Object getObject(Object o) {
        return null;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
