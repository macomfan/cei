package cn.ma.cei.generator.naming;

public interface IDescriptionConverter {
    String getFileDescriptor(String name);
    
    String getModelDescriptor(String name);

    String getClientDescriptor(String name);

    String getVariableDescriptor(String name);

    String getMemberVariableDescriptor(String name);

    String getMethodDescriptor(String name);
    
    String toStringDescriptor(String name);
    
    //String getTypeDescriptor(String name);
}
