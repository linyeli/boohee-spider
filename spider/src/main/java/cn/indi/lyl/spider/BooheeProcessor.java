package cn.indi.lyl.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

// get data from boohee
public class BooheeProcessor implements PageProcessor {
    private Logger logger = LoggerFactory.getLogger(BooheeProcessor.class);
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        if(page.getUrl().get().contains("group")){
            List<String> allLinks = page.getHtml().links().all();
            List<String> foodLinks = page.getHtml().links().regex("(https://www.boohee\\.com/shiwu/\\w+)").all();
            Set<String> removeDuplicateFoodLinksSet = new HashSet<>(foodLinks);
            page.addTargetRequests(new ArrayList<>(removeDuplicateFoodLinksSet));
            List<String> paginateLinks = page.getHtml().links().regex("(https://www.boohee\\.com/food/group/\\w+\\??\\w*\\=?\\w*)").all();
            Set<String> removeDuplicatePageLinksSet = new HashSet<>(paginateLinks);
            page.addTargetRequests(new ArrayList<>(removeDuplicatePageLinksSet));
        }
        // 部分二：定义如何抽取页面信息，并保存下来
        if(page.getUrl().get().contains("shiwu")){
            logger.info("fetch data,url is {}", page.getUrl());
            page.putField("nav", page.getHtml().xpath("//h2[@class='crumb']/text()").toString());
            page.putField("alias", page.getHtml().xpath("//ul[@class='basic-infor']/li[1]/text()").toString());
            page.putField("unit", page.getHtml().xpath("//ul[@class='basic-infor']/li[2]/span[@id='food-calory']/text()").toString());
            page.putField("calorie", page.getHtml().xpath("//ul[@class='basic-infor']/li[2]/span[@id='food-calory']/span[@class='stress red1']/text()").toString());
            page.putField("category", page.getHtml().xpath("//ul[@class='basic-infor']/li[3]/strong/a/text()").toString());
            page.putField("carbohydrateTitle", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[2]/dd[2]/span[@class='dt']/text()").toString());
            page.putField("carbohydrate", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[2]/dd[2]/span[@class='dd']/text()").toString());
            page.putField("fatTitle", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[3]/dd[1]/span[@class='dt']/text()").toString());
            page.putField("fat", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[3]/dd[1]/span[@class='dd']/text()").toString());
            page.putField("proteinTitle", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[3]/dd[2]/span[@class='dt']/text()").toString());
            page.putField("protein", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[3]/dd[2]/span[@class='dd']/text()").toString());
            page.putField("celluloseTitle", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[4]/dd[1]/span[@class='dt']/text()").toString());
            page.putField("cellulose", page.getHtml().xpath("//div[@class='nutr-tag margin10']/div/dl[4]/dd[1]/span[@class='dd']/text()").toString());
            if (page.getResultItems().get("nav")==null){
                //skip this page
                page.setSkip(true);
            }
        }else{
            page.setSkip(true);
        }
        // 部分三：从页面发现后续的url地址来抓取
//        page.addTargetRequests(page.getHtml().links().regex("(http://www.boohee\\.com/food/group//[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BooheeProcessor())
                //从"https://www.boohee.com/food/group/1"开始抓
                .addUrl("https://www.boohee.com/food/group/1")
                .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
