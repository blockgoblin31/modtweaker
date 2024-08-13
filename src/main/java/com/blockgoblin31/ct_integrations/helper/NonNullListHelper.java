package com.blockgoblin31.ct_integrations.helper;

import net.minecraft.core.NonNullList;

import java.util.Arrays;

public class NonNullListHelper {
    public static <T> NonNullList<T> convert(T[] input) {
        NonNullList<T> list = NonNullList.create();
        list.addAll(Arrays.asList(input));
        return list;
    }
}
