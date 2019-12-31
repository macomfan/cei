package cn.ma.cei.generator.naming;

public interface IDescriptionConverter {
    
    /***
     * Convert a file name to specified file name.
     * 
     * @param name The name of file.
     * @return The converted name of file.
     */
    String getFileDescriptor(String name);
    
    String getModelDescriptor(String name);

    String getClientDescriptor(String name);

    String getVariableDescriptor(String name);

    String getMemberVariableDescriptor(String name);

    String getMethodDescriptor(String name);
    
    String toStringDescriptor(String name);
    
    String getGenericTypeDescriptor(String name);
    
    //String getTypeDescriptor(String name);
}
