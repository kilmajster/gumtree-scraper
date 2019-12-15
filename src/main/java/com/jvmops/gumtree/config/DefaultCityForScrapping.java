package com.jvmops.gumtree.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Profile("!default")
@Component
@Slf4j
@AllArgsConstructor
class DefaultCityForScrapping {
    private static final City KATOWICE_SCRAPPER_ON_NOTIFICATIONS_OFF = City.builder()
            .id(ObjectId.get())
            .emails(Set.of())
            .name("katowice")
            .build();

    private CityRepository cityRepository;

    @PostConstruct
    void addIfNoneSet() {
        if (cityRepository.count() == 0) {
            cityRepository.save(KATOWICE_SCRAPPER_ON_NOTIFICATIONS_OFF);
        }
    }
}
