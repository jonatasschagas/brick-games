package com.jc.software.logic.util;

/**
 * Created by jonataschagas on 04/03/18.
 */
public class Utils {

    public static long asLong(int x, int y) {
        return (((long) x) << 32) | y;
    }

    public static int getX(long location) {
        return (int) ((location >> 32) & 0xFFFFFFFF);
    }

    public static int getY(long location) {
        return (int) (location & 0xFFFFFFFF);
    }

}
