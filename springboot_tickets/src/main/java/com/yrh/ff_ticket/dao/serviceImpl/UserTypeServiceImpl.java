package com.yrh.ff_ticket.dao.serviceImpl;

import com.yrh.ff_ticket.dao.repository.UserTypeRepository;
import com.yrh.ff_ticket.dao.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    UserTypeRepository userTypeRepository;
}
