package com.sertis.miniblog.api.repository.impl;

import com.sertis.miniblog.api.model.card.Card;
import com.sertis.miniblog.api.repository.inf.CardRepository;
import com.sertis.miniblog.api.repository.inf.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(value = "CardService")
@Transactional
public class CardServiceImpl implements CardService {

    @Autowired
	private CardRepository cardRepository;

	@Override
	public List<Card> getAllCards()  {
		return (List<Card>) cardRepository.findAll();
	}

	@Override
	public Card findById(Integer id) {
		return cardRepository.findById(id).orElse(null);
	}

	@Override
	public Card save(Card card) {
		card.setLastModified(new Date());
		return cardRepository.save(card);
	}

	@Override
	public void deleteById(Integer id) {
		cardRepository.deleteById(id);
	}
}
