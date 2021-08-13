package com.example.pfe.repository;

import com.example.pfe.entity.DisplayEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayRepository extends JpaRepository<DisplayEntity, Long> {


    @Query("select t from DisplayEntity t where t.progName like :keyword or t.display like :keyword")
    Page<DisplayEntity> filter(@Param("keyword") String keyword, Pageable pageable);

    @Query("select t from DisplayEntity t where  trim(t.progName) = :progName and trim(t.display) = :display")
    DisplayEntity find(@Param("progName") String progName, @Param("display") String display);

}
