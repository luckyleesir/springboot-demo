package com.springbootdemo;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis-generator逆向生成启动类
 *
 * @author lucky
 */
public class MyBatisGeneratorRun {
    public static void main(String[] args) throws Exception {
        MyBatisGeneratorRun app = new MyBatisGeneratorRun();
        app.generator();
    }

    private void generator() throws Exception {

        List<String> warnings = new ArrayList<>();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
