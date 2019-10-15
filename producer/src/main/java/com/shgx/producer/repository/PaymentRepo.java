package com.shgx.producer.repository;

import com.shgx.producer.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Optional<List<Payment>> findAllById(Long id);

    Optional<Payment> findByPayid(Long id);
}
