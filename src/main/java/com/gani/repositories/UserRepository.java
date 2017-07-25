package com.gani.repositories;

import com.gani.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gani on 7/24/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
