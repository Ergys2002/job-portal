package com.exh.jobseeker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employers")
public class EmployerController {
    //TODO: Implement setup employer for a user then test company because company can not be created when employer does not exist, also remove path field from ErrorResponse
}
