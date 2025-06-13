package ee.mihkel.veebipood.controller;

import com.github.vladislavgoltjajev.personalcode.exception.PersonalCodeException;
import ee.mihkel.veebipood.dto.AuthToken;
import ee.mihkel.veebipood.dto.EmailPassword;
import ee.mihkel.veebipood.dto.PersonDTO;
import ee.mihkel.veebipood.entity.Person;
import ee.mihkel.veebipood.entity.PersonRole;
import ee.mihkel.veebipood.repository.PersonRepository;
import ee.mihkel.veebipood.security.JwtUtil;
import ee.mihkel.veebipood.service.EmailService;
import ee.mihkel.veebipood.util.EstonianPersonalCodeValidatorFactory;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@CrossOrigin("http://localhost:4200")
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    JwtUtil jwtUtil;

//    @Autowired
//    EstonianPersonalCodeValidator validator;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailService emailService;

    @GetMapping("persons")
    public List<Person> persons() {
        return personRepository.findAll();
    }

//    @GetMapping("public-persons")
//    public List<PersonDTO> publicPersons() {
//        List<Person> persons = personRepository.findAll();
//        List<PersonDTO> personDTOs = new ArrayList<>();
//        for (Person p: persons) {
//            PersonDTO personDTO = new PersonDTO();
//            personDTO.setFirstName(p.getFirstName());
//            personDTO.setLastname(p.getLastname());
//            personDTO.setEmail(p.getEmail());
//            personDTOs.add(personDTO);
//        }
//        return personDTOs;
//    }

    @GetMapping("public-persons")
    public List<PersonDTO> publicPersons() {
        log.info(modelMapper);
        System.out.println(modelMapper);
        List<Person> persons = personRepository.findAll();
        return List.of(modelMapper.map(persons, PersonDTO[].class));
    }

    @GetMapping("person")
    public Person person() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return personRepository.findByEmail(email);
    }

//    private final EstonianPersonalCodeValidator validator = new EstonianPersonalCodeValidator();

    @PostMapping("signup")
    public List<Person> signup(@RequestBody Person person) throws PersonalCodeException {
        log.info("Signuping person {}", person);
        if (!EstonianPersonalCodeValidatorFactory.getValidator().isValid(person.getPersonalCode())) {
            throw new PersonalCodeException("Isikukood pole õige!"); // see ei tule front-endi vaid ainult logides
        }
        if (person.getEmail().isEmpty()) {
            throw new RuntimeException("Email is missing");
        }
        if (person.getPassword().isEmpty()) {
            throw new RuntimeException("Password is missing");
        }
        try {
            person.setRole(PersonRole.CUSTOMER);
            personRepository.save(person);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email on juba olemas");
        }
        emailService.sendEmail();
        return personRepository.findAll();
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody EmailPassword emailPassword) {
        Person person = personRepository.findByEmail(emailPassword.getEmail());
        if (person == null) {
            throw new RuntimeException("Sellise emailiga kasutajat ei eksisteeri");
        }
        if (!person.getPassword().equals(emailPassword.getPassword())) {
            throw new RuntimeException("Parool on vale");
        }
        return jwtUtil.getToken(person);
    }

    @PatchMapping("change-admin")
    public List<Person> persons(@RequestParam Long personId) {
        Person person = personRepository.findById(personId).orElseThrow();
        if (person.getRole().equals(PersonRole.CUSTOMER)) {
            person.setRole(PersonRole.ADMIN);
        } else {
            person.setRole(PersonRole.CUSTOMER);
        }
        personRepository.save(person);
        return personRepository.findAll();
    }

}
