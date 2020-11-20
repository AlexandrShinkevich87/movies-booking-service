package com.booking.movies.boundary;

import com.booking.movies.entity.Event;
import com.booking.movies.repository.EventRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Api("event")
@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
@Slf4j
public class EventEndpoint {
    private final PagedResourcesAssembler<Event> resourceAssembler;
    private final EventRepository eventRepository;

    @ApiOperation("Get all events for the next few days")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All data events with pagination and links are shown"),
    })
    @ResponseBody
    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PagedResources<Resource<Event>> getNextEvents(
            @ApiParam("Number of page") @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @ApiParam("Count of events") @RequestParam(value = "size") Integer size,
            @ApiParam("Sorting for events") @RequestParam(value = "sort") String[] sort /*rating, name*/,
            @ApiParam("Up to which date events can be shown") @RequestParam(value = "to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME/*2019-09-17T19:10:44.503Z*/) LocalDateTime to) {
        val pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        val eventPage = eventRepository.getForDateRange(LocalDateTime.now(), to.plusDays(1), pageRequest);
        return this.resourceAssembler.toResource(eventPage);
    }

    @ApiOperation("Get all events for the day")
    @ApiResponses({
            @ApiResponse(code = 200, message = "All data events with pagination and links are shown"),
    })
    @ResponseBody
    @GetMapping(value = "/certain", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PagedResources<Resource<Event>> getEventsForDate(
            @ApiParam("Number of page") @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @ApiParam("Count of events") @RequestParam(value = "size") Integer size,
            @ApiParam("Sorting for events") @RequestParam(value = "sort") String[] sort /*rating, name*/,
            @ApiParam("Date of events") @RequestParam(value = "eventDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME/*2019-09-17T19:10:44.503Z*/) LocalDateTime eventDate) {
        val pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sort);
        val eventPage = eventRepository.getForDateRange(eventDate, eventDate.plusDays(1), pageRequest);
        return this.resourceAssembler.toResource(eventPage);
    }


}
