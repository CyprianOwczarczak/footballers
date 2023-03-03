package com.owczarczak.footballers.footballer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

public interface FootballerRepository extends JpaRepository<Footballer, Integer> {

    @Query("SELECT a FROM Footballer a ORDER BY a.height DESC")
    Page<Footballer> findAllByOrderByHeightDesc(Pageable pageable);

    @Query("SELECT f FROM Footballer f WHERE f.name = :name")
    List<Footballer> findByName(@Param("name") String name);

    @Query("""
            SELECT
            CASE
                WHEN COUNT(f) > 0 THEN true
                ELSE false
            END
            FROM Footballer f
            WHERE
            f.pesel = :pesel
            """)
    boolean existsByPesel(@Param("pesel") String pesel);

    @Modifying
    @Query("DELETE FROM Footballer f WHERE f.id = :id")
    void deleteAllById(@Param("id") int id);

//- Który piłkarz pojawił się w największej liczbie meczów
    @Query("""
            select f
            from Footballer f
            join Contract c
            on c.footballer = f
            """)

    Footballer whichFootballerPlayedInTheBiggestAmountOfMatches();


////- Średnia liczba goli zdobyta przez każdego zawodnika na mecz np.Piłkarz1 zdobył 6,5 gola na mecz (i wypisać wszystkich)
//    @Query("""
//            select f
//            from footballer f
//            """)
//    List<Footballer> getMeanValueOfGoalsScoredByEveryFootballerPerMatch();

}
