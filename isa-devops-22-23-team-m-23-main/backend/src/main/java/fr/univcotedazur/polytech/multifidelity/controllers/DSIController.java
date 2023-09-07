package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.DSIMember;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.VerifFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.DSIFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.DsiRegistration;
import fr.univcotedazur.polytech.multifidelity.repositories.DSIRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = DSIController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class DSIController {
    static final String BASE_URI = "/DSI";
    static Logger logger = org.slf4j.LoggerFactory.getLogger(DSIRepository.class);
    @Autowired
    DsiRegistration dsiRegistration;

    @Autowired
    DSIFinder dsiFinder;

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DSIMember> register(@RequestBody @Valid DSIMember dsiMember) {
        try{
            logger.info("DSI member a enregistrer: " + dsiMember + " " + dsiMember.getName()+" "+dsiMember.getEmail() + " " + dsiMember.getPassword());
            DSIMember newDSIMember = dsiRegistration.registerDSI(dsiMember);
            logger.info("DSI member registered: " + newDSIMember);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDSIMember);
        } catch (VerifFailedException e) {
            logger.error("DSI member registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = "login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<DSIMember> login(@RequestBody @Valid Map<String, String> data) {
        try{
            DSIMember dsiMember = dsiRegistration.loginDSI(data.get("mail"), data.get("password"));
            logger.info("DSI member logged in: " + dsiMember);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(dsiMember);
        } catch (BadInformationException e) {
            logger.error("DSI member login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(path = "getAll")
    public ResponseEntity<DSIMember[]> getAll() {
        DSIMember[] dsiMembers = dsiFinder.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dsiMembers);
    }
}
