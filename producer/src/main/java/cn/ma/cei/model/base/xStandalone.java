package cn.ma.cei.model.base;

import java.util.LinkedList;
import java.util.List;

public abstract class xStandalone<T> extends xElement {

    public abstract void merge(T t);

    public <T extends xStandalone> T mergeElement(T dst, T src) {
        if (dst == null) {
            return src;
        }
        if (src == null) {
            return dst;
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
