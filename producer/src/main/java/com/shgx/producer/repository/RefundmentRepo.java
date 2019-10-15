package com.shgx.producer.repository;

import com.shgx.producer.model.Refundment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Repository
public interface RefundmentRepo extends JpaRepository<Refundment, Long> {
    Optional<List<Refundment>> findAllById(Long id);

    Optional<Refundment> findByRefundid(Long id);
}
