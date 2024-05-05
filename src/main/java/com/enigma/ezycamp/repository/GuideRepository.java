package com.enigma.ezycamp.repository;

import com.enigma.ezycamp.entity.Customer;
import com.enigma.ezycamp.entity.Guide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, String> {
    @Query("select g.* from m_guide as g join m_user_account as ua on ua.id = g.user_account_id where g.id = :id and ua.is_enable = true")
    Optional<Guide> findByIdGuide(@Param("id") String id);

    @Query("select g.* from m_guide as g join m_user_account as ua on ua.id = g.user_account_id where g.guide_name like :name and ua.is_enable = true")
    Page<Guide> findByNameGuide(@Param("name") String name, Pageable pageable);

    @Query("select g.* from m_guide as g join m_user_account as ua on ua.id = g.user_account_id where ua.is_enable = true")
    Page<Guide> findAllGuide(Pageable pageable);
}
