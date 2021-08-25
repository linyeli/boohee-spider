package cn.indi.lyl.spider.repository;

import cn.indi.lyl.spider.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
