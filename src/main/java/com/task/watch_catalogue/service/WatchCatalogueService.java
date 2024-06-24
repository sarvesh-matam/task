package com.task.watch_catalogue.service;

import com.task.watch_catalogue.dto.WatchCatalogueCheckoutResponse;
import com.task.watch_catalogue.entity.WatchCatalogueEntity;
import com.task.watch_catalogue.repository.WatchCatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WatchCatalogueService {

    private static final Logger log = LoggerFactory.getLogger(WatchCatalogueService.class);
    @Autowired
    WatchCatalogueRepository watchCatalogueRepository;

    public WatchCatalogueCheckoutResponse calculateTotalPrice(List<String> watchIdList) {
        log.info("calculateTotalPrice :: calculate total price of watch ids "+watchIdList.size());
        if(log.isDebugEnabled()){
            watchIdList.forEach(log::debug);
        }
        if(watchIdList.isEmpty()) {
            return new WatchCatalogueCheckoutResponse("0");
        }
        Map<String,Integer> uniqueWatchIdWithCountMap = mapUniqueWatchIdsWithCount(watchIdList);
        if(log.isDebugEnabled()){
            uniqueWatchIdWithCountMap.forEach((key, value) -> log.debug("Id: {}, Count: {}", key, value));
        }
        List<WatchCatalogueEntity> watchCatalogueEntityList = watchCatalogueRepository.findByIdIn(uniqueWatchIdWithCountMap.keySet());
        return new WatchCatalogueCheckoutResponse(calculateTotalPriceOfCart(uniqueWatchIdWithCountMap, watchCatalogueEntityList));
    }

    private String calculateTotalPriceOfCart(Map<String, Integer> uniqueWatchIdWithCount, List<WatchCatalogueEntity> watchCatalogueEntityList) {
        float totalPrice = 0;
        for (WatchCatalogueEntity entity : watchCatalogueEntityList) {
            int count = uniqueWatchIdWithCount.get(entity.getId());
            float unitPrice = entity.getUnitPrice();
            if (count == 1 || "".equals(entity.getDiscount())) {
                log.debug("use case : Provide watch id of a particular name only once or no discount for the watch "+entity.getId());
                totalPrice = totalPrice + (count*unitPrice);
            } else {
                String[] discount = entity.getDiscount().split(" ");
                int discountNumber= Integer.parseInt(discount[0]);
                float discountPrice = Integer.parseInt(discount[2]);
                int quotient = count / discountNumber;
                int remainder = count % discountNumber;
                if(quotient == 0) {
                    log.debug("use case : Provice watch id count more than 1 & less than eligible for discount price "+entity.getId());
                    totalPrice = totalPrice + (count*unitPrice);
                } else if (remainder == 0){
                    log.debug("use case : Provide watch id count more than 1 & equal or multiple of eligible discount price "+entity.getId());
                    totalPrice = totalPrice + (quotient*discountPrice);
                } else {
                    log.debug("use case : Provide watch id count more than 1 & more than eligible discount count which is not multiple "+entity.getId());
                    totalPrice = totalPrice + (quotient*discountPrice+remainder*unitPrice);
                }
            }
        }
        return String.valueOf(totalPrice);
    }

    private Map<String, Integer> mapUniqueWatchIdsWithCount(List<String> watchIdList) {
        log.info("mapUniqueWatchIdsWithCount :: Map unique watch id with it's count as a key value pair");
        Map<String, Integer> uniqueWatchIdWithCount = new HashMap<>();
        for (String id : watchIdList) {
            if (uniqueWatchIdWithCount.containsKey(id)){
                int count = uniqueWatchIdWithCount.get(id);
                uniqueWatchIdWithCount.put(id, ++count);
            } else {
                uniqueWatchIdWithCount.put(id, 1);
            }
        }
        return uniqueWatchIdWithCount;
    }
}
