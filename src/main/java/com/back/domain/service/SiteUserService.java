package com.back.domain.service;

import com.back.domain.repository.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteUserService {
    @Autowired
    private SiteUserRepository siteUserRepository;

}