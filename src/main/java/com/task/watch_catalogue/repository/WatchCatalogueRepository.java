package com.task.watch_catalogue.repository;

import com.task.watch_catalogue.entity.WatchCatalogueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchCatalogueRepository extends JpaRepository<WatchCatalogueEntity, Integer> {
}
