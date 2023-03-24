package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.FootballerDto;
import com.owczarczak.footballers.match.MatchDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreNewDto {
    private Integer id;

    //todo DTO not Entity ! --> zamiast obiektu zrobić prostszy typ (kilka pól zamiast całego obiektu)
    private MatchDto matchDto;
    private FootballerDto footballerDto; //id, name
    private Integer minuteScored;

    public ScoreNewDto(Integer id, MatchDto matchDto, FootballerDto footballerDto, Integer minuteScored) {
        this.id = id;
        this.matchDto = matchDto;
        this.footballerDto = footballerDto;
        this.minuteScored = minuteScored;
    }


}
