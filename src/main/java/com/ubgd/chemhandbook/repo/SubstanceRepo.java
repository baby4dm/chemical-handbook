package com.ubgd.chemhandbook.repo;

import com.ubgd.chemhandbook.model.Substance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubstanceRepo extends JpaRepository<Substance, Integer>, JpaSpecificationExecutor<Substance> {
    @Query("SELECT s FROM Substance s WHERE trim(lower(s.name)) = trim(lower(:name))")
    Optional<Substance> findByName(@Param("name") String name);
    @Query("SELECT s FROM Substance s WHERE trim(lower(s.haz)) = trim(lower(:haz))")
    Optional<Substance> findByHaz(@Param("haz") String hazCode);
    @Query("SELECT s FROM Substance s WHERE trim(lower(s.formula)) = trim(lower(:formula))")
    Optional<Substance> findByFormula(@Param("formula") String formula);
    @Query("SELECT s FROM Substance s WHERE trim(s.imdg) = trim(:imdg)")
    Optional<Substance> findByImdg(@Param("imdg") String imdgCode);
    @Query("SELECT s.oonNumber FROM Substance s")
    List<Integer> getAllIds();
}
