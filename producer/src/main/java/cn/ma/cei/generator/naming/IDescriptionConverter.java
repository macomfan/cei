package cn.ma.cei.generator.naming;

public interface IDescriptionConverter {
    String getModelDescriptor(String name);

    String getClientDescriptor(String name);

    String getVariableDescriptor(String name);

    String getMemberVariableDescriptor(String name);

    String getMethodDescriptor(String name);
    
    //String getTypeDescriptor(String name);
}
