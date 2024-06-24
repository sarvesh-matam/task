package com.task.watch_catalogue.service;

import com.task.watch_catalogue.dto.WatchCatalogueCheckoutResponse;
import com.task.watch_catalogue.entity.WatchCatalogueEntity;
import com.task.watch_catalogue.repository.WatchCatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeSet;
import java.util.List;
import java.util.TreeMap;

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
        SortedMap<String,Integer> uniqueWatchIdWithCount = mapUniqueWatchIdsWithCount(watchIdList);
        if(log.isDebugEnabled()){
            uniqueWatchIdWithCount.forEach((key, value) -> log.debug("Id: {}, Count: {}", key, value));
        }
        TreeSet<WatchCatalogueEntity> watchCatalogueEntitySet = new TreeSet<>(watchCatalogueRepository.findByIdIn(uniqueWatchIdWithCount.keySet()));
        return new WatchCatalogueCheckoutResponse(calculateTotalPriceOfCart(uniqueWatchIdWithCount, watchCatalogueEntitySet));
    }

    private String calculateTotalPriceOfCart(SortedMap<String, Integer> uniqueWatchIdWithCount, TreeSet<WatchCatalogueEntity> watchCatalogueEntitySet) {
        float totalPrice = 0;
        for (WatchCatalogueEntity entity : watchCatalogueEntitySet) {
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

    private SortedMap<String, Integer> mapUniqueWatchIdsWithCount(List<String> watchIdList) {
        log.info("mapUniqueWatchIdsWithCount :: Map unique watch id with it's count as a key value pair");
        SortedMap<String, Integer> uniqueWatchIdWithCount = new TreeMap<>();
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
