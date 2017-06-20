package com.hxsn.ssklf.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.hxsn.ssklf.R;
import com.hxsn.ssklf.utils.Tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by jiely on 2016/12/26.
 */
public class MyLineChart extends View {

    private static final float TEXT_TITLE_SIZE = 30;
    private static final float TEXT_VALUE_SIZE = 23;
    private final int Y_LABEL_SIZE = 7;
    Typeface normalFont = Typeface.create("宋体", Typeface.NORMAL);
    Typeface blodFont = Typeface.create("宋体", Typeface.BOLD);
    final int COLOR_GRID = getResources().getColor(R.color.gray_dark);       //网格颜色
    final int COLOR_WORNING = getResources().getColor(R.color.red);       //网格颜色
    final int COLOR_TEXT = getResources().getColor(R.color.black_text_n);    //文字颜色
    final int COLOR_POINT = getResources().getColor(R.color.sky_blue);    //圆点颜色
    final int COLOR_CURVE = getResources().getColor(R.color.green_none);     //折线、曲线颜色
    final int COLOR_BG_IN = getResources().getColor(R.color.color_31);      //曲线内部背景色
    final int COLOR_BG_OUT = getResources().getColor(R.color.gray_light_s);     //曲线外部背景色
    final int[] yLabelArray1 = {-10,0,10,20,30,40,50};
    final int[] yLabelArray2 = {0,15,30,50,60,80,100};
    final int[] yLabelArray3 = {-10,0,10,20,30,40,50};
    final int[] yLabelArray4 = {0,6000,12000,18000,24000,30000,36000};
    private DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    //final double[] yArray = {21,-12,13,19,14,22,Double.NaN,Double.NaN,Double.NaN,Double.NaN,13,46,14,12,13,31,14,12,13,21,14,12,13,11};
    private List<Integer> yLabelList;
    private Context context;
   // private int xTotalLength,yTotalLength;//x，y轴总长度
   // private int xLength,yLength;//x，y轴分段长度
    //private int yValueCnt=0;//y轴数据个数
    private List<Double> yValList;//y轴数据列表
    private List<Integer> xValList;//y轴数据列表
    private int[] xValArray = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    private String title = "温度变化曲线";//图标标题
    //private double xMin,xMax,yMin,yMax,yAverage;//x轴最小值，最大值；y轴最小值，最大值，y平均值
    //private int windowWidth,windowHight;
    private int type=1;//数据类型 1-空气温度,2-空气湿度,3-土壤温度,4-光照
    private boolean isHaveData = false;


    public MyLineChart(Context context) {
        super(context);
        this.context = context;
        Log.i("MyLineChart","MyLineChart---------1");
    }

    public MyLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Log.i("MyLineChart","MyLineChart---------3");
    }

    public MyLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Log.i("MyLineChart","MyLineChart---------2");
    }

    public void setDate(int type,List<Integer> xVals, List<Double> yVals){
        Log.i("MyLineChart","setDate");
        isHaveData = true;
        this.type = type;
        yLabelList = new ArrayList<>();
        yValList = yVals;
        xValList = xVals;
        initData(type);
    }

    private void initData(int type){
        if(xValList == null){
            xValList = new ArrayList<>();
        }
        if(yValList == null){
            yValList = new ArrayList<>();
        }
        yLabelList = new ArrayList<>();
        for(int i=0; i<Y_LABEL_SIZE; i++){
            switch (type){
                case 1:
                    yLabelList.add(yLabelArray1[i]);
                    title = "空气温度变化曲线";
                    break;
                case 2:
                    yLabelList.add(yLabelArray2[i]);
                    title = "空气湿度变化曲线";
                    break;
                case 3:
                    yLabelList.add(yLabelArray3[i]);
                    title = "土壤温度变化曲线";
                    break;
                case 4:
                    title = "光照变化曲线";
                    yLabelList.add(yLabelArray4[i]);
                    break;
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("MyLineChart","onLayout");
        initData(type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(COLOR_BG_IN);
        Log.i("MyLineChart","onDraw");
        //Log.i("MyLineChart","width="+this.getWidth()+",height="+this.getHeight());

        int xStart = 80;
        int yStart = this.getHeight()-getRelative(250);
        Log.i("MyLineChart",",yStart="+yStart);
        int yEnd = getRelative(150);
        int xEnd = getWidth();

        //画标题文字
        Paint paint = new Paint();
        paint.setColor(COLOR_TEXT);
        paint.setTextSize(TEXT_TITLE_SIZE);
        paint.setTypeface(blodFont);
        canvas.drawText(title, getWidth()-300, getRelative(100), paint);

        // 画y坐标轴
        paint.setStrokeWidth(3);
        canvas.drawLine(xStart, yStart, xStart,yEnd, paint);
        // 画x坐标轴
        paint.setStrokeWidth(3);
        canvas.drawLine(xStart, yStart, xEnd-50,yStart, paint);

        int xTotalLength = xEnd-xStart;                     //x轴总长度
        int xLength = 0;// = xTotalLength/(xValArray.length-1);    //x轴分段长度
        int yTotalLength = yStart-yEnd;                     //y轴总长度
        int  yLength = yTotalLength/Y_LABEL_SIZE-1;                      //y轴分段长度
        //画x轴数值 和 x网格
        paint.setTextSize(TEXT_VALUE_SIZE);
        //int size = xValArray.length;

        paint.setStrokeWidth(1);
        paint.setColor(COLOR_GRID);
        if(!isHaveData){//没有数据的时候
            xLength = xTotalLength/23;
            for(int i=0; i<24; i++){
                if(i<12){
                    canvas.drawText(String.valueOf(xValArray[i*2]), xStart+xLength*i*2-5, yStart+getRelative(60), paint);
                }
                canvas.drawLine(xStart+xLength*i, yStart, xStart+xLength*i,yEnd, paint);//画竖直线
            }
        }else {//有数据的时候
            xLength = xTotalLength/xValList.size()-1;
            int cunt = xValList.size();
            for(int i=0; i<cunt; i++){
                if(cunt >12){//大于12时 数值写一半
                    if( i< xValList.size()/2){
                        canvas.drawText(String.valueOf(xValArray[i*2]), xStart+xLength*i*2-5, yStart+getRelative(80), paint);
                    }
                }else {//小于12时 数值全写
                    //Log.i("MyLineChart",",xIndex="+String.valueOf(xValArray[i])+",xStart="+xStart+",x="+(xStart+xLength*i*-5));
                    canvas.drawText(String.valueOf(xValArray[i]), xStart+xLength*i-5, yStart+getRelative(80), paint);
                }
                canvas.drawLine(xStart+xLength*i, yStart, xStart+xLength*i,yEnd, paint);//画竖直线
            }
        }

        //画y轴数值 和 y网格
        int size = 0;//y轴数值的宽度
        paint.setStrokeWidth(1);
        for(int i=0; i<Y_LABEL_SIZE; i++){
            size = String.valueOf(yLabelList.get(i)).length()*10;
            canvas.drawText(String.valueOf(yLabelList.get(i)), xStart-30-size, yStart-yLength*i, paint);
            canvas.drawLine(xStart-20,  yStart-yLength*i, xEnd-50, yStart-yLength*i, paint);//画平行线
        }

        //paint.setStrokeWidth(2);
        //canvas.drawLine(xStart-20,  yStart-yLength*3, xEnd-50, yStart-yLength*3, paint);//不知什么原因第四行画不出来，所以加粗

        if(isHaveData && yValList.size() > 0){
            //画点
            //int pointX = 0,pointY=0,oldPointX,oldy2;//点的x轴坐标，点的y轴坐标
            if(xValList.size() > 1){
                xLength = xTotalLength/Y_LABEL_SIZE-1;
            }

            int pointX=0,oldPointX,oldPointY,pointY=0;

            //Line line = new Line();

            for(int i=0; i<xValList.size(); i++){
                //Log.i("MyLineChart","yValList"+i+"="+yValList.get(i));
                if(!yValList.get(i).isNaN()){
                    oldPointX = pointX;
                    oldPointY = pointY;
                    int position = Tools.getLabelIndex(yLabelList,yValList.get(i))-1;//y值在刻度中的位置
                    // Log.i("MyLineChart","i="+i);
                    if(position < 0 ){//超下限时
                        pointY = yStart;//-yLength*position-step;
                        pointX = xStart + (i * xLength);
                        paint.setColor(COLOR_WORNING);
                        paint.setTextSize(TEXT_TITLE_SIZE);
                        canvas.drawText(decimalFormat.format(yValList.get(i)),pointX-20, pointY+getRelative(100), paint);
                    }else if(position >= 6){//超上限时
                        pointY = yStart-yLength*(Y_LABEL_SIZE-1);//-step;
                        pointX = xStart + (i * xLength);
                        paint.setColor(COLOR_WORNING);
                        paint.setTextSize(TEXT_TITLE_SIZE);
                        canvas.drawText(decimalFormat.format(yValList.get(i)),pointX-10, pointY-getRelative(50), paint);
                    }else {
                        double yDownLabel = yLabelList.get(position+1);
                        double yUpLabel = yLabelList.get(position);
                        int step = (int) (yLength* (yValList.get(i)-yUpLabel)/(yDownLabel-yUpLabel));
                        pointY = yStart-yLength*position-step;
                        pointX = xStart + (i * xLength);
                    }
                    if(i>0 && (!yValList.get(i-1).isNaN())){
                        //画折线
                        paint.setStrokeWidth(6);
                        paint.setColor(COLOR_CURVE);
                        canvas.drawLine(oldPointX,  oldPointY, pointX, pointY, paint);
                    }
                    //画点
                    if(!yValList.get(i).isNaN()){
                        paint.setColor(COLOR_POINT);
                        canvas.drawCircle(pointX, pointY, 10, paint);
                    }
                }
            }
        }
    }


    /**
     * 获取高度的相对高度
     * @param a 实际高度
     * @return 相对高度
     */
    private int getRelative(int a){
        WindowManager winManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        //int windowWidth = winManager.getDefaultDisplay().getWidth();
        int windowHeight = winManager.getDefaultDisplay().getHeight();
        return a*this.getHeight()/windowHeight;
    }

}
