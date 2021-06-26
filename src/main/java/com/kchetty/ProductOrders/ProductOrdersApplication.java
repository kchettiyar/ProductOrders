package com.kchetty.ProductOrders;

import static springfox.documentation.builders.PathSelectors.regex;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*")
@ComponentScan("com.techm.wrapper.Dtagwrapper.*")
@EnableSwagger2
@EnableAutoConfiguration
@SpringBootApplication
public class ProductOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductOrdersApplication.class, args);
	}

	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("componentDataService").apiInfo(apiInfo()).select()
				.paths(regex("/componentDataService/.*")).build()
				.directModelSubstitute(XMLGregorianCalendar.class, MixIn.class);
	}

	public static interface MixIn {
		@JsonIgnore
		public void setYear(int year);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Catalog Service REST APIs").description("Microservice REST APIs")
				.termsOfServiceUrl("http://....").contact("TechMahindra").license("TechMahindra Licensed")
				.licenseUrl("https://techmahindra.com").version("2.0").build();
	}
}
