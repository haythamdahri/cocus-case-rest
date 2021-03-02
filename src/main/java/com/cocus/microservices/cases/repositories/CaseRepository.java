package com.cocus.microservices.cases.repositories;

import com.cocus.microservices.bo.entities.CaseBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Haytham DAHRI
 */
@Repository
public interface CaseRepository extends JpaRepository<CaseBO, Long> {

    @Query(value = "UPDATE cases SET customer_id = :customerId WHERE id = :caseId", nativeQuery = true)
    @Modifying
    @Transactional
    void updateCaseCustomer(@Param("caseId") Long caseId, @Param("customerId") Long customerId);

    List<CaseBO> findByCustomer_Username(String username);

}
