package com.cocus.microservices.cases.repositories;

import com.cocus.microservices.bo.entities.CaseBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
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

    @Query(value = "SELECT c FROM CaseBO c WHERE c.customer.username = :username AND c.content LIKE %:search%")
    Page<CaseBO> findCustomerCases(@Param("username") String username,
                                   @Param("search") String search, @PageableDefault Pageable pageable);

    @Query(value = "SELECT c FROM CaseBO c WHERE c.customer.username = :username")
    Page<CaseBO> findCustomerCases(String username, @PageableDefault Pageable pageable);

}
