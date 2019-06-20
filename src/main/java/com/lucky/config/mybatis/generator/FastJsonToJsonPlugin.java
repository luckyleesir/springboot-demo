package com.lucky.config.mybatis.generator;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * toJson方式插件
 *
 * @author lucky
 */
public class FastJsonToJsonPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("toJson");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addBodyLine("return JSON.toJSONString(this);");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.fastjson.JSON"));
        topLevelClass.addMethod(method);
        return true;
    }

}