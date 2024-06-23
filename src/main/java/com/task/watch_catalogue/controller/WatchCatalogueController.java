package com.task.watch_catalogue.controller;

import com.task.watch_catalogue.dto.WatchCatalogueCheckoutResponse;
import com.task.watch_catalogue.service.WatchCatalogueService;
import jakarta.annotation.Nonnull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class WatchCatalogueController {

    private static final Logger log = LogManager.getLogger(WatchCatalogueController.class);
    @Autowired
    WatchCatalogueService watchCatalogueService;

    @PostMapping("checkout")
    public ResponseEntity<WatchCatalogueCheckoutResponse> calculateTotalPrice(@RequestBody @Nonnull List<String> watchIdList) {
        return new ResponseEntity<>(watchCatalogueService.calculateTotalPrice(watchIdList), HttpStatus.OK);
    }
}
