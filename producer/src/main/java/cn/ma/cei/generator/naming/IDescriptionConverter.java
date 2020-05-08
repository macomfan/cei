package cn.ma.cei.generator.naming;

import java.util.List;
import java.util.Set;

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

    String getPrivateMemberDescriptor(String name);

    String getMethodDescriptor(String name);
    
    String toStringDescriptor(String name);

    String getSelfDescriptor();
    
    String getGenericTypeDescriptor(String baseName, List<String> subNames);

    /**
     * Retrun how to lookup a child member.
     * e.g. In Java
     * It is base.value
     *
     *
     * @param baseName
     * @param valueName
     * @return
     */
    String getReferenceByChain(String baseName, String valueName);

    /***
     * List all keywords here, to check if the variable name is conflicting with the keyword.
     * @return Set of keywords.
     */
    Set<String> getKeywords();

    Set<String> getBuildIn();
}
