package com.owczarczak.footballers.footballer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FootballerControllerTest {

    private final FootballerRepository mockRepository = mock(FootballerRepository.class);
    private final FootballerService mockService = mock(FootballerService.class);
//	private final FootballerMapper mapper = new FootballerMapper();

    private List<Footballer> footballerList = new ArrayList<>();

    @Test
    @DisplayName("Should add a footballer")
    void shouldAddFootballer() {
    }

}