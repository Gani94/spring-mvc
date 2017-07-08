package com.gani.services;

import com.gani.domain.DomainObject;

import java.util.*;

/**
 * Created by Gani on 7/7/17.
 */
public abstract class AbstractMapService {

    protected Map<Integer, DomainObject> domainObjectMap;

    public AbstractMapService(){
        domainObjectMap = new HashMap<>();
        loadDomainObjects();
    }


    public List<DomainObject> listAll(){
        return new ArrayList<>(domainObjectMap.values());
    }

    public DomainObject getById(Integer id){
        return domainObjectMap.get(id);
    }

    public DomainObject createOrUpdate(DomainObject domainObject){

        if(domainObject!=null){
            if(domainObject.getId()==null)
                domainObject.setId(getNextKey());
            domainObjectMap.put(domainObject.getId(),domainObject);
        }

        else{
            throw new RuntimeException("Object cannot be null");
        }

        return domainObject;
    }

    public void delete(Integer id){
        domainObjectMap.remove(id);
    }

    public Integer getNextKey(){
        return Collections.max(domainObjectMap.keySet())+1;
    }


    protected abstract void loadDomainObjects();
}
