package com.task.watch_catalogue.service;

import com.task.watch_catalogue.entity.WatchCatalogueEntity;
import com.task.watch_catalogue.repository.WatchCatalogueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class WatchCatalogueServiceTest {
    @Mock
    private WatchCatalogueRepository watchCatalogueRepository;

    @InjectMocks
    WatchCatalogueService watchCatalogueService;

    @Test
    void testEmptyWatchIdList() {
        Assertions.assertEquals("0", watchCatalogueService.calculateTotalPrice(List.of()).getPrice());

    }

    @Test
    void testWatchIdListWithoutDiscount() {
        Mockito.when(this.watchCatalogueRepository.findByIdIn(Mockito.anySet())).thenReturn(this.createMockEntityList());
        Assertions.assertEquals("260.0", watchCatalogueService.calculateTotalPrice(Arrays.asList("001","002","003","004")).getPrice());
    }

    @Test
    void testWatchIdListWithDiscount() {
        Mockito.when(this.watchCatalogueRepository.findByIdIn(Mockito.anySet())).thenReturn(this.createMockEntityList());
        Assertions.assertEquals("430.0", watchCatalogueService.calculateTotalPrice(Arrays.asList("001","001","001","002","002","003","004","004")).getPrice());
    }

    @Test
    void testWatchIdListWithUnavailableId() {
        Mockito.when(this.watchCatalogueRepository.findByIdIn(Mockito.anySet())).thenReturn(this.createMockEntityList());
        Assertions.assertEquals("430.0", watchCatalogueService.calculateTotalPrice(Arrays.asList("001","001","002","002","003","004","004","005")).getPrice());
    }

    @Test
    void testWatchIdListWithEmptyId() {
        Mockito.when(this.watchCatalogueRepository.findByIdIn(Mockito.anySet())).thenReturn(this.createMockEntityList());
        Assertions.assertEquals("430.0", watchCatalogueService.calculateTotalPrice(Arrays.asList("001","001","002","002","003","004","004","")).getPrice());
    }

    @Test
    void testWatchIdListWithMoreThanOneTimeDiscount() {
        Mockito.when(this.watchCatalogueRepository.findByIdIn(Mockito.anySet())).thenReturn(this.createMockEntityList());
        Assertions.assertEquals("630.0", watchCatalogueService.calculateTotalPrice(Arrays.asList("001","001","001","002","002","002","002","002","003","004","004")).getPrice());
    }

    private List<WatchCatalogueEntity> createMockEntityList() {
        List<WatchCatalogueEntity> watchCatalogueEntityList = new ArrayList<>();
        WatchCatalogueEntity entity = new WatchCatalogueEntity("001","Rolex",100,"3 for 200");
        WatchCatalogueEntity entity1 = new WatchCatalogueEntity("002","Michael Kors",80,"2 for 120");
        WatchCatalogueEntity entity2 = new WatchCatalogueEntity("003","Swatch",50,"");
        WatchCatalogueEntity entity3 = new WatchCatalogueEntity("004","Casio",30,"");
        watchCatalogueEntityList.add(entity);
        watchCatalogueEntityList.add(entity1);
        watchCatalogueEntityList.add(entity2);
        watchCatalogueEntityList.add(entity3);
        return watchCatalogueEntityList;
    }
}
