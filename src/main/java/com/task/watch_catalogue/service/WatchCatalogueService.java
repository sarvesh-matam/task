package com.task.watch_catalogue.service;

import com.task.watch_catalogue.dto.WatchCatalogueCheckoutResponse;
import com.task.watch_catalogue.entity.WatchCatalogueEntity;
import com.task.watch_catalogue.repository.WatchCatalogueRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedMap;
import java.util.TreeSet;
import java.util.List;
import java.util.TreeMap;

@Service
public class WatchCatalogueService {

    private static final Logger log = LogManager.getLogger(WatchCatalogueService.class);
    @Autowired
    WatchCatalogueRepository watchCatalogueRepository;

    public WatchCatalogueCheckoutResponse calculateTotalPrice(List<String> watchIdList) {
        if(watchIdList.isEmpty()) {
            return new WatchCatalogueCheckoutResponse("0");
        }
        SortedMap<String,Integer> uniqueWatchIdWithCount = mapUniqueWatchIdsWithCount(watchIdList);
        TreeSet<WatchCatalogueEntity> watchCatalogueEntitySet = new TreeSet<>(watchCatalogueRepository.findByIdIn(uniqueWatchIdWithCount.keySet()));
        return new WatchCatalogueCheckoutResponse(calculateTotalPriceOfCart(uniqueWatchIdWithCount, watchCatalogueEntitySet));
    }

    private String calculateTotalPriceOfCart(SortedMap<String, Integer> uniqueWatchIdWithCount, TreeSet<WatchCatalogueEntity> watchCatalogueEntitySet) {
        float totalPrice = 0;
        for (WatchCatalogueEntity entity : watchCatalogueEntitySet) {
            int count = uniqueWatchIdWithCount.get(entity.getId());
            float unitPrice = entity.getUnitPrice();
            if (count == 1 || "".equals(entity.getDiscount())) {
                totalPrice = totalPrice + unitPrice;
            } else {
                String[] discount = entity.getDiscount().split(" ");
                int discountNumber= Integer.parseInt(discount[0]);
                float discountPrice = Integer.parseInt(discount[2]);
                int quotient = count / discountNumber;
                int remainder = count % discountNumber;
                if(quotient == 0) {
                    totalPrice = totalPrice + (count*unitPrice);
                } else if (remainder == 0){
                    totalPrice = totalPrice + (quotient*discountPrice);
                } else {
                    totalPrice = totalPrice + (quotient*discountPrice+remainder*unitPrice);
                }
            }
        }
        return String.valueOf(totalPrice);
    }

    private SortedMap<String, Integer> mapUniqueWatchIdsWithCount(List<String> watchIdList) {
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
