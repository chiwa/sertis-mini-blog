package com.sertis.miniblog.api.repository.inf;

import com.sertis.miniblog.api.model.card.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer> {

}
