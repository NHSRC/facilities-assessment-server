package org.nhsrc.repository.security;

import org.nhsrc.domain.security.User;
import org.nhsrc.repository.NonTxDataRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
@PreAuthorize("hasRole('Users_Write')")
public interface UserRepository extends NonTxDataRepository<User> {
    @PreAuthorize("permitAll()")
    User findByEmail(String email);
}