package org.nhsrc.repository.nin;

import org.nhsrc.domain.nin.NinSyncDetails;
import org.nhsrc.domain.nin.NinSyncType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface NinSyncDetailsRepository extends PagingAndSortingRepository<NinSyncDetails, Integer> {
    NinSyncDetails findByType(NinSyncType type);
}