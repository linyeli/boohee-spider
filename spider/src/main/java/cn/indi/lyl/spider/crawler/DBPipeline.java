package cn.indi.lyl.spider.crawler;

import cn.indi.lyl.spider.entity.Food;
import cn.indi.lyl.spider.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class DBPipeline implements Pipeline {
    @Autowired
    private FoodRepository foodRepository;
    @Override
    public void process(ResultItems resultItems, Task task) {
        Food food = resultItems.get("food");
        Food result = foodRepository.save(food);
        System.out.println(result);
    }
}
