package fr.pantheonsorbonne.miage.service;

import fr.pantheonsorbonne.miage.dao.QuotaDAO;
import fr.pantheonsorbonne.miage.dto.Quota;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class QuotaService {


    @Inject
    QuotaDAO quotaDAO;

    public void consumeQuota(int vendorId, int concerId, int seating, int standing) throws NoQuotaForVendorAndConcertException, InsufficientQuotaException {

        Optional<Quota> q = quotaDAO.getQuota().stream()
                .filter(quota -> quota.vendorId() == vendorId)
                .filter(quota -> quota.concertId() == concerId).findAny();

        Quota realQuota = q.orElseThrow(() -> new NoQuotaForVendorAndConcertException());
        int newSeated = realQuota.seated() - seating;
        int newStanding = realQuota.standing() - standing;
        if (newSeated >= 0 && newStanding >= 0) {
            quotaDAO.getQuota().remove(realQuota);
            quotaDAO.getQuota().add(new Quota(vendorId,  newSeated, newStanding,concerId));
            return;
        }

        throw new InsufficientQuotaException();


    }


    public Quota getQuota(int vendorId, int concertId) {
        return quotaDAO.getQuota().stream()
                .filter(quota -> quota.vendorId() == vendorId)
                .filter(quota -> quota.concertId() == concertId).findAny().orElseThrow();

    }

    public List<Quota> getQuotaForVendor(int vendorId) {
        return quotaDAO.getQuota().stream()
                .filter(quota -> quota.vendorId() == vendorId)
                .toList();
    }
}
