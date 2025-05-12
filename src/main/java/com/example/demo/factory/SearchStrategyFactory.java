package com.example.demo.factory;

import com.example.demo.strategy.SearchStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 搜索策略工厂
 * 负责创建和管理不同的搜索策略
 */
@Component
public class SearchStrategyFactory {
    private final Map<String, SearchStrategy> strategyMap;

    /**
     * 构造函数
     * @param strategyMap 策略映射表，key为策略类型，value为策略实现
     */
    public SearchStrategyFactory(Map<String, SearchStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    /**
     * 获取搜索策略
     * @param type 策略类型（title/author）
     * @return 对应的搜索策略
     */
    public SearchStrategy getStrategy(String type) {
        SearchStrategy strategy = strategyMap.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported search type: " + type);
        }
        return strategy;
    }
} 