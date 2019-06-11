package com.lucky.config.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Lombok注解插件
 *
 * @author lukcy
 */
public class LombokAnnotationPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
        List<Method> methods = topLevelClass.getMethods();
        List<Method> remove = new ArrayList<>();
        for (Method method : methods) {
            if (method.getBodyLines().size() < 2) {
                remove.add(method);
            }
        }
        methods.removeAll(remove);
        return true;
    }

}