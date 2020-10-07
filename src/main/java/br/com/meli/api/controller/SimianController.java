package br.com.meli.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.domain.dto.SimianDto;
import br.com.meli.domain.exception.DnaNaoPermitidoException;
import br.com.meli.domain.exception.DnaUtilizadoException;
import br.com.meli.domain.model.Stats;
import br.com.meli.domain.service.SimianService;

@RestController
@RequestMapping("/simian-api")
public class SimianController {

	@Autowired
	private SimianService simianService;

	@PostMapping("/simian")
	public ResponseEntity<?> simian(@RequestBody @Valid SimianDto dna) {
		try {
			return simianService.isSimian(dna.getDna()) ? new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK)
					: new ResponseEntity<>(Boolean.FALSE, HttpStatus.FORBIDDEN);
		} catch (DnaUtilizadoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (DnaNaoPermitidoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("/stats")
    public ResponseEntity<Stats> statsReport() {
		return ResponseEntity.ok(simianService.getStatsReport());
    }

}
