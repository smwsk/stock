package com.smu.stock.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
@Configuration
public class DruidConfig {

	private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	public DataSource dataSource() {
		return new DruidDataSource();
	}

}
