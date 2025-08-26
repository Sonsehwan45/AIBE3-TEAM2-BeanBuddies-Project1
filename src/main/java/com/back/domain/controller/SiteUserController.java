package com.back.domain.controller;

import com.back.domain.service.SiteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SiteUserController {
    @Autowired
    private SiteUserService siteUserService;
}