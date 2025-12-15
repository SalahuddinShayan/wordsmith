package com.wordsmith.Repositories;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wordsmith.Entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findBySubscriptionId(String subscriptionId);
    Optional<Membership> findByUsername(String username);

    List<Membership> findByStatus(String status);
    List<Membership> findByStatusAndEndDateBefore(String status, ZonedDateTime now);
    List<Membership> findAllByUsernameOrderByStartDateDesc(String username);
    Optional<Membership> findByUsernameAndStatus(String username, String status);

    // KEEP: return a list of memberships for history / admin tasks
    List<Membership> findByUsernameOrderByStartDateDesc(String username);

    // NEW: convenience to fetch the most recent membership for a user
    Optional<Membership> findTopByUsernameOrderByCreatedAtDesc(String username);

    // NEW: count methods for admin dashboard
    long count();

    long countByStatus(String status);

    // NEW: fetch all memberships ordered by creation date descending
    List<Membership> findAllByOrderByCreatedAtDesc();

    Optional<Membership> findTopBySubscriptionIdOrderByCreatedAtDesc(String subscriptionId);

    Long findLatestBySubscriptionId(String subscriptionId);

    List<Membership> findByStatusOrderByCreatedAtDesc(String status);

    boolean existsBySubscriptionIdAndUsernameIsNotNull(String subscriptionId);

}
