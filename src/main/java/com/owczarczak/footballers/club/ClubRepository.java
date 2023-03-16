package com.owczarczak.footballers.club;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    @Query("SELECT c FROM Club c WHERE c.name = :name")
    List<Club> findByName(@Param("name") String name);

    @Query("SELECT c FROM Club c ORDER BY c.created DESC")
    Page<Club> findAllByOrderByCreatedDesc(Pageable pageable);

    //Wszystkie kluby które zagrały więcej niż 3 mecze
    @Query("""
            select c
            from Club c
            join ClubRepresentation cr
            on cr.club = c
            where (select count(cr.club_id) from cr) > 3
            group by c.id
             """)

//    where club_id in
//            (select club_id
//	from club_representation
//            group by club_id
//            having count(*) > 3)
//    group by c.id
    List<Club> getAllClubsWhichPlayedMoreThan3Matches();


//    @Query("""
//            select c
//            from Club c
//            join ClubRepresentation cr
//            on cr.club = c
//            where (select count(cr.club) from cr) > 3
//            group by c.id
//             """)
}
