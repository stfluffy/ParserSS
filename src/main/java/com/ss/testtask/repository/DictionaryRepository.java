package com.ss.testtask.repository;

import com.ss.testtask.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    Dictionary findByUrl(String url);
}
