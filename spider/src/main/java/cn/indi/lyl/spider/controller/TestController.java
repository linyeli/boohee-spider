package cn.indi.lyl.spider.controller;

import cn.indi.lyl.spider.entity.Food;
import cn.indi.lyl.spider.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/a")
    public String doTest() {
        Food food = new Food();
        food.setName("test");
        Food result = foodRepository.save(food);
        return result.getName();
    }
}
