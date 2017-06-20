package com.hxsn.ssk.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxsn.library.beans.Nongsh;
import com.hxsn.library.db.NongshService;
import com.hxsn.library.http.HttpRequest;
import com.hxsn.library.utils.BitmapUtil;
import com.hxsn.library.utils.DownloadUtil;
import com.hxsn.library.utils.JsonUtil;
import com.hxsn.library.utils.MyFileUtil;
import com.hxsn.ssk.R;
import com.hxsn.ssk.TApplication;
import com.hxsn.ssk.activity.NewsActivity;
import com.hxsn.ssk.utils.Const;
import com.hxsn.ssk.utils.DebugUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.  随时看
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class Nshui2Fragment extends Fragment {

    private GridView gridView;
    private Context context;
    private List<String> urls;
    private List<Nongsh> nongshList;

    public Nshui2Fragment() {
    }

    public Nshui2Fragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ValidFragment")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_njhui2, container, false);

        addView(view);

        //更新农事汇图标
        updateNongshImage();

        return view;
    }

    //更新农事汇图标
    private void updateNongshImage() {

        File file = new File(Const.PATH_NONGSH_IMAGE_PATH);
        if(!file.isDirectory()){
            file.delete();
        }
        boolean isNull = MyFileUtil.isFileDirNull(file);//文件夹是否为空
        String url = Const.URL_NONGSH_LIST;
        nongshList = NongshService.getInstance(getActivity()).getNongshList();

        if(isNull || TApplication.isUpdateNongshImage){
            new HttpRequest(context) {
                @Override
                public void getResponse(String response) {
                    nongshList = JsonUtil.getNongshList(HttpRequest.result);

                    if (nongshList != null) {
                        MyFileUtil.deleteAllFile(Const.PATH_NONGSH_IMAGE_PATH);
                        //下载前清空文件夹
                        int i = 0;
                        urls = new ArrayList<>();
                        for(Nongsh nongsh:nongshList){
                            urls.add(Const.URL_HEAD_NONGSH_IMAGE+nongsh.getImage());
                            DebugUtil.d("urls-" + i + 1 + "=" + urls.get(i));
                            i++;
                        }
                        Handler.Callback callback = new DownloadHandlerCallback();
                        final Handler handler = new Handler(callback);
                        new Thread(){
                            @Override
                            public void run() {
                                DownloadUtil.downloadImagesToLocal(urls, Const.PATH_NONGSH_IMAGE_PATH, handler);
                            }
                        }.start();
                    }
                }
            }.doGet(url);
        }else {//文件不为空
            if(nongshList == null || nongshList.size() == 0){
                new HttpRequest(context) {
                    @Override
                    public void getResponse(String response) {
                        nongshList = JsonUtil.getNongshList(HttpRequest.result);
                        urls = new ArrayList<>();
                        addNonshSql();//向农事汇数据库中添加数据
                        //重新下载图标
                        MyFileUtil.deleteAllFile(Const.PATH_NONGSH_IMAGE_PATH);
                        Handler.Callback callback = new DownloadHandlerCallback();
                        final Handler handler = new Handler(callback);
                        new Thread(){
                            @Override
                            public void run() {
                                DownloadUtil.downloadImagesToLocal(urls, Const.PATH_NONGSH_IMAGE_PATH, handler);
                            }
                        }.start();
                    }
                }.doGet(url);
            }else{
                addAdapter();
            }
        }
    }


    /**
     * @ description 向农事汇数据库中添加数据
     */
    private void addNonshSql(){
        int i=0;
        for(Nongsh nongsh:nongshList){
            urls.add(Const.URL_HEAD_NONGSH_IMAGE + nongsh.getImage());
            DebugUtil.d("urls-" + i + 1 + "=" + urls.get(i));
            String imgName = DownloadUtil.getFileName(urls.get(i));
            String path =  Const.PATH_NONGSH_IMAGE_PATH + imgName+".png";
            nongshList.get(i).setPath(path);
            DebugUtil.d("njhui2Fragment-Const.PATH_NONGSH_IMAGE_PATH+imgName=" + path);
            NongshService.getInstance(context).add(nongshList.get(i));
            i++;
        }
    }

    //下载图标后
    class DownloadHandlerCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case Const.MSG_SUCCESS:
                    NongshService.getInstance(context).clear();
                    for(int i=0; i<urls.size(); i++){
                        String imgName = DownloadUtil.getFileName(urls.get(i));
                        String path =  Const.PATH_NONGSH_IMAGE_PATH + imgName+".png";
                        nongshList.get(i).setPath(path);
                        DebugUtil.d("njhui2Fragment-Const.PATH_NONGSH_IMAGE_PATH+imgName=" + path);
                        NongshService.getInstance(context).add(nongshList.get(i));
                    }

                    addAdapter();
                    break;
                case Const.MSG_FAILURE:
                    break;
            }
            return true;
        }
    }

    private void addAdapter() {
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Nongsh nongsh = nongshList.get(position);
                Intent intent = new Intent();
                intent.putExtra("id",nongsh.getId());
                intent.putExtra("name",nongsh.getName());
                intent.setClass(getActivity(), NewsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void addView(View view) {
        gridView = (GridView) view.findViewById(R.id.grid_view);
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return nongshList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_image, parent, false);
                viewHolder.txtView = (TextView) convertView.findViewById(R.id.txt);
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.image);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            List<Nongsh> tempNongshList = NongshService.getInstance(context).getNongshList();
            viewHolder.txtView.setText(tempNongshList.get(position).getName());
            Bitmap bitmap = BitmapUtil.getLocalBitmap(tempNongshList.get(position).getPath());
            viewHolder.imageView.setImageBitmap(bitmap);
            return convertView;
        }
    }

    //     ViewHolder 模式, 效率提高 50%
    static class ViewHolder {
        TextView txtView;
        ImageView imageView;
    }

}
