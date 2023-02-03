package com.owczarczak.footballers.footballer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballerService {

    //TODO Add mapping from Footballer --> FootballerDto

//    private FootballerDto convertToDto(Footballer footballer) {
//        FootballerDto footballerDto = modelMapper.map(post, PostDto.class);
//        postDto.setSubmissionDate(post.getSubmissionDate(),
//                userService.getCurrentUser().getPreference().getTimezone());
//        return postDto;
//    }

    @Autowired
    FootballerRepository repository;

    //TODO the response is supposed to be mapped to DTO
    public List<FootballerDto> getAllFootballers() {
        return repository.findAll();
    }

    public Optional<FootballerDto> getFootballerById(@PathVariable int id) {

        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    public List<FootballerDto> get3HighestFootballers() {
        return repository.get3HighestFootballers();
    }

    public List<FootballerDto> getFootballersByName(String name) {
        return repository.findByName(name);
    }

    public FootballerDto addFootballer(@RequestBody @Valid FootballerDto footballerToAdd) {
        return repository.save(footballerToAdd);
    }

    public Optional<FootballerDto> updateFootballer(@PathVariable int id, @RequestBody FootballerDto footballerToUpdate) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        footballerToUpdate.setId(id);
        FootballerDto result = repository.save(footballerToUpdate);
        return Optional.of(result);
    }

    public boolean deleteFootballer(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
