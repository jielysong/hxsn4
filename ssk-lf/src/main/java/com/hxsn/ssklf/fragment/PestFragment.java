package com.hxsn.ssklf.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxsn.ssklf.R;
import com.hxsn.ssklf.model.PestProperty;

import java.util.ArrayList;
import java.util.List;

/**
 */
@SuppressLint("ValidFragment")
public class PestFragment extends Fragment implements View.OnClickListener{

    /**
     * 1黄瓜病虫害图片
     */
    private final static int[] pestIntImgs1 = {R.mipmap.bg_cucumber1_cudaob,R.mipmap.bg_cucumber2_likub,R.mipmap.bg_cucumber3_tanjub,
            R.mipmap.bg_cucumber4_genfubing,R.mipmap.bg_cucumber5_kuweibing,R.mipmap.bg_cucumber6_shuangmeib,R.mipmap.bg_cucumber7_jiaobanbing,
            R.mipmap.bg_cucumber8_heixingbing,R.mipmap.bg_cucumber9_huimeibing,R.mipmap.bg_cucumber10_babanbing,R.mipmap.bg_cucumber11_baifenbing,
            R.mipmap.bg_cucumber12_xianchongb,R.mipmap.bg_cucumber13_mankubing,R.mipmap.bg_cucumber14_baifenshi,R.mipmap.bg_cucumber15_guaya,
            R.mipmap.bg_cucumber16_lougu,R.mipmap.bg_cucumber17_yanfenshi,R.mipmap.bg_cucumber18_banqianying,R.mipmap.bg_cucumber19_banqianzma,
            R.mipmap.bg_cucumber20_huangshougua,R.mipmap.bg_cucumber21_biingdub};

    /**
     * 2番茄病虫害图片
     */
    private final static int[] pestIntImgs2 = {R.mipmap.bg_tomato1_genfu,R.mipmap.bg_tomato2_zaoyibing,R.mipmap.bg_tomato3_cudao,
            R.mipmap.bg_tomato4_liku,R.mipmap.bg_tomato5_tancu,R.mipmap.bg_tomato6_huiyeban,R.mipmap.bg_tomato7_hhuaquye,
            R.mipmap.bg_tomato8_huimeibing,R.mipmap.bg_tomato9_wanyib,R.mipmap.bg_tomato10_kuiyangb,R.mipmap.bg_tomato11_yanmeib,
            R.mipmap.bg_tomato12_kuweib,R.mipmap.bg_tomato13_xianchongb,R.mipmap.bg_tomato14_qingkub,R.mipmap.bg_tomato15_huaisi,
            R.mipmap.bg_tomato16_baifenshi,R.mipmap.bg_tomato17_yach,R.mipmap.bg_tomato18_lougu,R.mipmap.bg_tomato19_lihuajima19,
            R.mipmap.bg_tomato20_banqinay,R.mipmap.bg_tomato21_mianlingch,R.mipmap.bg_tomato22_yewene,R.mipmap.bg_tomato23_yanqingch,
            R.mipmap.bg_tomato24_dilaohu,R.mipmap.bg_tomato25_chahuangma,R.mipmap.bg_tomato26_yanfenshi};

    /**
     * 3莲藕病虫害图片
     */
    private final static int[] pestIntImgs3 = {R.mipmap.bg_lotus1_tancu,R.mipmap.bg_lotus2_yeban,R.mipmap.bg_lotus3_fubaib,
            R.mipmap.bg_lotus4_bingdub,R.mipmap.bg_lotus5_heban,R.mipmap.bg_lotus6_chie,R.mipmap.bg_lotus7_jinhuach,
            R.mipmap.bg_tomato22_yewene,R.mipmap.bg_tomato17_yach};

    private final static String[] pestNames1 = {"黄瓜猝倒病","黄瓜立枯病","黄瓜炭疽病","黄瓜根腐病","黄瓜枯萎病","黄瓜霜霉病","黄瓜细菌性角斑病",
            "黄瓜黑星病","黄瓜灰霉病","黄瓜靶斑病","黄瓜白粉病","黄瓜线虫病","黄瓜蔓枯病","黄瓜白粉虱","黄瓜瓜蚜","蝼蛄","烟粉虱","美洲斑潜蝇",
            "棕榈蓟马","黄守瓜","黄瓜病毒病"};
    private final static String[] pestNames2 = {"番茄褐色根腐","番茄早疫病","番茄猝倒病","番茄立枯病","番茄炭疽病","番茄灰叶斑病","番茄黄化曲叶",
            "番茄灰霉病","番茄晚疫病","番茄细菌性溃疡病","番茄叶霉病","番茄枯萎病","番茄线虫病","番茄青枯病","番茄细菌性随部坏死","番茄白粉虱","番茄蚜虫","蝼蛄",
            "番茄丽花蓟马","番茄斑潜蝇","番茄棉铃虫","番茄斜纹夜蛾","番茄烟青虫","小地老虎","番茄茶黄螨","番茄烟粉虱"};
    private final static String[] pestNames3 = {"莲藕炭疽病","叶斑病","莲藕腐败病","莲藕病毒病","莲藕棒孢褐斑病","刺蛾","食根金花虫",
            "斜纹夜蛾","蚜虫"};
    /**
     * 1黄瓜物候期
     */
    private final static String[] phenophases1 = {"育苗期","定植期","幼苗期","开花期","结果期"};

    /**
     * 2番茄普通物候期
     */
    private final static String[] phenophases20 = {"育苗期","定植期","幼苗期","开花坐果期","结果期"};

    /**
     * 20番茄秋冬茬物候期
     */
    private final static String[] phenophases21 = {"育苗期","定植期","幼苗期","开花坐果期","结果期","结果后期"};

    /**
     * 3莲藕物候期
     */
    private final static String[] phenophases3 = {"种植期","幼苗期","成株期","结藕期"};

    /**
     * 所有黄瓜病虫害
     */
    private final static PestProperty[] pestCucumberes = {
            new PestProperty(0,0,0,0,true),new PestProperty(0,0,0,1,true),
            new PestProperty(0,0,0,2,true),new PestProperty(0,0,0,13,true),
            new PestProperty(0,0,0,14,false),new PestProperty(0,0,0,15,true),//黄瓜，春茬，育苗期

            new PestProperty(0,0,1,3,true),new PestProperty(0,0,1,1,true),
            new PestProperty(0,0,1,13,false),new PestProperty(0,0,1,14,true),//黄瓜，春茬，定植期

            new PestProperty(0,0,2,4,true),new PestProperty(0,0,2,5,true),
            new PestProperty(0,0,2,2,false),new PestProperty(0,0,2,6,true),
            new PestProperty(0,0,2,14,true),new PestProperty(0,0,2,15,true),
            new PestProperty(0,0,2,16,true),                                //黄瓜，春茬，幼苗期

            new PestProperty(0,0,3,7,true),new PestProperty(0,0,3,8,true),
            new PestProperty(0,0,3,4,true),new PestProperty(0,0,3,6,true),
            new PestProperty(0,0,3,13,true),new PestProperty(0,0,3,17,true),
            new PestProperty(0,0,3,16,true),new PestProperty(0,0,3,18,false),//黄瓜，春茬，开花坐果期

            new PestProperty(0,0,4,9,true),new PestProperty(0,0,4,10,true),
            new PestProperty(0,0,4,4,true),new PestProperty(0,0,4,6,true),
            new PestProperty(0,0,4,11,false),new PestProperty(0,0,4,13,true),
            new PestProperty(0,0,4,14,true),new PestProperty(0,0,4,19,false),
            new PestProperty(0,0,4,17,true),new PestProperty(0,0,4,18,true), //黄瓜，春茬，结果期

            new PestProperty(0,1,0,0,true),new PestProperty(0,1,0,1,true),
            new PestProperty(0,1,0,2,true),new PestProperty(0,1,0,13,true),
            new PestProperty(0,1,0,14,false),new PestProperty(0,1,0,15,true),//黄瓜，秋冬茬，育苗期

            new PestProperty(0,1,1,3,true),new PestProperty(0,1,1,1,true),
            new PestProperty(0,1,1,5,true),new PestProperty(0,1,1,13,false),
            new PestProperty(0,1,1,14,true),new PestProperty(0,1,1,15,true),//黄瓜，秋冬茬，定植期

            new PestProperty(0,1,2,3,true),new PestProperty(0,1,2,4,true),
            new PestProperty(0,1,2,1,false),new PestProperty(0,1,2,5,true),
            new PestProperty(0,1,2,13,true),new PestProperty(0,1,2,15,true),
            new PestProperty(0,1,2,16,true),                                //黄瓜，秋冬茬，幼苗期

            new PestProperty(0,1,3,10,true),new PestProperty(0,1,3,8,true),
            new PestProperty(0,1,3,6,true),new PestProperty(0,1,3,13,true),
            new PestProperty(0,1,3,17,true),new PestProperty(0,1,3,16,true),
            new PestProperty(0,1,3,18,false),                                 //黄瓜，秋冬茬，开花坐果期

            new PestProperty(0,1,4,9,true),new PestProperty(0,1,4,20,false),
            new PestProperty(0,1,4,7,false),new PestProperty(0,1,4,4,true),
            new PestProperty(0,1,4,12,true),new PestProperty(0,1,4,6,true),
            new PestProperty(0,1,4,13,true),new PestProperty(0,1,4,14,true),
            new PestProperty(0,1,4,19,false),new PestProperty(0,1,4,7,true),
            new PestProperty(0,1,4,18,true),                                //黄瓜，秋冬茬，结果期

            new PestProperty(0,2,0,3,false),new PestProperty(0,2,0,1,true),
            new PestProperty(0,2,0,5,false),new PestProperty(0,2,0,2,true),
            new PestProperty(0,2,0,13,true),new PestProperty(0,2,0,14,true),
            new PestProperty(0,2,0,15,true),new PestProperty(0,2,0,18,false),//黄瓜，越冬茶，育苗期

            new PestProperty(0,2,1,10,true),new PestProperty(0,2,1,3,true),
            new PestProperty(0,2,1,7,false),new PestProperty(0,2,1,5,false),
            new PestProperty(0,2,1,2,true),new PestProperty(0,2,1,13,false),
            new PestProperty(0,2,1,14,true),new PestProperty(0,2,1,15,true),//黄瓜，越冬茶，定植期

            new PestProperty(0,2,2,3,true),new PestProperty(0,2,2,4,true),
            new PestProperty(0,2,2,12,false),new PestProperty(0,2,2,5,true),
            new PestProperty(0,2,2,6,true),new PestProperty(0,2,2,11,false),
            new PestProperty(0,2,2,13,true),new PestProperty(0,2,2,14,true),
            new PestProperty(0,2,2,15,true),                                //黄瓜，越冬茶，幼苗期

            new PestProperty(0,2,3,10,true),new PestProperty(0,2,3,7,false),
            new PestProperty(0,2,3,8,true),new PestProperty(0,2,3,4,true),
            new PestProperty(0,2,3,5,false),new PestProperty(0,2,3,2,false),
            new PestProperty(0,2,3,6,true),new PestProperty(0,2,3,13,true),
            new PestProperty(0,2,3,14,true),new PestProperty(0,2,3,17,true),//黄瓜，越冬茶，开花坐果期

            new PestProperty(0,2,4,9,true),new PestProperty(0,2,4,10,true),
            new PestProperty(0,2,4,12,false),new PestProperty(0,2,4,5,true),
            new PestProperty(0,2,4,6,true),new PestProperty(0,2,4,11,false),
            new PestProperty(0,2,4,13,true),new PestProperty(0,2,4,14,true),
            new PestProperty(0,2,4,17,true),new PestProperty(0,2,4,18,true),//黄瓜，越冬茶，结果期
    };

    /**
     * 所有番茄病虫害
     */
    private final static PestProperty[] pestTomatoes = {
            new PestProperty(1,0,0,2,false),new PestProperty(1,0,0,3,true),
            new PestProperty(1,0,0,15,false),new PestProperty(1,0,0,16,true),//番茄，春茬，育苗期

            new PestProperty(1,0,1,0,true),new PestProperty(1,0,1,3,true),
            new PestProperty(1,0,1,1,true),new PestProperty(1,0,1,15,false),
            new PestProperty(1,0,1,16,true),new PestProperty(1,0,1,17,false),//番茄，春茬，定植期

            new PestProperty(1,0,2,0,true),new PestProperty(1,0,2,6,true),
            new PestProperty(1,0,2,1,true),new PestProperty(1,0,2,15,false),
            new PestProperty(1,0,2,16,true),new PestProperty(1,0,2,17,true),//番茄，春茬，幼苗期

            new PestProperty(1,0,3,0,false),new PestProperty(1,0,3,6,true),
            new PestProperty(1,0,3,7,true),new PestProperty(1,0,3,8,false),
            new PestProperty(1,0,3,10,true),new PestProperty(1,0,3,1,false),
            new PestProperty(1,0,3,15,true),new PestProperty(1,0,3,18,false),
            new PestProperty(1,0,3,16,true),                                //番茄，春茬，开花坐果期

            new PestProperty(1,0,4,7,true),new PestProperty(1,0,4,8,false),
            new PestProperty(1,0,4,9,true),new PestProperty(1,0,4,14,true),
            new PestProperty(1,0,4,10,true),new PestProperty(1,0,4,15,true),
            new PestProperty(1,0,4,19,false),new PestProperty(1,0,4,24,false),
            new PestProperty(1,0,4,20,false),new PestProperty(1,0,4,21,true),
            new PestProperty(1,0,4,16,false),new PestProperty(1,0,4,22,true),//番茄，春茬，结果期

            new PestProperty(1,1,0,2,false),new PestProperty(1,1,0,6,false),
            new PestProperty(1,1,0,11,true),new PestProperty(1,1,0,3,true),
            new PestProperty(1,1,0,1,true),new PestProperty(1,1,0,15,false),
            new PestProperty(1,1,0,20,false),new PestProperty(1,1,0,21,false),
            new PestProperty(1,1,0,16,true),new PestProperty(1,1,0,25,false),
            new PestProperty(1,1,0,22,false),new PestProperty(1,1,0,17,true),
            new PestProperty(1,1,0,23,true),                                 //番茄，冬春茬，育苗期

            new PestProperty(1,1,1,0,true),new PestProperty(1,1,1,6,false),
            new PestProperty(1,1,1,3,true),new PestProperty(1,1,1,1,true),
            new PestProperty(1,1,1,16,true),new PestProperty(1,1,1,25,true),
            new PestProperty(1,1,1,17,true),new PestProperty(1,1,1,23,true),//番茄，冬春茬，定植期

            new PestProperty(1,1,2,0,true),new PestProperty(1,1,2,1,true),
            new PestProperty(1,1,2,15,false),new PestProperty(1,1,2,16,true),
            new PestProperty(1,1,2,25,true),                                //番茄，冬春茬，幼苗期

            new PestProperty(1,1,3,7,true),new PestProperty(1,1,3,8,false),
            new PestProperty(1,1,3,9,false),new PestProperty(1,1,3,10,true),
            new PestProperty(1,1,3,15,false),new PestProperty(1,1,3,16,false),//番茄，冬春茬，开花坐果期

            new PestProperty(1,1,4,7,true),new PestProperty(1,1,4,13,true),
            new PestProperty(1,1,4,8,false),new PestProperty(1,1,4,9,true),
            new PestProperty(1,1,4,12,false),new PestProperty(1,1,4,10,true),
            new PestProperty(1,1,4,1,false),new PestProperty(1,1,4,15,true),
            new PestProperty(1,1,4,19,true),new PestProperty(1,1,4,24,false),
            new PestProperty(1,1,4,18,false),new PestProperty(1,1,4,20,false),
            new PestProperty(1,1,4,21,true),new PestProperty(1,1,4,16,false),//番茄，冬春茬，结果期
            new PestProperty(1,1,4,22,true),

            new PestProperty(1,2,0,2,false),new PestProperty(1,2,0,6,false),
            new PestProperty(1,2,0,11,true),new PestProperty(1,2,0,3,true),
            new PestProperty(1,2,0,1,true),new PestProperty(1,2,0,15,false),
            new PestProperty(1,2,0,24,true),new PestProperty(1,2,0,18,false),
            new PestProperty(1,2,0,20,false),new PestProperty(1,2,0,16,true),
            new PestProperty(1,2,0,15,true),                                 //番茄，秋冬茬，育苗期

            new PestProperty(1,2,1,0,true),new PestProperty(1,2,1,6,false),
            new PestProperty(1,2,1,5,false),new PestProperty(1,2,1,3,true),
            new PestProperty(1,2,1,1,true),new PestProperty(1,2,1,15,false),
            new PestProperty(1,2,1,24,true),new PestProperty(1,2,1,18,false),
            new PestProperty(1,2,1,16,true),new PestProperty(1,2,1,25,false),
            new PestProperty(1,2,1,17,false),new PestProperty(1,2,1,23,true),//番茄，秋冬茬，定植期

            new PestProperty(1,2,2,0,true),new PestProperty(1,2,2,11,true),
            new PestProperty(1,2,2,1,true),new PestProperty(1,2,2,15,false),
            new PestProperty(1,2,2,19,true),new PestProperty(1,2,2,24,true),
            new PestProperty(1,2,2,20,false),new PestProperty(1,2,2,16,true),
            new PestProperty(1,2,2,25,false),                                //番茄，秋冬茬，幼苗期

            new PestProperty(1,2,3,7,true),new PestProperty(1,2,3,8,false),
            new PestProperty(1,2,3,9,false),new PestProperty(1,2,3,10,true),
            new PestProperty(1,2,3,15,false),new PestProperty(1,2,3,19,true),
            new PestProperty(1,2,3,21,false),new PestProperty(1,2,3,16,true),
            new PestProperty(1,2,3,22,true),                                 //番茄，秋冬茬，开花坐果期

            new PestProperty(1,2,4,0,false),new PestProperty(1,2,4,7,true),
            new PestProperty(1,2,4,9,true),new PestProperty(1,2,4,10,true),
            new PestProperty(1,2,4,1,false),new PestProperty(1,2,4,15,true),
            new PestProperty(1,2,4,24,true),new PestProperty(1,2,4,21,false),
            new PestProperty(1,2,4,16,true),new PestProperty(1,2,4,22,true),//番茄，秋冬茬，结果期

            new PestProperty(1,2,5,11,true),new PestProperty(1,2,5,13,false),
            new PestProperty(1,2,5,9,true),new PestProperty(1,2,5,14,true),
            new PestProperty(1,2,5,15,false),new PestProperty(1,2,5,19,true),
            new PestProperty(1,2,5,21,false),new PestProperty(1,2,5,16,true),
            new PestProperty(1,2,5,22,true)                                  //番茄，秋冬茬，结果后期
    };

    private final static PestProperty[] pestLotuses = {
            new PestProperty(2,0,0,4,true),new PestProperty(2,0,0,0,true),
            new PestProperty(2,0,0,1,true),new PestProperty(2,0,0,5,true),
            new PestProperty(2,0,0,7,true),new PestProperty(2,0,0,8,true),//莲藕，春茬，种植期

            new PestProperty(2,0,1,4,true),new PestProperty(2,0,1,2,true),
            new PestProperty(2,0,1,0,true),new PestProperty(2,0,1,1,true),
            new PestProperty(2,0,1,5,true),new PestProperty(2,0,1,7,true),
            new PestProperty(2,0,1,8,true),                             //莲藕，春茬，幼苗期

            new PestProperty(2,0,2,4,true),new PestProperty(2,0,2,2,true),
            new PestProperty(2,0,2,0,true),new PestProperty(2,0,2,1,false),
            new PestProperty(2,0,2,5,true),new PestProperty(2,0,2,7,true),
            new PestProperty(2,0,2,8,true),                             //莲藕，春茬，成株期

            new PestProperty(2,0,3,3,true),new PestProperty(2,0,3,4,true),
            new PestProperty(2,0,3,2,true),new PestProperty(2,0,3,0,true),
            new PestProperty(2,0,3,1,true),new PestProperty(2,0,3,5,true),
            new PestProperty(2,0,3,6,true),new PestProperty(2,0,3,7,true),
            new PestProperty(2,0,3,8,true),                             //莲藕，春茬，结藕期
    };

    private int mode;  //作物选择    0,黄瓜  1，番茄 2，莲藕
    private int mode1=0x09; //物候期选择  0x00001001  低3位标示季节茬 001-春茬   010-冬春茬或秋冬茬 100-秋冬茬或越冬茬
    private int phenophaseCnt=5;//物候期个数

    /**
     * 病虫害列表结果，点击物候期显示病虫害列表
     */
    private List<PestProperty> pestArray;
    private TextView txtCrop1,txtCrop2,txtCrop3;
    private ImageView imageCrop;

    public TextView txtSeason1,txtSeason2,txtSeason3;
    public TextView txtPhenophase1,txtPhenophase2,txtPhenophase3,txtPhenophase4,txtPhenophase5,txtPhenophase6;
    private PestAdapter adapter;


    public PestFragment() {
    }

    /**
     * @return A new instance of fragment PestFragment.
     */
    public static PestFragment newInstance() {
        PestFragment fragment = new PestFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pest, container, false);
        addView(view);
        addListener();

        refreshPhenoPhase();
        refreshPest();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.RecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PestAdapter();
        
        recyclerView.setAdapter(adapter);
        
        return view;
    }

    /**
     * 添加视图
     * @param view
     */
    private void addView(View view) {
        txtCrop1 = (TextView)view.findViewById(R.id.txt_crop1);
        txtCrop2 = (TextView)view.findViewById(R.id.txt_crop2);
        txtCrop3 = (TextView)view.findViewById(R.id.txt_crop3);
        imageCrop = (ImageView) view.findViewById(R.id.img_crop);

        txtSeason1 = (TextView) view.findViewById(R.id.txt_season1);
        txtSeason2 = (TextView) view.findViewById(R.id.txt_season2);
        txtSeason3 = (TextView) view.findViewById(R.id.txt_season3);

        txtPhenophase1 = (TextView) view.findViewById(R.id.txt_phenophase1);
        txtPhenophase2 = (TextView) view.findViewById(R.id.txt_phenophase2);
        txtPhenophase3 = (TextView) view.findViewById(R.id.txt_phenophase3);
        txtPhenophase4 = (TextView) view.findViewById(R.id.txt_phenophase4);
        txtPhenophase5 = (TextView) view.findViewById(R.id.txt_phenophase5);
        txtPhenophase6 = (TextView) view.findViewById(R.id.txt_phenophase6);
    }

    /**
     * 添加视图监听器
     */
    private void addListener() {
        txtCrop1.setOnClickListener(this);
        txtCrop2.setOnClickListener(this);
        txtCrop3.setOnClickListener(this);

        txtSeason1.setOnClickListener(this);
        txtSeason2.setOnClickListener(this);
        txtSeason3.setOnClickListener(this);

        txtPhenophase1.setOnClickListener(this);
        txtPhenophase2.setOnClickListener(this);
        txtPhenophase3.setOnClickListener(this);
        txtPhenophase4.setOnClickListener(this);
        txtPhenophase5.setOnClickListener(this);
        txtPhenophase6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.txt_crop1://选择作物1
                if(mode != 0){
                    cleanTextCrop();
                    mode = 0;
                    mode1 = 0x09;
                    txtCrop1.setTextColor(getResources().getColor(R.color.white));
                    txtCrop1.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                    imageCrop.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_cucumber));
                }
                break;
            case R.id.txt_crop2://选择作物2
                if(mode != 1){
                    cleanTextCrop();
                    mode = 1;
                    mode1 = 0x09;
                    txtCrop2.setTextColor(getResources().getColor(R.color.white));
                    txtCrop2.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                    imageCrop.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_tomato));
                }
                break;
            case R.id.txt_crop3://选择作物3
                if(mode != 2){
                    cleanTextCrop();
                    mode = 2;
                    mode1 = 0x09;
                    txtCrop3.setTextColor(getResources().getColor(R.color.white));
                    txtCrop3.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                    imageCrop.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_lotus));
                }
                break;
            case R.id.txt_season1://选择季节茬1
                if((mode1 & 0x01) != 0x01){
                    mode1 =0;
                    mode1 |= 0x01;
                    mode1 &= 0x07;
                    mode1 |= 0x08;
                    phenophaseCnt = 5;
                }
                break;
            case R.id.txt_season2://选择季节茬2
                if((mode1 & 0x02) != 0x02){
                    mode1 =0;
                    mode1 |= 0x02;
                    mode1 &= 0x07;
                    mode1 |= 0x08;
                    phenophaseCnt = 5;
                    if(mode == 1){//番茄下 点击第三茬出现6个物候期
                        phenophaseCnt = 6;
                    }
                }
                break;
            case R.id.txt_season3://选择季节茬3
                if((mode1 & 0x04) != 0x04){
                    mode1 =0;
                    mode1 |= 0x04;
                    mode1 &= 0x07;
                    mode1 |= 0x08;
                    phenophaseCnt = 4;
                }
                break;
            case R.id.txt_phenophase1://选择物候期1
                if((mode1 & 0x08) != 0x08){
                    mode1 &= 0x07;
                    mode1 |= 0x08;
                }
                break;
            case R.id.txt_phenophase2://选择物候期2
                if((mode1 & 0x10) != 0x10){
                    mode1 &= 0x07;
                    mode1 |= 0x10;
                }
                break;
            case R.id.txt_phenophase3://选择物候期3
                if((mode1 & 0x20) != 0x20){
                    mode1 &= 0x07;
                    mode1 |= 0x20;
                }
                break;
            case R.id.txt_phenophase4://选择物候期4
                if((mode1 & 0x40) != 0x40){
                    mode1 &= 0x07;
                    mode1 |= 0x40;
                }
                break;
            case R.id.txt_phenophase5://选择物候期5
                if((mode1 & 0x80) != 0x80){
                    mode1 &= 0x07;
                    mode1 |= 0x80;
                }
                break;
            case R.id.txt_phenophase6://选择物候期6
                if((mode1 & 0x100) != 0x100){
                    mode1 &= 0x07;
                    mode1 |= 0x100;
                }
                break;
        }
        refreshPhenoPhase();
        refreshPest();
        adapter.notifyDataSetChanged();
    }

    /**
     * 清理顶部菜单视图
     */
    private void cleanTextCrop(){
        txtCrop1.setTextColor(getResources().getColor(R.color.black));
        txtCrop1.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
        txtCrop2.setTextColor(getResources().getColor(R.color.black));
        txtCrop2.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
        txtCrop3.setTextColor(getResources().getColor(R.color.black));
        txtCrop3.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
    }

    /**
     * 刷新病虫害列表
     */
    private void refreshPest(){
        if(mode == 2){//莲藕
            if((mode1 & 0x08) == 0x08){//莲藕-春茬-种植期
                pestArray = getPestArray(2,0,0);
            }else if((mode1 & 0x10) == 0x10){//莲藕-春茬-幼苗期
                pestArray = getPestArray(2,0,1);
            }else if((mode1 & 0x20) == 0x20){//莲藕-春茬-成株期
                pestArray = getPestArray(2,0,2);
            }else if((mode1 & 0x40) == 0x40){//莲藕-春茬-结藕期
                pestArray = getPestArray(2,0,3);
            }
        }else {//黄瓜or番茄
            if (mode == 0) {//黄瓜
                txtSeason2.setText("秋冬茬");
                txtSeason3.setText("越冬茬");
                if ((mode1 & 0x01) == 0x01) {//春茬
                    if ((mode1 & 0x08) == 0x08) {//黄瓜-春茬-育苗期
                        pestArray = getPestArray(0, 0, 0);
                    } else if ((mode1 & 0x10) == 0x10) {//黄瓜-春茬-定植期
                        pestArray = getPestArray(0, 0, 1);
                    } else if ((mode1 & 0x20) == 0x20) {//黄瓜-春茬-幼苗期
                        pestArray = getPestArray(0, 0, 2);
                    } else if ((mode1 & 0x40) == 0x40) {//黄瓜-春茬-开花坐果期
                        pestArray = getPestArray(0, 0, 3);
                    } else if ((mode1 & 0x80) == 0x80) {//黄瓜-春茬-结果期
                        pestArray = getPestArray(0, 0, 4);
                    }
                }

                if ((mode1 & 0x02) == 0x02) {//秋冬茬
                    if ((mode1 & 0x08) == 0x08) {//黄瓜-秋冬茬-育苗期
                        pestArray = getPestArray(0, 1, 0);
                    } else if ((mode1 & 0x10) == 0x10) {//黄瓜-秋冬茬-定植期
                        pestArray = getPestArray(0, 1, 1);
                    } else if ((mode1 & 0x20) == 0x20) {//黄瓜-秋冬茬-幼苗期
                        pestArray = getPestArray(0, 1, 2);
                    } else if ((mode1 & 0x40) == 0x40) {//黄瓜-秋冬茬-开花坐果期
                        pestArray = getPestArray(0, 1, 3);
                    } else if ((mode1 & 0x80) == 0x80) {//黄瓜-秋冬茬-结果期
                        pestArray = getPestArray(0, 1, 4);
                    }
                }

                if ((mode1 & 0x04) == 0x04) {//越冬茬
                    if ((mode1 & 0x08) == 0x08) {//黄瓜-越冬茬-育苗期
                        pestArray = getPestArray(0, 2, 0);
                    } else if ((mode1 & 0x10) == 0x10) {//黄瓜-越冬茬-定植期
                        pestArray = getPestArray(0, 2, 1);
                    } else if ((mode1 & 0x20) == 0x20) {//黄瓜-越冬茬-幼苗期
                        pestArray = getPestArray(0, 2, 2);
                    } else if ((mode1 & 0x40) == 0x40) {//黄瓜-越冬茬-开花坐果期
                        pestArray = getPestArray(0, 2, 3);
                    } else if ((mode1 & 0x80) == 0x80) {//黄瓜-越冬茬-结果期
                        pestArray = getPestArray(0, 2, 4);
                    }
                }
            } else if (mode == 1) {//番茄
                if ((mode1 & 0x01) == 0x01) {//春茬
                    if ((mode1 & 0x08) == 0x08) {//番茄-春茬-育苗期
                        pestArray = getPestArray(1, 0, 0);
                    } else if ((mode1 & 0x10) == 0x10) {//番茄-春茬-定植期
                        pestArray = getPestArray(1, 0, 1);
                    } else if ((mode1 & 0x20) == 0x20) {//番茄-春茬-幼苗期
                        pestArray = getPestArray(1, 0, 2);
                    } else if ((mode1 & 0x40) == 0x40) {//番茄-春茬-开花坐果期
                        pestArray = getPestArray(1, 0, 3);
                    } else if ((mode1 & 0x80) == 0x80) {//番茄-春茬-结果期
                        pestArray = getPestArray(1, 0, 4);
                    }
                }

                if ((mode1 & 0x02) == 0x02) {//冬春茬
                    if ((mode1 & 0x08) == 0x08) {//番茄-冬春茬-育苗期
                        pestArray = getPestArray(1, 1, 0);
                    } else if ((mode1 & 0x10) == 0x10) {//番茄-冬春茬-定植期
                        pestArray = getPestArray(1, 1, 1);
                    } else if ((mode1 & 0x20) == 0x20) {//番茄-冬春茬-幼苗期
                        pestArray = getPestArray(1, 1, 2);
                    } else if ((mode1 & 0x40) == 0x40) {//番茄-冬春茬-开花坐果期
                        pestArray = getPestArray(1, 1, 3);
                    } else if ((mode1 & 0x80) == 0x80) {//番茄-冬春茬-结果期
                        pestArray = getPestArray(1, 1, 4);
                    }
                }

                if ((mode1 & 0x04) == 0x04) {//秋冬茬
                    if ((mode1 & 0x08) == 0x08) {//番茄-越冬茬-育苗期
                        pestArray = getPestArray(1, 2, 0);
                    } else if ((mode1 & 0x10) == 0x10) {//番茄-越冬茬-定植期
                        pestArray = getPestArray(1, 2, 1);
                    } else if ((mode1 & 0x20) == 0x20) {//番茄-越冬茬-幼苗期
                        pestArray = getPestArray(1, 2, 2);
                    } else if ((mode1 & 0x40) == 0x40) {//黄瓜-越冬茬-开花坐果期
                        pestArray = getPestArray(1, 2, 3);
                    } else if ((mode1 & 0x80) == 0x80) {//番茄-越冬茬-结果期
                        pestArray = getPestArray(1, 2, 4);
                    } else if ((mode1 & 0x100) == 0x100) {//番茄-越冬茬-结果后期
                        pestArray = getPestArray(1, 2, 5);
                    }
                }
            }
        }
    }

    /**
     * 刷新物候期显示
     */
    private void refreshPhenoPhase(){
        if(mode == 2) {//莲藕
            txtSeason2.setVisibility(View.GONE);
            txtSeason3.setVisibility(View.GONE);
            phenophaseCnt = 4;
        }else{
            txtSeason2.setVisibility(View.VISIBLE);
            txtSeason3.setVisibility(View.VISIBLE);
            phenophaseCnt = 5;
            if(mode == 0) {//黄瓜
                txtSeason2.setText("秋冬茬");
                txtSeason3.setText("越冬茬");
            }else if(mode == 1){//番茄
                txtSeason2.setText("冬春茬");
                txtSeason3.setText("秋冬茬");
            }

            if((mode1 & 0x02) == 0x02){//第二茬
                txtSeason2.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                txtSeason2.setTextColor(getResources().getColor(R.color.white));
            }else {
                txtSeason2.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
                txtSeason2.setTextColor(getResources().getColor(R.color.black_text_n));
            }

            if((mode1 & 0x04) == 0x04){//第三茬
                if(mode == 1){//番茄
                    phenophaseCnt = 6;
                }
                txtSeason3.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                txtSeason3.setTextColor(getResources().getColor(R.color.white));
            }else {
                txtSeason3.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
                txtSeason3.setTextColor(getResources().getColor(R.color.black_text_n));
            }
        }

        if((mode1 & 0x01) == 0x01){//春茬
            txtSeason1.setBackgroundColor(getResources().getColor(R.color.sky_blue));
            txtSeason1.setTextColor(getResources().getColor(R.color.white));
        }else {
            txtSeason1.setBackgroundColor(getResources().getColor(R.color.gray_light_s));
            txtSeason1.setTextColor(getResources().getColor(R.color.black_text_n));
        }

        if(phenophaseCnt == 4){//物候期为4个
            txtPhenophase5.setVisibility(View.GONE);
            txtPhenophase6.setVisibility(View.GONE);
        }else if(phenophaseCnt == 5){
            txtPhenophase6.setVisibility(View.GONE);
            txtPhenophase5.setVisibility(View.VISIBLE);
            if((mode1 & 0x80) == 0x80){
                txtPhenophase5.setTextColor(getResources().getColor(R.color.sky_blue));
            }else {
                txtPhenophase5.setTextColor(getResources().getColor(R.color.black_text_n));
            }
        }else if(phenophaseCnt == 6){//物候期为6个
            txtPhenophase6.setVisibility(View.VISIBLE);
            txtPhenophase5.setVisibility(View.VISIBLE);
            if((mode1 & 0x80) == 0x80){
                txtPhenophase5.setTextColor(getResources().getColor(R.color.sky_blue));
            }else {
                txtPhenophase5.setTextColor(getResources().getColor(R.color.black_text_n));
            }

            if((mode1 & 0x100) == 0x100){
                txtPhenophase6.setTextColor(getResources().getColor(R.color.sky_blue));
            }else {
                txtPhenophase6.setTextColor(getResources().getColor(R.color.black_text_n));
            }
        }

        if((mode1 & 0x08) == 0x08){
            txtPhenophase1.setTextColor(getResources().getColor(R.color.sky_blue));
        }else {
            txtPhenophase1.setTextColor(getResources().getColor(R.color.black_text_n));
        }
        if((mode1 & 0x10) == 0x10){
            txtPhenophase2.setTextColor(getResources().getColor(R.color.sky_blue));
        }else {
            txtPhenophase2.setTextColor(getResources().getColor(R.color.black_text_n));
        }
        if((mode1 & 0x20) == 0x20){
            txtPhenophase3.setTextColor(getResources().getColor(R.color.sky_blue));
        }else {
            txtPhenophase3.setTextColor(getResources().getColor(R.color.black_text_n));
        }
        if((mode1 & 0x40) == 0x40){
            txtPhenophase4.setTextColor(getResources().getColor(R.color.sky_blue));
        }else {
            txtPhenophase4.setTextColor(getResources().getColor(R.color.black_text_n));
        }

    }

    /**
     *
     * 获取病虫害列表
     * @param cropId 作物下标
     * @param seasonId 季节茬下标
     * @param phenophaseId 物候期下标
     * @return 病虫害列表
     */
    private List<PestProperty> getPestArray(int cropId,int seasonId,int phenophaseId){
        List<PestProperty> pestArray = new ArrayList<>();
        switch (cropId){
            case 0://黄瓜
                for(int i=0; i<pestCucumberes.length;i++){
                    if(pestCucumberes[i].getSeasonIndex() == seasonId && pestCucumberes[i].getPhenophaseIndex() == phenophaseId){
                        pestArray.add(pestCucumberes[i]);
                    }
                }
                break;
            case 1://番茄
                for(int i=0; i<pestTomatoes.length;i++){
                    if(pestTomatoes[i].getSeasonIndex() == seasonId && pestTomatoes[i].getPhenophaseIndex() == phenophaseId){
                        pestArray.add(pestTomatoes[i]);
                    }
                }
                break;
            case 2://莲藕
                for(int i=0; i<pestLotuses.length;i++){
                    if(pestLotuses[i].getSeasonIndex() == seasonId && pestLotuses[i].getPhenophaseIndex() == phenophaseId){
                        pestArray.add(pestLotuses[i]);
                    }
                }
                break;
        }
        return pestArray;
    }

    public class PestAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pest2, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);

            return new PestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            PestViewHolder holder = (PestViewHolder) viewHolder;
            holder.position = i;
            Log.i("PestFragment","position="+i);
            if(i >= pestArray.size()){
                for (PestProperty pest : pestArray) {
                    Log.i("PestFragment", "pest=" + pest.toString());
                }
               return;
            }
            PestProperty pest = pestArray.get(i);

            int rid=R.mipmap.bg_cucumber1_cudaob;// = pestIntImgs1[pest.getIdIndex()];
            String name = "病毒病";
            String phenophase = "种植期";
            switch (pest.getCropId()){
                case 0:
                    rid = pestIntImgs1[pest.getIdIndex()];
                    name = pestNames1[pest.getIdIndex()];
                    phenophase = phenophases1[pest.getPhenophaseIndex()];
                    break;
                case 1:
                    rid = pestIntImgs2[pest.getIdIndex()];
                    name = pestNames2[pest.getIdIndex()];
                    phenophase = phenophases21[pest.getPhenophaseIndex()];
                    break;
                case 2:
                    rid = pestIntImgs3[pest.getIdIndex()];
                    name = pestNames3[pest.getIdIndex()];
                    phenophase = phenophases3[pest.getPhenophaseIndex()];
                    break;
            }
            holder.imageHoldPest.setBackgroundDrawable(getResources().getDrawable(rid));
            holder.txtHoldPestName.setText(name);
            if(pest.isInfect()){
                holder.txtHoldPestResult.setText("易发生");
                holder.txtHoldPestResult.setTextColor(getResources().getColor(R.color.red));
            }else {
                holder.txtHoldPestResult.setText("普通");
                holder.txtHoldPestResult.setTextColor(getResources().getColor(R.color.orange));
            }
            holder.txtHoldPhenophase.setText(phenophase);

            switch (pest.getSeasonIndex()){
                case 0:
                    holder.txtHoldSeason.setText("春茬");
                    break;
                case 1:
                    if(pest.getCropId() == 1){
                        holder.txtHoldSeason.setText("秋冬茬");
                    }else if(pest.getCropId() == 2){
                        holder.txtHoldSeason.setText("越冬茬");
                    }
                    break;
                case 2:
                    if(pest.getCropId() == 1){
                        holder.txtHoldSeason.setText("冬春茬");
                    }else if(pest.getCropId() == 2){
                        holder.txtHoldSeason.setText("秋冬茬");
                    }
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return pestArray.size();
        }

    }

    /**
     * 定义适配器的子视图
     */
    class PestViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageHoldPest;
        public TextView txtHoldSeason,txtHoldPhenophase,txtHoldPestName,txtHoldPestResult;

        public int position;

        public PestViewHolder(View itemView) {
            super(itemView);
            txtHoldSeason = (TextView) itemView.findViewById(R.id.txt_season);
            txtHoldPhenophase = (TextView) itemView.findViewById(R.id.txt_phenophase);
            txtHoldPestName = (TextView) itemView.findViewById(R.id.txt_pest_name);
            txtHoldPestResult = (TextView) itemView.findViewById(R.id.txt_result);
            imageHoldPest = (ImageView)itemView.findViewById(R.id.img_pest);
        }
    }
}
