package com.cts.processPension.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.processPension.model.PensionerInput;

@Repository
public interface PensionerDetailsRepository extends JpaRepository<PensionerInput, String> {

}
