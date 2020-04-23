package cn.ma.cei.generator.builder;

import cn.ma.cei.generator.Variable;

public interface IStringBuilderBuilder extends IBuilderBase {
    /**
     * Define the StringWrapper object.
     * e.g.
     * StringWrapper stringBuilderObject = new StringWrapper();
     *
     * @param stringBuilderObject the variable of StringWrapperObject
     */
    void defineStringBuilderObject(Variable stringBuilderObject);

    /**
     * Append string to StringWrapper.
     * e.g.
     * stringBuilderObject.append(variable);
     *
     * @param stringBuilderObject
     * @param variable
     */
    void appendStringItem(Variable stringBuilderObject, Variable variable);

    /**
     * Combine the string items in StringWrapper.
     * e.g.
     * stringBuilderObject.combine(separator);
     *
     * @param stringBuilderObject
     * @param separator
     */
    void combineStringItems(Variable stringBuilderObject, Variable separator);
}
