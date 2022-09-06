package com.example.personmanagement.services.mapper;

import com.example.personmanagement.api.PersonMapper;
import com.example.personmanagement.api.TaskMapper;
import com.example.personmanagement.dto.PersonDto;
import com.example.personmanagement.dto.TaskDto;
import com.example.personmanagement.entity.PersonEntity;
import com.example.personmanagement.entity.TaskEntity;
import com.example.personmanagement.services.MessageService;
import com.example.personmanagement.types.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonMapperImp implements PersonMapper {

    private final MessageService messageService = new MessageService();
    private final TaskMapper taskMapper;

    public PersonDto personEntityToPerson(PersonEntity personEntity) {
        log.info("Was calling personEntityToPerson. Input personEntity: {}", personEntity);
        return PersonDto.builder()
                .id(getId(personEntity))
                .uid(personEntity.getUid())
                .name(personEntity.getName())
                .age(personEntity.getAge().toString())
                .email(personEntity.getEmail())
                .salary(personEntity.getSalary().toString())
                .position(messageService.getMessage(personEntity.getPosition()))
                .experience(personEntity.getExperience().toString())
                .tasks(getTasks(personEntity.getTasks()))
                .build();
    }

    public PersonEntity personToPersonEntity(PersonDto personDto) {
        log.info("Was calling personToPersonEntity. Input personDto: {}", personDto);
        return PersonEntity.builder()
                .id(getId(personDto))
                .uid(getUid(personDto))
                .name(personDto.getName().trim())
                .age(Integer.valueOf(personDto.getAge()))
                .email(personDto.getEmail().trim())
                .salary(new BigDecimal(personDto.getSalary()))
                .position(Position.definePosition(personDto.getPosition()))
                .experience(Double.valueOf(personDto.getExperience()))
                .build();
    }

    public PersonEntity personDtoAndPersonEntityToPersonEntity(PersonDto personDto, PersonEntity personEntity) {
        log.info("Was calling personDtoAndPersonEntityToPersonEntity. Input personDto: {} personEntity: {}", personDto, personEntity);
        return PersonEntity.builder()
                .id(personEntity.getId())
                .uid(personEntity.getUid())
                .name(personDto.getName().trim())
                .age(Integer.valueOf(personDto.getAge()))
                .email(personDto.getEmail().trim())
                .salary(new BigDecimal(personDto.getSalary()))
                .position(Position.definePosition(personDto.getPosition()))
                .experience(Double.valueOf(personDto.getExperience()))
                .build();
    }

    private Long getId(PersonDto personDto) {
        log.debug("Was calling getId.");
        if (personDto.getId() == null) {
            return null;
        }
        return Long.valueOf(personDto.getId());
    }

    private String getId(PersonEntity personEntity) {
        log.debug("Was calling getId.");
        if (personEntity.getId() == null) {
            return null;
        }
        return personEntity.getId().toString();
    }

    private List<TaskDto> getTasks(List<TaskEntity> taskEntities) {
        log.debug("Was calling getTasks.");
        if (taskEntities == null) {
            return null;
        }
        return taskEntities
                .stream()
                .map(taskMapper::taskEntityToTask)
                .collect(Collectors.toList());
    }

    private String getUid(PersonDto personDto) {
        log.debug("Was calling getUid. Input personDto: {}", personDto);
        if (personDto.getUid() == null) {
            return UUID.randomUUID().toString();
        }
        return personDto.getUid();
    }
}