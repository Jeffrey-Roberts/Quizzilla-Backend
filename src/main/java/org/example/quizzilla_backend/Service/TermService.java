package org.example.quizzilla_backend.Service;

import org.example.quizzilla_backend.Model.Term;
import org.example.quizzilla_backend.Repository.TermRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TermService {
    private final TermRepository termRepository;

    TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    public Term createTerm(String name, String description) {
        validateTerm(name, description);

        var term = new Term();
        term.setName(name);
        term.setDescription(description);

        return termRepository.save(term);
    }

    public List<Term> getAllTerms() {
        Iterable<Term> termsIterable = termRepository.findAll();
        List<Term> terms = new ArrayList<>();
        termsIterable.forEach(terms::add);
        return terms;
    }

    public void deleteTerm(Long id) {
        Term term = getTerm(id);
        termRepository.delete(term);
    }

    public Term updateTerm(Long id, String name, String description) {
        validateTerm(name, description);
        Term term = getTerm(id);

        term.setName(name);
        term.setDescription(description);

        return termRepository.save(term);
    }


    private void validateTerm(String name, String description) {
        if (name.isEmpty() || description.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Term or description is empty");
    }

    private Term getTerm(Long id) {
        Optional<Term> optionalTerm = termRepository.findById(id);
        if (optionalTerm.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return optionalTerm.get();
    }
}
