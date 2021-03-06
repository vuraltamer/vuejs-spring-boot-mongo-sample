package com.project.main.service.base;

import com.project.main.common.model.BaseRequest;
import com.project.main.common.model.SearchRequest;
import com.project.main.common.model.SearchResponse;
import com.project.main.service.dto.base.BaseDto;
import com.project.main.data.document.base.BaseDocument;
import com.project.main.data.repository.base.BaseRepository;
import com.project.main.common.constant.Constants;
import com.project.main.util.ApplicationUtil;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseService<E extends BaseDocument, D extends BaseDto, PK extends Serializable> {

    /*
    * Get BaseRepository
    *
    * */
    public abstract BaseRepository<E, PK> getRepository();

    /*
     * Generic save-update-delete-all methods for E document
     *
     * */
    public D get(PK id) {
        final Optional<E> document = getRepository().findById(id);
        return !document.isEmpty() ? (D) document.get().toDto() : null;
    }

    public E save(E document) {
        document.setId(generateNextIdValue());
        document.setCreateDate(new Date());
        return getRepository().save(document);
    }

    public E update(E document) {
        document.setUpdateDate(new Date());
        return getRepository().save(document);
    }

    public void delete(Integer id) {
        getRepository().deleteById((PK) id);
    }

    public SearchResponse<D> all(BaseRequest request) {
        final Page<E> documentPage = getRepository().findAll(request.pageRequest());
        final List<D> dtoList = (List<D>) toDto(documentPage.toList());
        final int elementCount = Long.valueOf(documentPage.getTotalElements()).intValue();
        return new SearchResponse<D>(request.getPagination().getCurrentPage(), elementCount, dtoList);
    }

    private synchronized Integer generateNextIdValue() {
        final PageRequest pageable = PageRequest.of(0, 1, ApplicationUtil.sortDescending(Constants.ID));
        final Optional<E> first = getRepository().findAll(pageable).stream().findFirst();
        final Integer id = first.isEmpty() ? 0 : first.get().getId();
        return Math.addExact(id, 1);
    }

    /**
     * Generic toDto converter for list
     * @param entities
     * @return
     */
    public List<? extends BaseDto> toDto(List<? extends BaseDocument> entities) {
        try {
            List<BaseDto> resources = new ArrayList<>();
            for (BaseDocument t : entities) {
                resources.add(t.toDto());
            }
            return resources;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generic toDocument converter for list
     * @param dtoList
     * @return
     */
    public List<? extends BaseDocument> toDocument(List<? extends BaseDto> dtoList) {
        try {
            List<BaseDocument> entities = new ArrayList<>();
            for (BaseDto t : dtoList) {
                entities.add(t.toDocument());
            }
            return entities;
        } catch (Exception e) {
            return null;
        }
    }

    public abstract SearchResponse<? extends BaseDto> search(SearchRequest searchRequest);
}
