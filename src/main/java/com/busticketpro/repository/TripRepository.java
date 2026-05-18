package com.busticketpro.repository;

import com.busticketpro.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("""
        select distinct t
        from Trip t
        join fetch t.route r
        join fetch r.fromLocation
        join fetch r.toLocation
        join fetch t.bus
        where (:fromId is null or r.fromLocation.id = :fromId)
          and (:toId is null or r.toLocation.id = :toId)
          and (:date is null or function('date', t.departureTime) = :date)
        order by t.departureTime asc
    """)
    List<Trip> search(@Param("fromId") Long fromId,
                      @Param("toId") Long toId,
                      @Param("date") LocalDate date);

    @Query("""
        select t
        from Trip t
        join fetch t.route r
        join fetch r.fromLocation
        join fetch r.toLocation
        join fetch t.bus
        where t.id = :id
    """)
    Optional<Trip> findDetailById(@Param("id") Long id);
}