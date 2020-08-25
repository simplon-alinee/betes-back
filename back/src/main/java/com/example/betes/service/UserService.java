package com.example.betes.service;

import com.example.betes.exception.ResourceNotFoundException;
import com.example.betes.model.User;
import com.example.betes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;


@Service
public class UserService {

    public static final int PAGE_SIZE_MIN = 10;
    public static final int PAGE_SIZE_MAX = 100;
    public static final int PAGE_MIN = 0;
    private static final String PAGE_VALID_MESSAGE = "La taille de la page doit être comprise entre 10 et 100";


    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent() ) { return optUser.get();} else {throw new ResourceNotFoundException();
        }
    }

    public Page<User> findAllUsers(
            @Min(message = "Le numéro de page ne peut être inférieur à 0", value = PAGE_MIN)
                    Integer page,
            @Min(value = PAGE_SIZE_MIN, message = PAGE_VALID_MESSAGE)
            @Max(value = PAGE_SIZE_MAX, message = PAGE_VALID_MESSAGE)
                    Integer size,
            String sortProperty,
            Sort.Direction sortDirection
    ) {
        //Vérification de sortProperty
        if(Arrays.stream(User.class.getDeclaredFields()).
                map(Field::getName).
                filter(s -> s.equals(sortProperty)).count() != 1){
            throw new IllegalArgumentException("La propriété " + sortProperty + " n'existe pas !");
        }

        Pageable pageable = PageRequest.of(page,size,sortDirection, sortProperty);
        Page<User> users = userRepository.findAllByOrderByUsernameAsc(pageable);
        if(page >= users.getTotalPages()){
            throw new IllegalArgumentException("Le numéro de page ne peut être supérieur à " + users.getTotalPages());
        } else if(users.getTotalElements() == 0){
            throw new EntityNotFoundException("Il n'y a aucun user dans la base de données");
        }
        return users;
    }


}
