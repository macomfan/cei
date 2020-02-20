package cn.ma.cei.utils;

public class TwoTuple<Value1, Value2> {
    private Value1 value1;
    private Value2 value2;

    public TwoTuple(Value1 value1, Value2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Value1 get1() {
        return value1;
    }

    public Value2 get2() {
        return value2;
    }
}
