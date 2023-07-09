package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportPhoto;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.PassportHandedException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PassportServiceBean implements PassportService {

    private final PassportRepository passportRepository;

    @Override
    public Passport create(Passport passport) {
        return passportRepository.save(passport);
    }

    @Override
    public List<Passport> getAll() {
        return passportRepository.findAll();
    }

    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Passport> getAllHanded() {
        return passportRepository.findAllByIsHandedTrue().orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Passport> getAllNotHanded() {
        return passportRepository.findAllByIsHandedFalse().orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Passport addPhoto(Integer passportId, String photoLink) {
        Passport passport = getById(passportId);
        PassportPhoto photo = Optional.ofNullable(passport.getPhoto())
                .orElse(PassportPhoto.builder().passport(passport).build());
        photo.setPhotoLink(photoLink);
        passport.setPhoto(photo);
        return passportRepository.save(passport);
    }

    @Override
    public Passport handPassport(Integer passportId, String photoLink) throws PassportHandedException {
        Passport passport = addPhoto(passportId, photoLink);
        if (passport.isHanded()) {
            throw new PassportHandedException();
        }
        passport.setHanded(true);
        return passportRepository.save(passport);
    }
}
