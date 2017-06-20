package com.snsoft.ctpf.beans;
//TODO 请相关包命名空间


/**
 * 代码由神农  sn-framework 平台代码生成器，在2012-02-03 11:27:32生成  
 * 表:sn_crop 的DTO对象
 * 描述:作物养分用量信息表
 */
public class CropNutrientInfo{

		/**
		 * 氮总需用量
		 */
		private double N;

		/**
		 * 磷总需用量
		 */
		private double P;

		/**
		 * 钾总需用量
		 */
		private double K;

		/**
		 * 需肥量在底肥中的比例
		 */
		private int Nbase;

		/**
		 * 需肥量在底肥中的比例
		 */
		private int Pbase;

		/**
		 * 需肥量在底肥中的比例
		 */
		private int Kbase;

		/**
		 * 底肥总用量
		 */
		private double Nb;

		/**
		 * 底肥总用量
		 */
		private double Pb;

		/**
		 * 底肥总用量
		 */
		private double Kb;

		
		/**
		 * 追肥总用量
		 */
		private double Nt;

		/**
		 * 追肥总用量
		 */
		private double Pt;

		/**
		 * 追肥总用量
		 */
		private double Kt;

		public CropNutrientInfo(){ 
		}



		
		
		/**
		 * 属性:N getter器
		 */
		public double getN(){
			return this.N;
		}
		
		
		/**
		 * 属性:N setter器
		 */
		public void setN(double val){
			this.N = val;
		}
		
		
		/**
		 * 属性:P getter器
		 */
		public double getP(){
			return this.P;
		}
		
		
		/**
		 * 属性:P setter器
		 */
		public void setP(double val){
			this.P = val;
		}
		
		
		/**
		 * 属性:K getter器
		 */
		public double getK(){
			return this.K;
		}
		
		
		/**
		 * 属性:K setter器
		 */
		public void setK(double val){
			this.K = val;
		}
		
		
		/**
		 * 属性:Nbase getter器
		 */
		public int getNbase(){
			return this.Nbase;
		}
		
		
		/**
		 * 属性:Nbase setter器
		 */
		public void setNbase(int val){
			this.Nbase = val;
		}
		
		
		/**
		 * 属性:Pbase getter器
		 */
		public int getPbase(){
			return this.Pbase;
		}
		
		
		/**
		 * 属性:Pbase setter器
		 */
		public void setPbase(int val){
			this.Pbase = val;
		}
		
		
		/**
		 * 属性:Kbase getter器
		 */
		public int getKbase(){
			return this.Kbase;
		}
		
		
		/**
		 * 属性:Kbase setter器
		 */
		public void setKbase(int val){
			this.Kbase = val;
		}

		/**
		 * @return the nb
		 */
		public double getNb() {
			return N*Nbase/100;
		}

		/**
		 * @param nb the nb to set
		 */
		public void setNb(double nb) {
			Nb = nb;
		}

		/**
		 * @return the pb
		 */
		public double getPb() {
			return P*Pbase/100;
		}

		/**
		 * @param pb the pb to set
		 */
		public void setPb(double pb) {
			Pb = pb;
		}


		/**
		 * @return the kb
		 */
		public double getKb() {
			return K*Kbase/100;
		}


		/**
		 * @param kb the kb to set
		 */
		public void setKb(double kb) {
			Kb = kb;
		}





		/**
		 * @return the nt
		 */
		public double getNt() {
			return this.N-this.getNb();
		}





		/**
		 * @return the pt
		 */
		public double getPt() {
			return this.P-this.getPb();
		}





		/**
		 * @return the kt
		 */
		public double getKt() {
			return this.K-this.getKb();
		}
		
		
		
	}
