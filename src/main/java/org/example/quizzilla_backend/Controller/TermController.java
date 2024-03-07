package org.example.quizzilla_backend.Controller;

import org.example.quizzilla_backend.DTO.TermDTO;
import org.example.quizzilla_backend.Model.Term;
import org.example.quizzilla_backend.Service.TermService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST Controller: https://hyperskill.org/learn/step/13287
// Handling RequestBody: https://hyperskill.org/learn/step/13334
// Post / Delete Requests: https://hyperskill.org/learn/step/13292
@RestController
@RequestMapping
public class TermController {
    private final TermService termService;

    TermController(TermService termService) {
        this.termService = termService;
    }

    @PostMapping("/term")
    public ResponseEntity<Term> postTerm(@RequestBody TermDTO request) {
        Term term = termService.createTerm(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(term);
    }

    @GetMapping("/terms")
    public ResponseEntity<List<Term>> getTerms() {
        List<Term> terms = termService.getAllTerms();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(terms);
    }

    @DeleteMapping("/term/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        termService.deleteTerm(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/term/{id}")
    public ResponseEntity<Term> updateTerm(@PathVariable Long id, @RequestBody TermDTO request) {
        Term term = termService.updateTerm(id, request.name(), request.description());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(term);
    }
}
