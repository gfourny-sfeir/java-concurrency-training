package fr.concurrency.training.controller;

import fr.concurrency.training.model.gdu.UtilisateurRefUtAppWithFonction;
import fr.concurrency.training.service.GduService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gfourny
 */
@RestController
@RequestMapping("/api/gdu")
@RequiredArgsConstructor
@Slf4j
public class GduController {

    private final GduService gduService;

    @GetMapping("/utilisateurs")
    public List<UtilisateurRefUtAppWithFonction> getUsers() {
        return gduService.retrieveUsersWithFonctions();
    }

    //TODO implémenter un autre endpoint non bloquant
}
