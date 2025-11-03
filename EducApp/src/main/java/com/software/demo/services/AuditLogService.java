package com.software.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.software.demo.entities.AuditLog;
import com.software.demo.repositories.AuditLogRepository;

@Service
public class AuditLogService {
    
    @Autowired
    private AuditLogRepository repo;

    public AuditLog log(String action, String operador, String details) {
        AuditLog a = new AuditLog();
        a.setAction(action);
        a.setOperador(operador);
        a.setDetails(details);
        return repo.save(a);
    }
}
