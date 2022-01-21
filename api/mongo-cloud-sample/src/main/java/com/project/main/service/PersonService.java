package com.project.main.service;

import com.project.main.common.model.SearchRequest;
import com.project.main.common.model.SearchResponse;
import com.project.main.service.dto.PersonDto;
import com.project.main.data.document.Person;
import com.project.main.data.repository.PersonRepository;
import com.project.main.data.repository.base.BaseRepository;
import com.project.main.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.main.util.ApplicationUtil.getMatchingAllContainsStringMatcher;

@Service
@RequiredArgsConstructor
public class PersonService extends BaseService<Person, PersonDto, Integer> {

    private final PersonRepository repository;

    @Override
    public BaseRepository<Person, Integer> getRepository() {
        return repository;
    }

    @Override
    public SearchResponse<PersonDto> search(SearchRequest searchRequest) {
        final PersonDto searchModel = (PersonDto) searchRequest.getSearchDto();
        final Example<Person> example = Example.of(searchModel.toDocument(), getMatchingAllContainsStringMatcher());
        final Page<Person> personPage = repository.findAll(example, searchRequest.pageRequest());
        final List<PersonDto> personDtoList = (List<PersonDto>) toDto(personPage.toList());
        final int elementCount = Long.valueOf(personPage.getTotalElements()).intValue();
        return new SearchResponse<>(searchRequest.getPagination().getCurrentPage(), elementCount, personDtoList);
    }
}
