package it.jobhunt.JobHunt.service;

import it.jobhunt.JobHunt.exception.DefaultException;
import it.jobhunt.JobHunt.exception.NotFoundException;
import org.springframework.data.domain.Page;

public interface CrudOperation<Entity, EntityHelper, CreateEntityHelper, EntityFilter> {

    Page<EntityHelper> findAll(EntityFilter helper) throws DefaultException;

    EntityHelper create(CreateEntityHelper helper) throws DefaultException;

    EntityHelper get(Long id) throws NotFoundException, DefaultException;

    EntityHelper edit(EntityHelper helper) throws DefaultException, NotFoundException;

    void delete(Long id) throws DefaultException, NotFoundException;
}