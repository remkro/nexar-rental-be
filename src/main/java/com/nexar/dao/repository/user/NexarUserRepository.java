package com.nexar.dao.repository.user;

import com.nexar.dao.model.user.NexarUser;
import com.nexar.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NexarUserRepository extends AbstractRepository<NexarUser> {
    Optional<NexarUser> findOneByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
}
