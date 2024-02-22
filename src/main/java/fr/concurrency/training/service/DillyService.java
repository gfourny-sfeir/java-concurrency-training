package fr.concurrency.training.service;

import org.springframework.stereotype.Service;

import fr.concurrency.training.model.Dilly;
import lombok.RequiredArgsConstructor;

/**
 * @author gfourny
 */
@Service
@RequiredArgsConstructor
public class DillyService {

    private final Apis apis;

    public Dilly command() {

        var preferences = apis.fetchPreferences();
        var beer = apis.fetchBeer(preferences);
        var vodka = apis.fetchVodka();

        return new Dilly(beer, vodka);
    }
}
