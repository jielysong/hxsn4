package com.snsoft.ctpf.beans;

/**
 * <p>Title: 测土配方施肥专家咨询系统</p>
 *
 * <p>Description: 养分型型对象，使用 int 值标识养分的类型在计算和查询中都使用静态的
 * 变量值标识</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: 华夏神农</p>
 *
 * @author 马利强　2012年2月17日
 * @version 1.0
 */
public final class NutrientType {
    /**
     * 有机质
     */
    public final static int O = 0;

    /**
     * 全氮
     */
    public final static int N = 1;

    /**
     * 缓效磷
     */
    public final static int sP = 2;

    /**
     *速效磷
     */
    public final static int P = 3;

    /**
     * 缓效钾
     */
    public final static int sK = 4;

    /**
     * 速效钾
     */
    public final static int K = 5;

    /**
     * 酸碱度pH值
     */
    public final static int pH = 6;

    /**
     * 微量元素铜
     */
    public final static int Cu = 7;

    /**
     * 微量元素铁
     */
    public final static int Fe = 8;

    /**
     * 微量元素锌
     */
    public final static int Zn = 9;

    /**
     * 微量元素硫
     */
    public final static int S = 10;

    /**
     * 微量元素锰
     */
    public final static int Mn = 11;

    /**
     * 微量元素硼
     */
    public final static int B = 12;

    /**
     * 微量元素硅
     */
    public final static int Si = 13;

    /**
     * 微量元素钙
     */
    public final static int Ca = 14;

    /**
     * 微量元素钼
     */
    public final static int Mo = 15;


    /**
     * 土种编号
     */
    public final static int Tz = 16;

    /**
     * 养分的个数，当创建养分数组时候，可以引用该值
     */
    public final static int MAX_NUM = 17;
}
