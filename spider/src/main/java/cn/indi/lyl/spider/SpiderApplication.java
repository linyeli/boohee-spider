package cn.indi.lyl.spider;

import cn.indi.lyl.spider.crawler.BooheeProcessor;
import cn.indi.lyl.spider.crawler.DBPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import us.codecraft.webmagic.Spider;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories("cn.indi.lyl.spider.repository")
public class SpiderApplication implements CommandLineRunner {
	@Autowired
	private BooheeProcessor booheeProcessor;
	@Autowired
	private DBPipeline dbPipeline;

	public static void main(String[] args) {
		SpringApplication.run(SpiderApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
//		if(false){return;}
//		System.out.println(booheeProcessor);
		Spider.create(booheeProcessor)
				//从"https://www.boohee.com/food/group/1"开始抓
				.addUrl("https://www.boohee.com/food/group/1")
//                .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
				.addPipeline(dbPipeline)
				//开启5个线程抓取
				.thread(5)
				//启动爬虫
				.run();
	}
}
