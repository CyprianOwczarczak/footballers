package com.owczarczak.footballers.footballer;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FootballerService {

    @Autowired
    FootballerRepository repository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        List<Footballer> footballerList = repository.findAll();

        //Load file and compile it
        File file = ResourceUtils.getFile("class[ath:footballers.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(footballerList);

        //We fill the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "ExamplePerson");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.contentEquals("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "C:\\Users\\Cyprian\\Desktop\\Reports" + "\\footballers.html");
        }

        if (reportFormat.contentEquals("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Cyprian\\Desktop\\Reports" + "\\footballers.pdf");
        }

        return "report generated !";
    }

    public List<FootballerDto> getAllFootballers() {
        List<Footballer> footballersList = repository.findAll();
        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public Optional<FootballerDto> getFootballerById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Footballer footballer = repository.getReferenceById(id);
            return Optional.ofNullable(FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build());
        }
    }

    public List<FootballerDto> getXHighestFootballers(int pageNumber, int numberOfPlayers) {
        Pageable pageable = PageRequest.of(pageNumber, numberOfPlayers);
        Page<Footballer> footballersList = repository.findAllByOrderByHeightDesc(pageable);

        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public List<FootballerDto> getFootballersByName(String name) {
        List<Footballer> footballersList = repository.findByName(name);
        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public FootballerDto addFootballer(FootballerDto footballerToBeAdded) {
        Footballer newFootballer = Footballer.builder()
                .pesel(footballerToBeAdded.getPesel())
                .name(footballerToBeAdded.getName())
                .height(footballerToBeAdded.getHeight())
                .build();
        Footballer savedEntity = repository.save(newFootballer);
        return FootballerDto.builder()
                .id(savedEntity.getId())
                .pesel(savedEntity.getPesel())
                .name(savedEntity.getName())
                .height(savedEntity.getHeight())
                .build();
    }

    public Optional<FootballerDto> updateFootballer(int id, FootballerDto footballerToBeUpdated) {
        Footballer updatedFootballer = Footballer.builder()
                .id(footballerToBeUpdated.getId())
                .pesel(footballerToBeUpdated.getPesel())
                .name(footballerToBeUpdated.getName())
                .height(footballerToBeUpdated.getHeight())
                .build();
        Footballer savedEntity = repository.save(updatedFootballer);
        return Optional.of(FootballerDto.builder()
                .id(savedEntity.getId())
                .pesel(savedEntity.getPesel())
                .name(savedEntity.getName())
                .height(savedEntity.getHeight())
                .build());
    }

    //Returns FootballerDtoMostMatches with id only
    public List<FootballerDtoMostMatches> getFootballersWhoPlayedInMostMatches() {
        List<Integer[]> footballersList = repository.whichFootballersPlayedInMostMatches();
        List<FootballerDtoMostMatches> dtos = new LinkedList<>();
        for (Integer[] integers : footballersList) {
            FootballerDtoMostMatches dtoToBeAdded = FootballerDtoMostMatches.builder()
                    .id(integers[0])
                    .representationSize(integers[1])
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

//        List<ScoreAvgGoalsDto> getAvgGoalsPerMatchForFootballer() {
//            List<Object[]> returnedListOfArrays = repository.getAvgGoalsPerMatchForFootballer();
//            List<ScoreAvgGoalsDto> dtos = new LinkedList<>();
//            for (Object[] row : returnedListOfArrays) {
//                ScoreAvgGoalsDto dtoToBeAdded = ScoreAvgGoalsDto.builder()
//                        .id((Integer) row[0])
//                        .footballerName((String) row[1])
//                        .numberOfGoals((BigInteger) row[2])
//                        .numberOfMatches((BigInteger) row[3])
//                        .averageGoals((Double) row[4])
//                        .build();
//                dtos.add(dtoToBeAdded);

    @Transactional
    public void deleteFootballer(@PathVariable int id) {
        repository.deleteAllById(id);
    }

    public boolean existsByPesel(String pesel) {
        return repository.existsByPesel(pesel);
    }
}
