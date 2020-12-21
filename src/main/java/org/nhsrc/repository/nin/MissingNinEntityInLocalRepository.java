package org.nhsrc.repository.nin;


import org.nhsrc.domain.nin.FacilityEntityType;
import org.nhsrc.domain.nin.MissingNinEntityInLocal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MissingNinEntityInLocalRepository extends PagingAndSortingRepository<MissingNinEntityInLocal, Integer> {
    MissingNinEntityInLocal findByNameAndType(String name, FacilityEntityType type);
}