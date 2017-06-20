package com.hxsn.library.beans;

/**
 * @ desc: 农事汇消息
 * "id":"40288c9a544bb8ae01544c050aeb0009",
 * "name":"新闻中心",
 * "image":"/resource/app/nshimg/xwzx.jpg"
 */
public class Nongsh {
    private String id;
    private String name;
    private String image;//图标
    //private Bitmap bitmap;
    private String path;//图标本地地址

    public Nongsh() {}

    public Nongsh(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /*public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Nongsh{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
