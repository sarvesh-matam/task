package com.task.watch_catalogue.controller;

import com.task.watch_catalogue.service.WatchCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WatchCatalogueController {

    @Autowired
    WatchCatalogueService watchCatalogueService;
}
