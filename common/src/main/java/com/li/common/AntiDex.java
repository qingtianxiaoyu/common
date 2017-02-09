package com.li.common;

/**
 * Created by liweifa on 2016/12/10.
 * 用来对抗dex2jar反编译的一些方法
 * 原理
 * dex2jar并不是没有漏洞的
 * 通过大量的使用dex2jar对代码进行反编译的过程中
 * 可以发现其在对一些代码反编译的过程中
 * 会发生异常导致反编译失败
 * 通过将这些代码加入到工程中可以在一定程度上加大工程被反编译的难度
 */

public class AntiDex {
    /**
     * @link sun.security.util.BitArray
     * 此方法已经失效
     */
    private int position(int var0) {
        return 1 << 7 - var0 % 8;
    }

}
