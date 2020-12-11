package com.fishpound.accountservice.service.tools;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class PageTools {
    private String sortName;
    private Sort.Direction direction;
    private int page;
    private int pageSize = 10;
    private Map<String, Sort.Direction> sortMap = new HashMap<>();

    public PageTools(String sortName, Sort.Direction direction, int page) {
        this.sortName = sortName;
        this.direction = direction;
        this.page = page;
    }

    public PageTools(String sortName, Sort.Direction direction, int page, int pageSize) {
        this.sortName = sortName;
        this.direction = direction;
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageTools(int page, Map<String, Sort.Direction> sortMap) {
        this.page = page;
        this.sortMap = sortMap;
    }

    public PageTools(int page, int pageSize, Map<String, Sort.Direction> sortMap) {
        this.page = page;
        this.pageSize = pageSize;
        this.sortMap = sortMap;
    }

    public Pageable sortSingle(){
        Sort sort = Sort.by(direction, sortName);
        Pageable pageable = PageRequest.of(page-1, pageSize, sort);
        return pageable;
    }

    public Pageable sortMultiple(){
        List<Sort.Order> orders = new ArrayList<>();
        Set<String> keySet = sortMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            orders.add(new Sort.Order(sortMap.get(key), key));
        }
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page-1, pageSize, sort);
        return pageable;
    }
}
