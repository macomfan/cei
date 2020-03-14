package cn.ma.cei.model.base;

import java.util.LinkedList;
import java.util.List;

public abstract class xStandalone<T> extends xElement {
    private String filename;

    private void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public abstract void merge(T t);

    public <T extends xStandalone> T mergeElement(T dst, T src) {
        if (dst == null) {
            return src;
        }
        dst.merge(src);
        return dst;
    }

    public  <T> List<T> mergeList(List<T> dst, List<T> src) {
        if (dst == null) {
            dst = new LinkedList<>();
        }
        if (src != null) {
            dst.addAll(src);
        }
        return dst;
    }
}
