package com.owczarczak.footballers.clubRepresentation;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepresentationRepository extends JpaRepository<ClubRepresentation, Integer> {
//    @Query("SELECT c FROM ClubRepresentation c where c.club_id = :clubId")
//    ClubRepresentation findClubByClubId(@Param("clubId") int clubId);
}