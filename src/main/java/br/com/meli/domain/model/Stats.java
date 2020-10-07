package br.com.meli.domain.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stats {

	private BigDecimal count_mutant_dna;
	private BigDecimal count_human_dna;
	private BigDecimal ratio;
}
