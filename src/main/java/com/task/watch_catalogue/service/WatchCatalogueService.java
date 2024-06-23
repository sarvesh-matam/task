package com.task.watch_catalogue.service;

import com.task.watch_catalogue.repository.WatchCatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchCatalogueService {

    @Autowired
    WatchCatalogueRepository watchCatalogueRepository;
}
