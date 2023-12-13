package ru.petapp.taskmenagementsystem.taskmenagment.constants;

import java.util.List;

public interface SecurityConstants {


    List<String> TASKS_WHITE_LIST = List.of("/rest/tasks",
                                                              "/rest/tasks/search",
                                                              "/rest/tasks/{id}");


    List<String> USERS_WHITE_LIST = List.of("/rest/users/auth",
                                                              "/rest/users/registration",
                                                              "/rest/users/remember-password",
                                                              "/rest/users/change-password");

    List<String> TASKS_PERMISSION_LIST = List.of("/rest/tasks/add",
                                                                   "/rest/tasks/update",
                                                                   "/rest/tasks/delete/**",
                                                                   "/rest/tasks/delete/{id}");
        
    List<String> USERS_PERMISSION_LIST = List.of("/rest/rent/task/*");


    List<String> RESOURCES_WHITE_LIST = List.of("/swagger-ui/**",
                                                "/webjars/bootstrap/5.0.2/**",
                                                "/v3/api-docs/**",
                                                "/error");



}
