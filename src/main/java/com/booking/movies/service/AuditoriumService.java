package com.booking.movies.service;

import com.booking.movies.entity.Auditorium;
import com.booking.movies.entity.Seat;
import com.booking.movies.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuditoriumService {

    private final SeatRepository seatRepository;

    public Seat getSeatByNumber(Auditorium auditorium, int seatNumber) {
        final Optional<Seat> anySeat = auditorium.getSeats().stream().filter(seat -> seat.getSeatNumber() == seatNumber).findAny();
        return anySeat.orElse(null);
    }

    public void updateSeatStatus(boolean status, Seat seat) {
        seatRepository.updateSeatStatus(status, seat.getId());
    }
}

