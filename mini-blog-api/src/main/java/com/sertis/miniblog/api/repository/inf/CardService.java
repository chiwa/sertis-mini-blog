package com.sertis.miniblog.api.repository.inf;


import com.sertis.miniblog.api.model.card.Card;

import java.util.List;

public interface CardService {

    List<Card> getAllCards();

    Card findById(Integer id);

    Card save(Card card);

    void deleteById(Integer id);

}
