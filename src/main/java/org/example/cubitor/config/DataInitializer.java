package org.example.cubitor.config;

import lombok.RequiredArgsConstructor;
import org.example.cubitor.entity.Event;
import org.example.cubitor.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;

    @Override
    public void run(String... args) {
        if (eventRepository.count() == 0) {
            List<Event> defaultEvents = List.of(
                    new Event("3x3", Event.ScrambleGenerationType.X3),
                    new Event("2x2", Event.ScrambleGenerationType.X2),
                    new Event("4x4", Event.ScrambleGenerationType.X4),
                    new Event("5x5", Event.ScrambleGenerationType.X5),
                    new Event("6x6", Event.ScrambleGenerationType.X6),
                    new Event("7x7", Event.ScrambleGenerationType.X7),
                    new Event("Pyraminx", Event.ScrambleGenerationType.PYRA),
                    new Event("Megaminx", Event.ScrambleGenerationType.MINX),
                    new Event("Skewb", Event.ScrambleGenerationType.SKEWB),
                    new Event("Clock", Event.ScrambleGenerationType.CLOCK)
            );

            eventRepository.saveAll(defaultEvents);
            System.out.println(">>> Basic events initialized");
        }
    }
}
