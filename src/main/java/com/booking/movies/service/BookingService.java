package com.booking.movies.service;

import com.booking.movies.entity.Auditorium;
import com.booking.movies.entity.Event;
import com.booking.movies.entity.MovieSession;
import com.booking.movies.entity.Seat;
import com.booking.movies.entity.Ticket;
import com.booking.movies.repository.TicketRepository;
import com.booking.movies.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserAccountRepository userAccountRepository;
    private final TicketRepository ticketRepository;
    private final AuditoriumService auditoriumService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void bookTickets(Set<Ticket> tickets) {
        validateTickets(tickets);
        for (Ticket ticket : tickets) {
            Ticket persistTicket = bookTicket(ticket);
            Auditorium aud = getAuditoriumByEventAndDate(ticket.getEvent(), ticket.getDateTime());
            Seat seat = auditoriumService.getSeatByNumber(aud, persistTicket.getSeat().getSeatNumber());
            auditoriumService.updateSeatStatus(false, seat);
        }
    }

    private void validateTickets(Set<Ticket> tickets) {
        Set<Seat> seats = tickets.stream().map(Ticket::getSeat).collect(Collectors.toSet());
        for (Ticket ticket : tickets) {
            if (!areSeatsAvailable(ticket.getEvent(), ticket.getDateTime(), seats)) {
                throw new IllegalArgumentException("The specified seats couldn't be booked!");
            }
        }
    }

    private boolean areSeatsAvailable(Event event, LocalDateTime dateTime, Set<Seat> seats) {
        final Auditorium auditorium = getAuditoriumByEventAndDate(event, dateTime);
        return auditorium != null && (auditorium.getSeats().containsAll(seats) && seats.stream().allMatch(Seat::isAvailable));
    }

    private Auditorium getAuditoriumByEventAndDate(Event event, LocalDateTime dateTime) {
        final Optional<MovieSession> anyMovieSession = event.getMovieSessions().stream()
                .filter(movieSession -> movieSession.getAirDate().equals(dateTime))
                .findAny();
        return anyMovieSession.map(MovieSession::getAuditorium).orElse(null);
    }

    public Ticket bookTicket(Ticket ticket) {
        if (ticket.getUser() == null) {
            throw new RuntimeException("Please sign in to book tickets!");
        }
        double ticketPrice = ticket.getEvent().getTicketPrice();
        double userBalance = userAccountRepository.getByUser(ticket.getUser()).getBalance();
        if (ticketPrice > userBalance) {
            throw new RuntimeException("You don't have enough money. Please refill you balance. Ticket price: "
                    + ticketPrice + " You balance: " + userBalance);
        }
        double price = userBalance - ticketPrice;
        userAccountRepository.updateBalance(price, ticket.getUser().getId());
        return ticketRepository.save(ticket);
    }
}
