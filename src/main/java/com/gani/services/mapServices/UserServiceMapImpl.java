package com.gani.services.mapServices;

import com.gani.domain.DomainObject;
import com.gani.domain.User;
import com.gani.services.UserService;
import com.gani.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gani on 7/13/17.
 */

@Service
@Profile("mapService")
public class UserServiceMapImpl extends AbstractMapService implements UserService {

    private EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public User getById(Integer id) {
        return (User) super.getById(id);
    }

    @Override
    public User createOrUpdate(User user) {

        if(user.getPassword()!=null){
            user.setEncryptedPassword(
                                        encryptionService.encryptString(user.getPassword()));
        }

        return (User) super.createOrUpdate(user);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public Integer getNextKey() {
        return super.getNextKey();
    }
}
