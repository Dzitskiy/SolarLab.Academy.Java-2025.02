package com.solarl.education.repository;

import com.solarl.education.entity.Advertisement;
import com.solarl.education.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a WHERE a.category = :category")
    List<Advertisement> findByCategory(@Param("category") CategoryEnum category);

    @Query("SELECT a FROM Advertisement a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<Advertisement> findByNameIgnoreCase(@Param("name") String name);

    @Query(value = "SELECT * FROM advertisement WHERE seller = :seller", nativeQuery = true)
    List<Advertisement> findBySeller(@Param("seller") String seller);

    @Query(value = "SELECT * FROM advertisement WHERE cost BETWEEN :minCost AND :maxCost", nativeQuery = true)
    List<Advertisement> findByCostRange(@Param("minCost") Integer minCost, @Param("maxCost") Integer maxCost);
}

