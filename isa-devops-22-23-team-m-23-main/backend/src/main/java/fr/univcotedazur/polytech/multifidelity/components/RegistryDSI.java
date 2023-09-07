package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.DSIMember;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.VerifFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.DSIFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.DsiRegistration;
import fr.univcotedazur.polytech.multifidelity.repositories.DSIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RegistryDSI implements DsiRegistration, DSIFinder {

    private final DSIRepository dsiRepository;

    @Autowired
    public RegistryDSI(DSIRepository dsiRepository) {
        this.dsiRepository = dsiRepository;
    }
    @Override
    public Optional<DSIMember> findByEmail(String name) {
        return dsiRepository.findByEmail(name);
    }

    @Override
    public Optional<DSIMember> findById(Long id) {
        return dsiRepository.findById(id);
    }

    @Override
    public DSIMember[] findAll() {
        List<DSIMember> dsiMembers = new ArrayList<>();
        for (DSIMember dsiMember : dsiRepository.findAll()) dsiMembers.add(dsiMember);
        return dsiMembers.toArray(new DSIMember[0]);
    }
    @Override
    public DSIMember registerDSI(String name, String mail, String password) throws VerifFailedException {
        if(findByEmail(mail).isPresent()) throw new VerifFailedException("DSI with this email already exist");
        DSIMember dsiMember = new DSIMember(name, mail, crypt(password));
        dsiRepository.save(dsiMember, dsiMember.getId());
        return dsiMember;
    }

    @Override
    public DSIMember loginDSI(String mail, String password) throws BadInformationException {
        Optional<DSIMember> dsiMember = findByEmail(mail);
        if(dsiMember.isPresent() && dsiMember.get().getPassword().equals(crypt(password))) {
            return dsiMember.get();
        }
        throw new BadInformationException("Bad credentials");
    }

    @Override
    public DSIMember registerDSI(DSIMember dsiMember) throws VerifFailedException {
        return this.registerDSI(dsiMember.getName(), dsiMember.getEmail(), dsiMember.getPassword());
    }

    String crypt(String password) {
        // hash password with SHA-256
        try {
            System.out.println("password = " + password);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder s = new StringBuilder();
            for (byte b : hash) s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
