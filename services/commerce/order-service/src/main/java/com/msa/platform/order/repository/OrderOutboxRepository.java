package com.msa.platform.order.repository;

import com.msa.platform.order.domain.OrderOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderOutboxRepository extends JpaRepository<OrderOutbox, Long> {
    List<OrderOutbox> findByProcessedFalse();
}
