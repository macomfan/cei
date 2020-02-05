package cn.ma.cei.model.base;

public abstract class xStandalone<T> extends xElement {
    private String filename;

    private void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void merge(T t) {}
}
