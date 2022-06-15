package br.com.vertzon.gconsenti.controller.api.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vertzon.gconsenti.domain.exception.BusinessRuleException;
import br.com.vertzon.gconsenti.domain.exception.EntityInUseException;
import br.com.vertzon.gconsenti.domain.exception.EntityNotFoundException;
import br.com.vertzon.gconsenti.domain.model.LegalBaseFinality;
import br.com.vertzon.gconsenti.domain.repository.LegalBaseFinalityRepository;
import br.com.vertzon.gconsenti.domain.service.LegalBaseFinalityService;

@RestController
@RequestMapping("/api/v1/legalbasefinalities")
public class LegalBaseFinalityController {

	@Autowired
	private LegalBaseFinalityRepository legalBaseFinalityRepository;
	@Autowired
	private LegalBaseFinalityService legalBaseFinalityService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<LegalBaseFinality> legalBaseFinalitys = legalBaseFinalityRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(legalBaseFinalitys);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable(value = "id", required = true) Long id) {
		Optional<LegalBaseFinality> legalBaseFinality = legalBaseFinalityRepository.findById(id);
		if (legalBaseFinality.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(legalBaseFinality);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<?> register(@RequestBody LegalBaseFinality legalBaseFinality) {
		try {
			legalBaseFinalityService.register(legalBaseFinality);
			return ResponseEntity.status(HttpStatus.CREATED).body(legalBaseFinality);
		} catch (BusinessRuleException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		try {
			legalBaseFinalityService.delete(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntityInUseException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
