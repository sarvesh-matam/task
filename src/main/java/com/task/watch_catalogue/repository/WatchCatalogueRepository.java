package com.task.watch_catalogue.repository;

import com.task.watch_catalogue.entity.WatchCatalogueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WatchCatalogueRepository extends JpaRepository<WatchCatalogueEntity, String> {

    List<WatchCatalogueEntity> findByIdIn(Set<String> watchIds);
}
