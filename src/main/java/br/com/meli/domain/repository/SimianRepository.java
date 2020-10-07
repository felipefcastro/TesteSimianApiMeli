package br.com.meli.domain.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meli.domain.model.Simian;

public interface SimianRepository extends JpaRepository<Simian, Long>{
	
	Optional<Simian> findByDna(String dna);
	
	 BigDecimal countByIsSimianTrue();
	 
	 BigDecimal countByIsSimianFalse();
}
