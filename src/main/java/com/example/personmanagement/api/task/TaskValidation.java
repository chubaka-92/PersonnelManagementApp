package com.example.personmanagement.api.task;

import com.example.personmanagement.dto.TaskDto;

public interface TaskValidation {

    TaskDto validate(TaskDto taskDto);
}