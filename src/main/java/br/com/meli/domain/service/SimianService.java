package br.com.meli.domain.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.meli.domain.exception.DnaNaoPermitidoException;
import br.com.meli.domain.exception.DnaUtilizadoException;
import br.com.meli.domain.model.Simian;
import br.com.meli.domain.model.Stats;
import br.com.meli.domain.repository.SimianRepository;

@Service
public class SimianService {

	public static final String ALLOWED_LETTERS = "[^A,T,G,C]";
	
	@Autowired
	private SimianRepository simianRepository;

	public Simian salvar(Simian dna) {

		Optional<Simian> dnaExistente = simianRepository.findByDna(dna.getDna());

		if (dnaExistente.isPresent()) {
			throw new DnaUtilizadoException(String.format(
					"DNA %s já está cadastrado no banco de dados! Por favor, insira outro valor.", dna.getDna()));
		}

		return simianRepository.save(dna);

	}

	public void converterParaSalvar(String[] dna, boolean isSimian) {
		String sequenciaDna = "";

		for (int i = 0; i < dna.length; i++) {
			if(sequenciaDna.equals("")) {
				sequenciaDna += dna[i];	
			}else {
				sequenciaDna += "," + dna[i];
			}
		}

		Simian simian = new Simian();
		simian.setDna(sequenciaDna);
		simian.setSimian(isSimian);

		salvar(simian);
	}


	public Boolean isSimian(String[] dna) {
		
		validarDnaRecebido(dna);

		String diagonal = "";
		String vertical = "";
		String horizontal = "";
		int tamanho = dna.length;
		boolean isSimian = false;

		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if (i < tamanho - 3) {
					if (j < tamanho - 3) {
						char gen = dna[i].charAt(j);
						diagonal += gen;
						diagonal += dna[i + 1].charAt(j + 1);
						diagonal += dna[i + 2].charAt(j + 2);
						diagonal += dna[i + 3].charAt(j + 3);
						String subDiagonal = diagonal.replace(String.valueOf(gen), "");
						if (subDiagonal.length() == 0) {
							isSimian = true;
						}
					}
					char gen = dna[i].charAt(j);
					vertical += gen;
					vertical += dna[i + 1].charAt(j);
					vertical += dna[i + 2].charAt(j);
					vertical += dna[i + 3].charAt(j);
					String subVertical = vertical.replace(String.valueOf(gen), "");
					if (subVertical.length() == 0) {
						isSimian = true;
					}
				}
				if (j < tamanho - 3) {
					char gen = dna[i].charAt(j);
					horizontal += gen;
					horizontal += dna[i].charAt(j + 1);
					horizontal += dna[i].charAt(j + 2);
					horizontal += dna[i].charAt(j + 3);
					String subHorizonal = horizontal.replace(String.valueOf(gen), "");
					if (subHorizonal.length() == 0) {
						isSimian = true;
					}
				}
			}
		}

		converterParaSalvar(dna, isSimian);

		return isSimian;
	}
	
	private void validarDnaRecebido(String[] dna) {
		if(dna == null || dna.length == 0) {
        	throw new DnaNaoPermitidoException(String.format(
					"DNA não pode ser nulo/vazio"));
        }

        for (int i = 0; i < dna.length ; i++) {
            if (!valoresValidos(dna[i])){
            	throw new DnaNaoPermitidoException(String.format(
    					"DNA não permitido! Favor utilizar apenas as letras: A, T, C e G."));
            }
        }
    }

    public boolean valoresValidos(String sequence) {
        Pattern pattern = Pattern.compile(ALLOWED_LETTERS);
        Matcher matcher = pattern.matcher(sequence);

        return !matcher.find();
    }
	
	 public Stats getStatsReport() {
	        BigDecimal humanDna = simianRepository.countByIsSimianFalse();
	        BigDecimal mutantDna = simianRepository.countByIsSimianTrue();
	        BigDecimal ratio = calculaRatio(humanDna, mutantDna);

	        return Stats.builder()
	                .count_human_dna(humanDna)
	                .count_mutant_dna(mutantDna)
	                .ratio(ratio)
	                .build();
	    }
	 
	 public BigDecimal calculaRatio(BigDecimal humanDna, BigDecimal mutantDna) {
		 if(humanDna.equals(BigDecimal.ZERO)) {
			 humanDna = new BigDecimal(1);
		 }
		 
		 return mutantDna.divide(humanDna);
	 }

	
}
