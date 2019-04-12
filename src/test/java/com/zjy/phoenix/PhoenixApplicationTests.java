package com.zjy.phoenix;

import com.zjy.phoenix.config.springMVC.RestTemplateConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({RestTemplateConfig.class})
public class PhoenixApplicationTests {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private RestTemplate restTemplate;
	@Test
	public void contextLoads() {

		String result = restTemplate
				.getForObject("http://127.0.0.1/hello", String.class);
		logger.info(result);
	}

}
