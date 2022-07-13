package com.example.personmenegement.services;

import com.example.personmenegement.dao.PersonDAO;
import com.example.personmenegement.entity.PersonEntity;
import com.example.personmenegement.mapper.PersonMapper;
import com.example.personmenegement.soap.person.*;
import com.example.personmenegement.types.Status;
import com.example.personmenegement.validation.PersonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class PersonService {// todo добавить интерфейс
    private final PersonDAO personDao;
    private final PersonMapper personMapper;
    private final PersonValidation personValidation;
    private final ResourceBundle errorMsg = ResourceBundle.getBundle("message");// todo вынеси в константу

    public GetPersonByIdResponse getPersonById(Long id) {
        GetPersonByIdResponse response = new GetPersonByIdResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        PersonEntity personEntity = personDao.findPersonById(id);

        if (personEntity == null) {
            serviceStatus.setStatus(Status.ERROR.name());
            serviceStatus.setMessage(MessageFormat.format(errorMsg.getString("personNotFound"), id));// todo вынеси в константу
        } else {
            response.setPerson(personMapper.personEntityToPerson(personEntity));
            serviceStatus.setStatus(Status.SUCCESS.name());
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    public AddPersonResponse addNewPerson(Person person) {
        AddPersonResponse response = personValidation.addPersonValidator(person);

        if (response.getServiceStatus().getStatus().equals(Status.SUCCESS.name())) {
            response.getPerson().setId(String.valueOf(personDao
                    .addPerson(personMapper.personToPersonEntity(person))));
        }
        return response;
    }

    public UpdatePersonResponse updatePerson(Person person) {
        UpdatePersonResponse response = personValidation.updatePersonValidator(person);

        if (response.getServiceStatus().getStatus().equals(Status.SUCCESS.name())) {
            response.getPerson()
                    .setId(String.valueOf(personDao
                            .updatePerson(personMapper.personToPersonEntity(person))));
        }
        return response;
    }

    public DeletePersonResponse deletePerson(long id) {
        DeletePersonResponse response = new DeletePersonResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if (personDao.findPersonById(id) == null) {
            serviceStatus.setStatus(Status.ERROR.name());
            serviceStatus.setMessage(MessageFormat.format(errorMsg.getString("personNotFound"), id));// todo вынеси в константу
        } else {
            serviceStatus.setStatus(Status.SUCCESS.name());
            personDao.deletePersonById(id);
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
