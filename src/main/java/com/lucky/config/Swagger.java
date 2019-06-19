package com.lucky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 配置文件
 *
 * @author lucky
 */
@Configuration
@EnableSwagger2
public class Swagger extends WebMvcConfigurationSupport {

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.lucky.controller"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	/**
	 * //启动swagger注解 启动服务，浏览器输入"http://服务名:端口号/swagger-ui.html"
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("lucky API文档")
				.description("简单优雅的restful风格")
				.version("1.0")
				.build();
	}

	private List<ApiKey> securitySchemes() {
		//设置请求头信息
		List<ApiKey> result = new ArrayList<>();
		ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
		result.add(apiKey);
		return result;
	}

	private List<SecurityContext> securityContexts() {
		//设置需要登录认证的路径
		List<SecurityContext> result = new ArrayList<>();
		result.add(getContextByPath("/user/.*"));
		return result;
	}

	private SecurityContext getContextByPath(String pathRegex){
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(pathRegex))
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		List<SecurityReference> result = new ArrayList<>();
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		result.add(new SecurityReference("Authorization", authorizationScopes));
		return result;
	}

}
