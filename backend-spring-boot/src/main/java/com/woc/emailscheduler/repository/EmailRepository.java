package com.woc.emailscheduler.repository;

import com.woc.emailscheduler.model.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Scheduler, Long> {

    @Query("SELECT e FROM Scheduler e WHERE e.status = 'PENDING' AND e.scheduledTime <= :cutoffTime ORDER BY e.scheduledTime ASC")
    List<Scheduler> findPendingEmails(@Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT e FROM Scheduler e WHERE e.status = 'PENDING' AND e.scheduledTime BETWEEN :currentTime AND :cutoffTime ORDER BY e.scheduledTime ASC")
    List<Scheduler> findFutureEmails(@Param("currentTime") LocalDateTime currentTime, @Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE e.status = 'SENT'")
    long countSentEmails();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE e.status = 'FAILED'")
    long countFailedEmails();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE e.status = 'PENDING'")
    long countPendingEmails();


}
