package com.wordsmith.Services;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wordsmith.Entity.Membership;
import com.wordsmith.Repositories.MembershipRepository;
import com.wordsmith.Util.EmailMasker;

@Service
public class MembershipExpiryScheduler {

    private static final Logger log = LoggerFactory.getLogger(MembershipExpiryScheduler.class);

    private final MembershipRepository membershipRepo;

    public MembershipExpiryScheduler(MembershipRepository membershipRepo) {
        this.membershipRepo = membershipRepo;
    }

    /** 
     * Runs every hour to expire memberships that passed end_date 
     */
    @Scheduled(cron = "0 0 * * * *") // every hour
    public void expireOldMemberships() {

        ZonedDateTime now = ZonedDateTime.now();

        try {
            List<Membership> toExpire =
                    membershipRepo.findByStatusAndEndDateBefore("ACTIVE", now);

            if (toExpire.isEmpty()) {
                log.debug("SCHEDULER_NO_EXPIRY_REQUIRED at={}", now);
                return;
            }

            log.info("SCHEDULER_EXPIRY_FOUND count={} at={}", toExpire.size(), now);

            for (Membership m : toExpire) {
                String maskedUser = EmailMasker.mask(m.getUsername());

                log.info("SCHEDULER_EXPIRING membershipId={} user={} oldStatus=ACTIVE newStatus=EXPIRED endDate={}",
                        m.getId(),
                        maskedUser,
                        m.getEndDate()
                );

                m.setStatus("EXPIRED");
                membershipRepo.save(m);
            }

            log.info("SCHEDULER_EXPIRY_COMPLETE expiredCount={} at={}", toExpire.size(), now);

        } catch (Exception ex) {
            log.error("SCHEDULER_EXPIRY_FAILURE at={} error={}", now, ex.getMessage(), ex);
        }
    }
}
