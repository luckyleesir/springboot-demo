package com.lucky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置文件
 *
 * @author lucky
 */
@Configuration(value="false")
@EnableSwagger2
public class Swagger {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lucky.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * //启动swagger注解 启动服务，浏览器输入"http://服务名:8080/swagger-ui.html"
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("lucky API文档")
				.description("简单优雅的restful风格")
				.version("1.0")
				.build();
	}

}
