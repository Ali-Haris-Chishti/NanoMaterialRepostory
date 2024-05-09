package com.example.nmr.repository;

import com.example.nmr.model.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepo extends CrudRepository<Report, Integer> {
}
