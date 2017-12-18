package org.nhsrc.repository.security;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.BaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends BaseRepository<User> {
    User findByEmail(String email);
}