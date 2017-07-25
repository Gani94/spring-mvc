package com.gani.services.mapServices;

import com.gani.domain.DomainObject;
import com.gani.domain.Role;
import com.gani.services.RoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Gani on 7/21/17.
 */

@Service
@Profile("mapService")
public class RoleServiceMapImpl extends AbstractMapService implements RoleService{

    @Override
    public Role createOrUpdate(Role domainObject) {

        return (Role) super.createOrUpdate(domainObject);
    }

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Role getById(Integer id) {
        return (Role) super.getById(id);
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
