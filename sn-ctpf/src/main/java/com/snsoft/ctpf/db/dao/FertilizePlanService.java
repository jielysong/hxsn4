package com.snsoft.ctpf.db.dao;


import com.snsoft.ctpf.beans.FertilizerInfo;
import com.snsoft.ctpf.beans.NutrientType;

import java.util.Iterator;
import java.util.Vector;

/**
 * <p>Title: 藁城测土配方施肥专家咨询系统</p>
 *
 * <p>Description: 肥料组合起来形成一个施肥计划</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: 华夏神农</p>
 *
 * @author 马利强
 * @version 1.0
 */
public class FertilizePlanService {

    /**
     * 对象数组存放一个或多个肥料，即一种肥料或多种肥料组合成为一个施肥方案
     */
    private Vector manure = new Vector();

    /**
     * 各种养分的用量数组
     */
    private double[] dosage = new double[NutrientType.MAX_NUM];

    /**
     * 迭代方法使用
     */
    private Iterator iter = null;

    /**
     * 计算出来的理论配方
     */
    private double[] theory=new double[NutrientType.MAX_NUM];

    /**
     * 默认构造方法
     */
    /**
     * 作物ID
     */
    private int cropid = -1;
    /**
     * 配方肥养分总含量
     */
    private float zhl = -1;
    /**
     * 配方肥配比下限
     */
    private double[] pbxx = new double[3];
    
    
    
    public FertilizePlanService() {
    }


    public FertilizePlanService(int schemeid){
    	
    	//这里原来的构造方法的主要作用就是将本次施肥中的肥料列表添加到manure中
//        if (schemeid > 0) {
//        	/**
//        	 * 
//        	 */
//            int[] ids = queryPlansID(schemeid);
//            if (ids != null) {
//                for (int i = 0; i < ids.length; i++) {
//                	FertilizerInfo mo = new FertilizerInfo(ids[i]);
//                    this.manure.add(mo);
//                }
//            }
//        }
    	
    }


    /**
     *
     * @param mo ManureObject
     */
    public void addFertilizerInfo(FertilizerInfo mo){
        if(mo!=null){
            this.manure.add(mo);
        }
    }

    public void setExcellentArg(int cropid, float zhl){
    	
    	this.cropid = cropid;
    	this.zhl = zhl;
    }

    /**
     * 返回理论 N/P/K 的比例值 百分数
     * @param nutrient int
     * @return double
     */
    public double getTheory(int nutrient){
        return this.theory[nutrient];
    }



    /**
     * 李开望算法
     * 根据给定的养分需要量，计算配方肥的比例与需要两的 cos 值（最优匹配系数）
     */
    private void excellentManure(){
        double N=this.dosage[NutrientType.N];
        double P=this.dosage[NutrientType.P];
        double K=this.dosage[NutrientType.K];
        
        excellentManureHandan(N,P,K);
        
    }



    /**
     *
     * @param N double
     * @param P double
     * @param K double
     */
    private void excellentManure(double N,double P,double K){
        double cos[]=new double[manure.size()];
        FertilizerInfo kmo=new FertilizerInfo();
        for(int i=0;i<this.manure.size();i++){
        	FertilizerInfo mo=getFertilizerInfo(i);
        	double N1 = mo.getN();
            double P1 = mo.getP();
            double K1 = mo.getK();
            if(mo.getDedicated()==1){  //说明是专用肥
                //公式计算进行优化
                double n=((N*N1+P*P1+K*K1)*(N*N1+P*P1+K*K1));
                double m=(N*N+P*P+K*K)*(N1*N1+P1*P1+K1*K1);
                if (m==0.0){
                    cos[i]=-1.0;
                }
                cos[i]=n/m;
            }else{
            	//如果不是专用肥，并且是钾肥，将其进行记录
            	if (N1==0 && P1==0 && K1>0){
            		//非复合肥时，记录一个钾肥。
            		kmo=mo;
            	}
            }
        }

        double max=0.0;
        int point=-1;
        for(int i=0;i<cos.length;i++){
            if(cos[i]>max){
                max=cos[i];
                point=i;
            }
        }

        if(point>=0){
        	FertilizerInfo mo=getFertilizerInfo(point);
            this.manure.clear();
            this.manure.add(mo);
            this.manure.add(kmo);   //复合肥时，增加钾肥施用。
        }
    }

    
    /**
     * 邯郸的复合肥优选算法，主要算法条件挑选
     * 1、底施N占整个生育期的40%;
     * 2、在基肥中，以应施P和K中最高的为基准（原来是以P为基准），不再补施P或K单质肥；
     * 3、推荐方案（1）底施肥量控制在20～30公斤，推荐方案（2）施肥量控制在40～50公斤。
     * @param N double
     * @param P double
     * @param K double
     * 
     * 在计算的过程中，计算出当磷和钾单独施满的肥料差（以5kg为一个单位）
     * 计算出总施肥量与40公斤的肥料差（以10公斤为一个单位）
     * 所有肥料计算完成后，选取磷钾肥料差最小的复合肥，如果磷钾肥料差相等，则筛选总施肥量与40公斤肥料差最小的肥料
     * 
     * 20140610 马利强修改此方法，将此方法配方肥筛选进行了调整
     * 如果全部的肥料中都是具备磷钾含量的配方肥时即进行优化筛选算法，否则不筛选
     * 增加了k的计数器，如果计数器不是全部肥料数，那么最后的筛选肥料的最终结果不执行
     */
    private void excellentManureHandan(double N,double P,double K){
    	
    	int PKSUBUNIT=8;	//磷钾肥料差以5kg为一个单位，便于肥料差分档
    	int VSUBUNIT=1;		//与参考施用量的肥料差，以5kg为一个单位
    	int WEIGHT=30;		//参考施用肥量	35kg
    	
    	long pksub[]=new long[manure.size()];	//磷钾肥料差
    	long vsub[]=new long[manure.size()];	//与参考施用量的肥料差
    	long tmppksub=9999;
    	long tmpvsub=9999;
    	int point=-1;
    	int k=0;
    	for(int i=0;i<this.manure.size();i++){
    		//遍历每一种肥料
        	FertilizerInfo mo=getFertilizerInfo(i);
	    	if(mo.getDedicated()==1){
	    		//是专用肥时，并且包括P和K时才进行优肥挑选
	    		if (mo.contain(NutrientType.P) && mo.contain(NutrientType.K)) {
	    			k++;
	    			double p = mo.calculteNotChange(NutrientType.P, P);
		        	//如果肥料中包含磷肥和钾肥，则施用两样都能补满的最大用量
		            double kp= mo.calculteNotChange(NutrientType.K, K);
		            pksub[i]=Math.round(Math.abs(kp-p)/PKSUBUNIT);   //计算每一种肥的满P和满K差
		            if (kp>p){
		            	vsub[i]=Math.round(Math.abs(kp-WEIGHT)/PKSUBUNIT);
		            }else{
		            	vsub[i]=Math.round(Math.abs(p-WEIGHT)/PKSUBUNIT);
		            }
		            if (tmppksub>pksub[i]){
		            	//说明找到更小的磷钾差肥料
		            	tmppksub=pksub[i];
		            	tmpvsub=vsub[i];
		            	point=i;
		            }else if(tmppksub==pksub[i] && tmpvsub>vsub[i]){
		            	//磷钾差相等时以与参考施肥量最接近为好
		            	tmpvsub=vsub[i];
		            	point=i;
		            }
		        }
	    	}
    	}
    	
        if(point>=0 && k==this.manure.size() || k>2){
        	//point代表最佳比例的配方肥, k代表全部都是配方肥才需要重新筛选。
        	FertilizerInfo mo=getFertilizerInfo(point);
            this.manure.clear();
            this.manure.add(mo);
            //this.manure.add(kmo);   //复合肥时，增加钾肥施用。
        }
    }
    
    
    
    
    
    /**
    * 邯郸的复合肥优选算法，主要算法条件挑选
    * 1、底施N占整个生育期的40%;
    * 2、在基肥中，以应施P和K中最高的为基准（原来是以P为基准），不再补施P或K单质肥；
    * 3、推荐方案（1）底施肥量控制在20～30公斤，推荐方案（2）施肥量控制在40～50公斤。
    * @param N double
    * @param P double
    * @param K double
    */
   private void excellentManureHandanBak(double N,double P,double K){
	   N=N/2;
	   P=P;
       double cos[]=new double[manure.size()];
       FertilizerInfo kmo=new FertilizerInfo();
       for(int i=0;i<this.manure.size();i++){
       	FertilizerInfo mo=getFertilizerInfo(i);
       	double N1 = mo.getN();
           double P1 = mo.getP();
           double K1 = mo.getK();
           if(mo.getDedicated()==1){  //说明是专用肥
               //公式计算进行优化
               double n=((N*N1+P*P1+K*K1)*(N*N1+P*P1+K*K1));
               double m=(N*N+P*P+K*K)*(N1*N1+P1*P1+K1*K1);
               if (m==0.0){
                   cos[i]=-1.0;
               }
               cos[i]=n/m;
           }else{
           	//如果不是专用肥，并且是钾肥，将其进行记录
           	if (N1==0 && P1==0 && K1>0){
           		//非复合肥时，记录一个钾肥。
           		kmo=mo;
           	}
           }
       }

       double max=0.0;
       int point=-1;
       for(int i=0;i<cos.length;i++){
           if(cos[i]>max){
               max=cos[i];
               point=i;
           }
       }

       if(point>=0){
       	FertilizerInfo mo=getFertilizerInfo(point);
           this.manure.clear();
           this.manure.add(mo);
           //this.manure.add(kmo);   //复合肥时，增加钾肥施用。
       }
   }
    
    
    
    
    
    /**
     * 计算各肥料的用量
     * 计算的顺序为 P、N、K
     * @param dosage double[]
     * @return boolean
     */
    public boolean calculate(double dosage[]){
        if (dosage != null) {
            System.arraycopy(dosage, 0, this.dosage, 0, dosage.length);

            //
            //excellentManureWangdu();
            //excellentManure();
              this.excellentManure();
            //
            calculateMostlyPhosphorus();

            //先从肥料组合中查找养分种类最多的肥料
            //calculateOptimizeAllNutrient();

            return true;
        }
        return false;
    }

    /**
     * 是否存在多种肥料，到取得肥料对象前必须先对用本方法
     * @return boolean
     */
    public boolean hasMore(){
        if(iter==null){
            iter=this.manure.iterator();
        }
        return iter.hasNext();
    }
    
    /**
     * 是否存在多种肥料，到取得肥料对象前必须先对用本方法
     * @return boolean
     */
    public boolean First(){

        iter=this.manure.iterator();

        return iter.hasNext();
    }

    /**
     * 返回一个肥料对象ManureObject
     * @return Object
     */
    public Object next(){
        return iter.next();
    }

    /**
     * 返回一个施肥计划中肥料对象总数
     * @return int
     */
    public int getManureObjectCount() {
        return this.manure.size();
    }

    /**
     * 返回具体某一肥料对象
     * @param index int
     * @return ManureObject
     */
    public FertilizerInfo getFertilizerInfo(int index) {
        return (FertilizerInfo)this.manure.get(index);
    }


    /**
     * 以 P 养分的含量为主要元素的计算方法
     * 该算法按 P、N、K 元素的顺序进行计算，先满足 P 元素的用量，再满足 N 元素的用量
     * 最后满足 K 元素的用量；如果是复合肥料则也按这个顺序进行计算；不考虑后边元素的
     * 补给和超出部分
     */
    private void calculateMostlyPhosphorus(){
        //calculatePhosphorus();  //P2O5
    	calculatePandK();		//以磷钾同补的方式，计算磷用量
    	calculateKalium();   //K2O
        calculateNitrogen();   //N
        
    }


    /**
     * 最优化所有养分的计算方法
     * 该算法从肥料养分最多的费量算起，找出施用量最少的元素决定复合肥料的用量，然后再计
     * 算剩余养分的肥料用量，最后得到最优的肥料施用量；不会出现补给和超出的情况
     *
     * 2007年08月22日 张建广添加肥料用量优化算法
     * 调用方法说明：在进行计算前给定的施用肥料的组合，然后调用 calculate(double dosage[])
     * 方法完成计算。注意，给定的肥料是有顺序的，先用前边的肥料满足养分需要，后边的肥料作为补充
     * 肥料。
     *   （1）复合肥料的优化计算，复合肥必须是第一中给定的肥料，后边可补充多种单质肥（单质肥
     * 最好不要用二元肥）；当某肥料的用量为0.0时，说明不需要补充这种肥料。
     *   （2）肥料组合时，将二元肥放到前边，单质肥放在后边。当某肥料的用量为0.0时，说明不需要补
     * 充这种肥料。
     *
     */
    private void calculateOptimizeAllNutrient(){
    	FertilizerInfo main=findFirstManure();
        if(main==null) return;
        int nutrientType=main.calculteMinDosage(dosage[NutrientType.N],
                                                dosage[NutrientType.P],
                                                dosage[NutrientType.K]);
       if(manure.size() == 1)
        	nutrientType = main.calculteMaxDosage(dosage[NutrientType.N],
                                                  dosage[NutrientType.P],
                                                  dosage[NutrientType.K]);
        this.manuressort(); //肥料排序

        switch(nutrientType){
            case NutrientType.N:  //按 N 进行计算后，施用的该肥料用量最少
                calculateNitrogen();
                calculatePhosphorus();
                calculateKalium();
                break;
            case NutrientType.P: //按 P 进行计算后，施用的该肥料用量最少
                calculatePhosphorus();
                calculateNitrogen();
                calculateKalium();
                break;
            case NutrientType.K:  //按 K 进行计算后，施用的该肥料用量最少
                calculateKalium();
                calculateNitrogen();
                calculatePhosphorus();
                break;
            default:

        }
    }



    /**
     * 返回肥料组合中包含养分最多的肥料对象
     * @return ManureObject
     */
    private FertilizerInfo findFirstManure(){
        int mc[]=new int[manure.size()];
        for(int i=0;i<manure.size();i++){
        	FertilizerInfo mo=(FertilizerInfo)manure.get(i);
            mc[i]=mo.getNutrientCount();
        }

        int max=0;
        int findIndex=0;
        for(int i=0;i<mc.length;i++){
            if(mc[i]>max){
                max=mc[i];
                findIndex=i;
            }
        }
        return getFertilizerInfo(findIndex);
    }


    /**
     * 计算 N
     */
    private void calculateNitrogen(){
        for(int i=0;i<manure.size();i++){
        	FertilizerInfo mo=(FertilizerInfo)manure.get(i);
            //如果该肥料用量已经计算过则跳过，不再重复计算
            if(mo.isCalculated()) continue;
            //如果肥料中不含 N 素则跳过
            if(!mo.contain(NutrientType.N)) continue;
            //mo=getFertilizerInfo(mo.getFertilizerid());
            //因为有了库里不存在的自动生成配方肥，所以由查找生成新的肥料变为了复制生成新的肥料
            mo=getNewFertilizerInfo(mo);	
            //计算满足纯氮 N 所需的肥料用量
            double n = mo.calculte(NutrientType.N,dosage[NutrientType.N]);
            dosage[NutrientType.N]=0.0;

            if (mo.contain(NutrientType.P)) {
                //计算肥料中含有的P2O5量
                double p = n * mo.getP() / 100;
                //总P2O5用量减去已含有的P2O5量，剩余P2O5用其他含P2O5的肥料补齐
                this.dosage[NutrientType.P] = this.dosage[NutrientType.P] - p;
            }

            if (mo.contain(NutrientType.K)) {
                //计算肥料中含有的K2O的量
                double k = n * mo.getPercent(NutrientType.K) / 100;
                //总K2O用量减去已含有的K2O量，剩余K2O用其他含K2O的肥料补齐
                this.dosage[NutrientType.K] = this.dosage[NutrientType.K] - k;
            }
            if (mo.getDedicated()==1){
            	//如果专用肥，则强制施满
	            if(n<=0.0f){
	            	mo.setDosage(0.01);		//避免重复计算
	            }else{
	            	mo.setDosage(n);		//避免重复计算
	            }
            }
            /*
            FertilizerInfo monew =new FertilizerInfo ();
            monew.setFertilizername(mo.getFertilizername());
            monew.setN(mo.getN());
            monew.setP(mo.getP());
            monew.setK(mo.getK());
            monew.setDosage(mo.getDosage());*/
            
            manure.set(i,mo);   //放回数组中

        }
    }

    
    /**
     * 计算 P 的同时，处理钾，如果是复合肥，同时含有磷和钾，那么要把他们全部补满。
     */
    private void calculatePandK() {
    	
    	if (calculateBeipiaoPandK()){
    		//在这里判断一下是否为北票的复合肥、氮磷肥和单磷肥底肥配方肥计算方式
    		//如果是的话单独处理。
    		return;
    	}
    	
    	if (calculateBeipiaoNPKandPK()){
    		//在这里判断一下是否为北票的复合肥、氮磷肥配方肥计算方式
    		//如果是的话单独处理。
    		return;
    	}
    	
        for (int i = 0; i < manure.size(); i ++) {
        	FertilizerInfo mo = (FertilizerInfo)manure.get(i);
            //如果该肥料用量已经计算过则跳过，不再重复计算
            if(mo.isCalculated()) continue;
            //如果肥料中不含 P2O5则跳过
            if(!mo.contain(NutrientType.P)) continue;
            //mo=getFertilizerInfo(mo.getFertilizerid());
            //因为有了库里不存在的自动生成配方肥，所以由查找生成新的肥料变为了复制生成新的肥料
            mo=getNewFertilizerInfo(mo);
            //计算满足P2O5所需的肥料用量
            double p = mo.calculte(NutrientType.P, dosage[NutrientType.P]);
            if (mo.contain(NutrientType.K)) {
            	//如果肥料中包含磷肥和钾肥，则施用两样都能补满的最大用量
	            double kp= mo.calculte(NutrientType.K, dosage[NutrientType.K]);
	            if (kp>p)p=kp;
            }
            dosage[NutrientType.P]=0.0;

            if (mo.contain(NutrientType.N)) {
                //计算肥料中含有的N素量
                double n = p * mo.getPercent(NutrientType.N) / 100;
                //总N素用量减去已含有的N素量，剩余N用其他含氮肥料补齐
                this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
            }

            if (mo.contain(NutrientType.K)) {
                //计算肥料中含有的K2O的量
                double k = p * mo.getPercent(NutrientType.K) / 100;
                //总K2O用量减去已含有的K2O量，剩余K2O用其他含K2O的肥料补齐
                this.dosage[NutrientType.K] = this.dosage[NutrientType.K] - k;
            }
            //2010年07月　马利强根据定兴修改需求，除了专用配方肥以外，
            //复合肥只补充最少量的肥料，其它用单质肥补充。
            if (mo.getDedicated()==1){
            	//如果专用肥，则强制施满
	            if(p<=0.0f){
	            	//在此种方法中增加了setDosage这个方法来使用
	            	mo.setDosage(0.01);		//避免重复计算
	            }else{
	            	mo.setDosage(p);		//避免重复计算
	            }
            }
            FertilizerInfo monew =new FertilizerInfo ();
            manure.set(i, mo); //为了避免地址传递影响，而新置了值放回数组中

        }
    }
    
    
   
    /**
     * 在这里判断一下是否为北票的复合肥、氮磷肥配方计算方式
     * 如果是的话首先用复合肥施底限、然后氮磷肥施底限补充满
     * @return
     */
    private boolean calculateBeipiaoNPKandPK() {
    	//下面开始检查是否符合北票的蔬菜底肥模式
    	if (manure.size()!=2) return false;
    	int indexNPK=-1;		//复合肥位置
    	int indexNP=-1;			//氮磷肥位置
    	for (int i = 0; i < manure.size(); i ++) {
    		FertilizerInfo mo = (FertilizerInfo)manure.get(i);
    		if (mo.contain(NutrientType.N) && mo.contain(NutrientType.P)
    				&& mo.contain(NutrientType.K) ) {
    			indexNPK=i;
    		}else if (mo.contain(NutrientType.N) && mo.contain(NutrientType.P) && !mo.contain(NutrientType.K)){
    			indexNP=i;
    		}
    	}
    	if (indexNPK<0 ||indexNP<0) return false;//有一种肥料未找到就不满足条件
    	
    	//下面开始计算
    	int NPK_MIN = 25;		//复合肥下限
    	int NPK_MAX = 80;		//复合肥上限
    	int NP_MIN = 15;		//氮磷肥下限
    	int NP_MAX = 25;		//氮磷肥上限

    	
    	FertilizerInfo moNPK=(FertilizerInfo)manure.get(indexNPK);		//获得三种肥料
    	FertilizerInfo moNP=(FertilizerInfo)manure.get(indexNP);
    
    	double fiNPK=NPK_MIN;		//各肥料用量，初始值全部采取下限值
    	double fiNP=NP_MIN;
    	
    	double p=fiNPK*moNPK.getPercent(NutrientType.P)/100;
    	p=p+fiNP*moNP.getPercent(NutrientType.P)/100;
    	double addp=0; //需补充肥量
    	if (p<dosage[NutrientType.P]){
    		//剩余的缺磷元素，用单质肥补充。
    		p=dosage[NutrientType.P]-p;		
			addp = moNP.calculte(NutrientType.P, p);		//需要NP肥补充磷用量	
			if ((NP_MAX-NP_MIN)>=addp){
    			//如果需要被补充的磷元素在单质肥区间内可以补充，就全部用单质肥补充
    			fiNP=fiNP+addp;
			}else{
				fiNP=NP_MAX;	//如果不够补充到最大值，
				//剩余补磷量去除新MAX到MIN之间的含磷量
    			p=p-(NP_MAX-NP_MIN)*moNP.getPercent(NutrientType.P)/100;
    			addp = moNPK.calculte(NutrientType.P, p);	//需要NPK肥补充磷用量
    			if ((NP_MAX-NP_MIN)>=addp){
    				fiNPK=fiNPK+addp;
    			}else{
    				fiNPK=NPK_MAX;	//如果不够补充到最大值，
    				//剩余补磷量去除新MAX到MIN之间的含磷量
        			p=p-(NPK_MAX-NPK_MIN)*moNPK.getPercent(NutrientType.P)/100;
        			addp = moNP.calculte(NutrientType.P, p);	//需要NPK肥补充磷用量
        			fiNP=fiNP+addp;
    			}
			}
    	}
    	
    	//已经计算出各种肥料的用量了，
    	dosage[NutrientType.P]=0.0;
    	moNPK.setDosage(fiNPK);		//NPK用量
    	double k = fiNPK * moNPK.getPercent(NutrientType.K) / 100;
    	this.dosage[NutrientType.K] = this.dosage[NutrientType.K] - k;
    	double n = fiNPK * moNPK.getPercent(NutrientType.N) / 100;
    	this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
    	
    	moNP.setDosage(fiNP);		//NP用量
    	n = fiNP * moNP.getPercent(NutrientType.N) / 100;
    	this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
    	manure.set(indexNPK, moNPK);
    	manure.set(indexNP, moNP);

		return true;
    	
    }
    
    
    
    /**
     * 在这里判断一下是否为北票的复合肥、氮磷肥和单磷肥底肥配方肥计算方式
     * 如果是的话首先用复合肥施底限、然后氮磷肥施底限、然后单磷肥补充满
     * @return
     */
    private boolean calculateBeipiaoPandK() {
    	//下面开始检查是否符合北票的蔬菜底肥模式
    	if (manure.size()!=3) return false;
    	int indexNPK=-1;		//复合肥位置
    	int indexNP=-1;			//氮磷肥位置
    	int indexP=-1;			//单磷肥位置
    	for (int i = 0; i < manure.size(); i ++) {
    		FertilizerInfo mo = (FertilizerInfo)manure.get(i);
    		if (mo.contain(NutrientType.N) && mo.contain(NutrientType.P)
    				&& mo.contain(NutrientType.K) ) {
    			indexNPK=i;
    		}else if (mo.contain(NutrientType.N) && mo.contain(NutrientType.P) && !mo.contain(NutrientType.K)){
    			indexNP=i;
    		}else if (mo.contain(NutrientType.P)){
    			indexP=i;
    		}
    	}
    	if (indexNPK<0 ||indexNP<0 || indexP<0) return false;//有一种肥料未找到就不满足条件
    	
    	//下面开始计算
    	int NPK_MIN = 20;		//复合肥下限
    	int NPK_MAX = 90;		//复合肥上限
    	int NP_MIN = 10;		//氮磷肥下限
    	int NP_MAX = 20;		//氮磷肥上限
    	int P_MIN = 0;			//单磷肥下限
    	int P_MAX = 0;			//单磷肥上限	
    	
    	FertilizerInfo moNPK=(FertilizerInfo)manure.get(indexNPK);		//获得三种肥料
    	FertilizerInfo moNP=(FertilizerInfo)manure.get(indexNP);
    	FertilizerInfo moP=(FertilizerInfo)manure.get(indexP);
    
    	double fiNPK=NPK_MIN;		//各肥料用量，初始值全部采取下限值
    	double fiNP=NP_MIN;
    	double fiP=P_MIN;
    	
    	double p=fiNPK*moNPK.getPercent(NutrientType.P)/100;
    	p=p+fiNP*moNP.getPercent(NutrientType.P)/100;
    	p=p+fiP*moP.getPercent(NutrientType.P)/100;
    	
    	if (p<dosage[NutrientType.P]){
    		//剩余的缺磷元素，用单质肥补充。
    		p=dosage[NutrientType.P]-p;		
    		double addp = moP.calculte(NutrientType.P, p);		//需要单质补充肥料量		
    		if ((P_MAX-P_MIN)>=addp){
    			//如果需要被补充的磷元素在单质肥区间内可以补充，就全部用单质肥补充
    			fiP=fiP+addp;
    		}else{
    			fiP=P_MAX;	//如果不够补充到最大值，
    			//剩余补磷磷去除新P_MAX到P_MIN之间的含磷量
    			p=p-(P_MAX-P_MIN)*moP.getPercent(NutrientType.P)/100;
    			addp = moNP.calculte(NutrientType.P, p);		//需要NP肥补充磷用量	
    			if ((NP_MAX-NP_MIN)>=addp){
        			//如果需要被补充的磷元素在单质肥区间内可以补充，就全部用单质肥补充
        			fiNP=fiNP+addp;
    			}else{
    				fiNP=NP_MAX;	//如果不够补充到最大值，
    				//剩余补磷量去除新MAX到MIN之间的含磷量
        			p=p-(NP_MAX-NP_MIN)*moNP.getPercent(NutrientType.P)/100;
        			addp = moNPK.calculte(NutrientType.P, p);	//需要NPK肥补充磷用量
        			if ((NPK_MAX-NPK_MIN)>=addp){
        				fiNPK=fiNPK+addp;
        			}else{
        				fiNPK=NPK_MAX;	//如果不够补充到最大值，
        				//剩余补磷量去除新MAX到MIN之间的含磷量
            			p=p-(NPK_MAX-NPK_MIN)*moNPK.getPercent(NutrientType.P)/100;
            			addp = moP.calculte(NutrientType.P, p);	//需要NPK肥补充磷用量
            			fiP=fiP+addp;
        			}
    			}
    		}
    	}
    	
    	//已经计算出各种肥料的用量了，
    	dosage[NutrientType.P]=0.0;
    	moNPK.setDosage(fiNPK);		//NPK用量
    	double k = fiNPK * moNPK.getPercent(NutrientType.K) / 100;
    	this.dosage[NutrientType.K] = this.dosage[NutrientType.K] - k;
    	double n = fiNPK * moNPK.getPercent(NutrientType.N) / 100;
    	this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
    	
    	moNP.setDosage(fiNP);		//NP用量
    	n = fiNP * moNP.getPercent(NutrientType.N) / 100;
    	this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
    	moP.setDosage(fiP);		//NP用量

    	manure.set(indexNPK, moNPK);
    	manure.set(indexNP, moNP);
    	manure.set(indexP, moP);
    	
		return true;
	}


	/**
     * 计算 P
     */
    private void calculatePhosphorus() {
        for (int i = 0; i < manure.size(); i ++) {
        	FertilizerInfo mo = (FertilizerInfo)manure.get(i);
            //如果该肥料用量已经计算过则跳过，不再重复计算
            if(mo.isCalculated()) continue;
            //如果肥料中不含 P2O5则跳过
            if(!mo.contain(NutrientType.P)) continue;
            //mo=getFertilizerInfo(mo.getFertilizerid());
            //因为有了库里不存在的自动生成配方肥，所以由查找生成新的肥料变为了复制生成新的肥料
            mo=getNewFertilizerInfo(mo);
            
            //计算满足P2O5所需的肥料用量
            double p = mo.calculte(NutrientType.P, dosage[NutrientType.P]);
            dosage[NutrientType.P]=0.0;

            if (mo.contain(NutrientType.N)) {
                //计算肥料中含有的N素量
                double n = p * mo.getPercent(NutrientType.N) / 100;
                //总N素用量减去已含有的N素量，剩余N用其他含氮肥料补齐
                this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
            }

            if (mo.contain(NutrientType.K)) {
                //计算肥料中含有的K2O的量
                double k = p * mo.getPercent(NutrientType.K) / 100;
                //总K2O用量减去已含有的K2O量，剩余K2O用其他含K2O的肥料补齐
                this.dosage[NutrientType.K] = this.dosage[NutrientType.K] - k;
            }
            //2010年07月　马利强根据定兴修改需求，除了专用配方肥以外，
            //复合肥只补充最少量的肥料，其它用单质肥补充。
            if (mo.getDedicated()==1){
            	//如果专用肥，则强制施满
	            if(p<=0.0f){
	            	//在此种方法中增加了setDosage这个方法来使用
	            	mo.setDosage(0.01);		//避免重复计算
	            }else{
	            	mo.setDosage(p);		//避免重复计算
	            }
            }
            FertilizerInfo monew =new FertilizerInfo ();
            //monew.setFertilizername(mo.getFertilizername());
            //monew.setN(mo.getN());
            //monew.setP(mo.getP());
            //monew.setK(mo.getK());
            //monew.setDosage(mo.getDosage());
            //manure.set(i, monew); //为了避免地址传递影响，而新置了值放回数组中
            manure.set(i, mo); //为了避免地址传递影响，而新置了值放回数组中

        }
    }

    private FertilizerInfo getFertilizerInfo(String fertilizerid) {
		// TODO Auto-generated method stub
    	FertilizerService fs=new FertilizerService();
    	FertilizerInfo fi=fs.getFertilizerInfo(fertilizerid);
		return fi;
	}

    private FertilizerInfo getNewFertilizerInfo(FertilizerInfo fer) {
		// TODO Auto-generated method stub
    	
    	FertilizerInfo fi=new FertilizerInfo();
    	
    	fi.setP(fer.getP());
		fi.setK(fer.getK());
		fi.setN(fer.getN());
		fi.setDedicated(fer.getDedicated());
		fi.setFertilizername(fer.getFertilizername());
		fi.setShortname(fer.getShortname());
    	

		return fi;
	}

	/**
     * 计算 K
     */
    private void calculateKalium(){
        for(int i=0;i<manure.size();i++){
        	FertilizerInfo mo = (FertilizerInfo)manure.get(i);
            //如果该肥料用量已经计算过则跳过，不再重复计算
            if(mo.isCalculated()) continue;
            //如果肥料中不含 K2O则跳过
            if(!mo.contain(NutrientType.K)) continue;
            mo=getFertilizerInfo(mo.getFertilizerid());	
            //计算满足K2O所需的肥料用量
            double k = mo.calculte(NutrientType.K, dosage[NutrientType.K]);
            dosage[NutrientType.K]=0.0;

            if (mo.contain(NutrientType.N)) {
                //计算肥料中含有的N素量
                double n = k * mo.getPercent(NutrientType.N) / 100;
                //总N素用量减去已含有的N素量，剩余N用其他含氮肥料补齐
                this.dosage[NutrientType.N] = this.dosage[NutrientType.N] - n;
            }

            if (mo.contain(NutrientType.P)) {
                //计算肥料中含有的P2O5的量
                double p = k * mo.getPercent(NutrientType.P) / 100;
                //总P2O5用量减去已含有的P2O5量，剩余P2O5用其他含P2O5的肥料补齐
                this.dosage[NutrientType.P] = this.dosage[NutrientType.P] - p;
            }
            if (mo.getDedicated()==1){
            	//如果专用肥，则强制施满
	            if(k<=0.0f){
	            	mo.setDosage(0.01);		//避免重复计算
	            }else{
	            	mo.setDosage(k);		//避免重复计算
	            }
            }
            /*
            FertilizerInfo monew =new FertilizerInfo ();
            monew.setFertilizername(mo.getFertilizername());
            monew.setN(mo.getN());
            monew.setP(mo.getP());
            monew.setK(mo.getK());
            monew.setDosage(mo.getDosage());
            manure.set(i, monew); //放回数组中
            */
            manure.set(i, mo); //放回数组中

        }
    }

//    /**
//     * 检索数据库的肥料使用关系 SN_FLSYGXB 查询指定ID方案所有的肥料ID
//     * @param id int
//     */
//    private int[] queryPlansID(int id){
//        DbFlsygxDAO dao=DAOConnection.getFactory().createFlsygxDAO();
//
//        int[] ids=null;
//        try {
//            Collection objs = dao.selectFlsygx("select * from SN_FLSYGXB where SFFA=" + id + "");
//            if(objs!=null && objs.size()>0){
//                ids=new int[objs.size()];
//                DbFlsygxVO[] vo=new DbFlsygxVO[objs.size()];
//                vo=(DbFlsygxVO[])objs.toArray(vo);
//                for(int i=0;i<vo.length;i++){
//                    ids[i]=vo[i].getFlbh();
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return ids;
//    }
    
    /**
     * 
     * 肥料排序 将数组中肥料按养分种类数从小到大进行排序
     * @return
     */
    private void manuressort() {
    	
    	Object temp;
        for (int i = 0; i < manure.size(); i++) 
        { 
        	for(int j = 0; j < manure.size() - i - 1; j++)
        	{
        	if(((FertilizerInfo)manure.get(j)).getNutrientCount()>((FertilizerInfo)manure.get(j+1)).getNutrientCount())
        	{ 
        	  temp=manure.get(j);
        	  manure.set(j, manure.get(j+1));
        	  manure.set(j+1, temp);
        	} 
           }
       }
    }
}
