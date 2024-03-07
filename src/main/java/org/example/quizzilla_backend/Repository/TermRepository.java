package org.example.quizzilla_backend.Repository;

import org.example.quizzilla_backend.Model.Term;
import org.springframework.data.repository.CrudRepository;

// https://hyperskill.org/learn/step/18031#repositories-in-spring
public interface TermRepository extends CrudRepository<Term, Long> {
}
