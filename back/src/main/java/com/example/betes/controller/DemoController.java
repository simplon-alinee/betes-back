package com.example.betes.controller;

import com.example.betes.model.Bet;
import com.example.betes.model.BetSkeleton;
import com.example.betes.service.BetService;
import com.example.betes.service.DemoService;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired
    DemoService demoService;
    /**
     * cette méthode va servir a initialiser des données fixes en base de donnée, afin de pouvoir réaliser la démo sans
     * risque
     */
    @GetMapping("/init")
    public void initDemo() {
        //
    }

    /**
     * cette méthode va servir a déclencer la mise un jour d'un match, comme pour simuler l'activation de la mise a jour
     * de donnée sur un match spécifique
     */
    @GetMapping("/go/{matchId}")
    public void demoGo( @PathVariable("matchId") Long matchId) {
        demoService.demoGo(matchId);
    }

}
