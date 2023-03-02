package com.example.codingevents.controllers;

import com.example.codingevents.data.EventRepository;
import com.example.codingevents.models.Event;
import com.example.codingevents.models.EventType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;







@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    //findAll, save, findById - methods that are part of CRUDrepo and therefore our interface

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("events", eventRepository.findAll()); //EventData.getAll is calling a method on the class
        return "events/index";
    }

    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("types", EventType.values());
        return "events/create";
    }
    @PostMapping("create")
    public String createEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) { //Spring will create a newEvent object for us

        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            return "events/create";
        }

        eventRepository.save(newEvent);
        return "redirect:/events";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model) {
        model.addAttribute("title", "Delete events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required =false) int[] eventIds) {
        if (eventIds !=null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
        return "redirect:/events";
    }

    @GetMapping("{id}")
    public String displaySingleContact(@PathVariable int id, Model model) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            model.addAttribute("event", event);

            return "events/single-contact";
        } else {
            // Handle the case where the event with the given ID is not found
            return "error";
        }
    }

    @PostMapping("{id}/edit")
    public String editEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) { //Spring will create a newEvent object for us

        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            return "events/edit";
        }

        eventRepository.save(newEvent);
        return "redirect:/events";
    }



    @GetMapping("{id}/edit")
    public String editSingleContact(@PathVariable int id, Model model,@ModelAttribute @Valid Event newEvent, Errors errors) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            model.addAttribute("event", event);
            eventRepository.deleteById(id);
            return "events/edit";
        } else {
            // Handle the case where the event with the given ID is not found
            return "error";
        }
    }


}