package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.PassportPhoto;

import java.util.List;

public interface PassportService {

    Passport create(Passport passport);

    List<Passport> getAll();

    Passport getById(Integer id);

    List<Passport> getAllHanded();

    List<Passport> getAllNotHanded();

    Passport addPhoto(Integer passportId, String photoLink);

    Passport handPassport(Integer id, String photoLink);
}
