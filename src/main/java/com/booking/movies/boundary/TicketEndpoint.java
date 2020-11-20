package com.booking.movies.boundary;

import com.booking.movies.entity.Auditorium;
import com.booking.movies.entity.Event;
import com.booking.movies.entity.Seat;
import com.booking.movies.entity.Ticket;
import com.booking.movies.entity.User;
import com.booking.movies.repository.EventRepository;
import com.booking.movies.repository.MovieSessionRepository;
import com.booking.movies.repository.TicketRepository;
import com.booking.movies.repository.UserRepository;
import com.booking.movies.service.AuditoriumService;
import com.booking.movies.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Api("ticket")
@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketEndpoint {
    private final EventRepository eventRepository;
    private final MovieSessionRepository movieSessionRepository;
    private final AuditoriumService auditoriumService;
    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final TicketRepository ticketRepository;

    @ApiOperation("Book ticket")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ticket was booked"),
    })
    @ResponseBody
    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> bookTickets(@ApiParam("phone number") @RequestParam("phoneNumber") String phoneNumber,
                                            @ApiParam("email") @RequestParam("email") String email,
                                            @ApiParam("event name") @RequestParam("eventName") String eventName,
                                            @ApiParam("event time") @RequestParam(value = "eventDateTime")
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME/*2019-09-17T19:10:44.503Z*/) LocalDateTime eventDateTime,
                                            @ApiParam("seat number") @RequestParam("seatNumber") Integer seatNumber) {
        Set<Ticket> tickets = new HashSet<>();
        Event event = eventRepository.getByName(eventName);
        Auditorium auditorium = movieSessionRepository.getByEvent(event).getAuditorium();
        Seat seat = auditoriumService.getSeatByNumber(auditorium, seatNumber);
        User user = userRepository.findByEmail(email);
        Ticket ticket = new Ticket(event, eventDateTime, seat, user);
        tickets.add(ticket);
        bookingService.bookTickets(tickets);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("Get the ticket info")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The ticket info was retrieved"),
    })
    @ResponseBody
    @GetMapping(value = "/ticket", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ticket> getTicket( @ApiParam(value = "Id of the ticket", required = true)
                                 @RequestParam("ticketId") Long ticketId) {
        return ticketRepository.findById(ticketId).map(ticket -> new ResponseEntity<>(ticket, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation("Get tickets related to the user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The ticket info was retrieved"),
    })
    @ResponseBody
    @GetMapping(value = "/ticket/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Ticket>> getTicketByUser( @ApiParam(value = "user Id of the ticket", required = true)
                                 @RequestParam("userId") Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        val tickets = ticketRepository.getByUser(user);
        return ResponseEntity.ok(tickets);
    }

    @ApiOperation("Get tickets related to the event")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The ticket info was retrieved"),
    })
    @ResponseBody
    @GetMapping(value = "/ticket/event", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Ticket>> getTicketByEvent( @ApiParam(value = "event Id of the ticket", required = true)
                                 @RequestParam("eventId") Long eventId) {

        val event = eventRepository.findById(eventId).orElseThrow();

        val tickets = ticketRepository.getByEvent(event);
        return ResponseEntity.ok(tickets);
    }
}
