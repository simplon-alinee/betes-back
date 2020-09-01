package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.Team;
import com.example.betes.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    public static final int PAGE_SIZE_MIN = 10;
    public static final int PAGE_SIZE_MAX = 100;
    public static final int PAGE_MIN = 0;
    private static final String PAGE_VALID_MESSAGE = "La taille de la page doit être comprise entre 10 et 100";


    @Autowired
    private TeamRepository teamRepository;

    public Team getById(Long id) {
        Optional<Team> optTeam = teamRepository.findById(id);
        if (optTeam.isPresent()) {
            return optTeam.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public Page<Team> findAllTeams(
            @Min(message = "Le numéro de page ne peut être inférieur à 0", value = PAGE_MIN)
                    Integer page,
            @Min(value = PAGE_SIZE_MIN, message = PAGE_VALID_MESSAGE)
            @Max(value = PAGE_SIZE_MAX, message = PAGE_VALID_MESSAGE)
                    Integer size,
            String sortProperty,
            Sort.Direction sortDirection
    ) {
        //Vérification de sortProperty
        if(Arrays.stream(Team.class.getDeclaredFields()).
                map(Field::getName).
                filter(s -> s.equals(sortProperty)).count() != 1){
            throw new IllegalArgumentException("La propriété " + sortProperty + " n'existe pas !");
        }

        Pageable pageable = PageRequest.of(page,size,sortDirection, sortProperty);
        Page<Team> teams = teamRepository.findAllByOrderByTeamName(pageable);
        if(page >= teams.getTotalPages()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Le numéro de page ne peut être supérieur à " + teams.getTotalPages());
        } else if(teams.getTotalElements() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Il n'y a aucune équipe dans la base de données");
        }
        return teams;
    }

    public Long getIdInterneByIdApiExterne(Long idExt){
        return teamRepository.getIdByIdApiExtEquals(idExt);
    }
    public Team getByIdExt(Long idExt){
        return teamRepository.getTeamByIdApiExt(idExt);
    }

    public void saveAll(List<Team> listTeam){
        teamRepository.saveAll(listTeam);
    }
}
