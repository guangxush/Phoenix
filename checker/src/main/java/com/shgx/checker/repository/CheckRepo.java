package com.shgx.checker.repository;

import com.shgx.checker.model.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Repository
public interface CheckRepo extends JpaRepository<Check, Long> {
    Optional<List<Check>> findAllById(Long id);

    Optional<Check> findByCheckid(Long id);
}
