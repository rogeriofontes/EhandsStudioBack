package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.budget.QuoteMessage;

import java.util.List;

public interface QuoteMessageService {

    List<QuoteMessage> findAll();

    QuoteMessage findById(Long id);

    QuoteMessage save(QuoteMessage quoteMessage);

    QuoteMessage update(Long id, QuoteMessage quoteMessage);

    void delete(Long id);

    boolean existsById(Long id);
}
