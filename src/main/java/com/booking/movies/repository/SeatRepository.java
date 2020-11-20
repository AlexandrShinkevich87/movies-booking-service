package com.booking.movies.repository;

import com.booking.movies.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Modifying
    @Query(value = "update seat set is_available = ?  where id = ?", nativeQuery = true)
    void updateSeatStatus(boolean isAvailable, Long id);
}
