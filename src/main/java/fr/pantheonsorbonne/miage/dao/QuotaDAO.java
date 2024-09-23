package fr.pantheonsorbonne.miage.dao;

import fr.pantheonsorbonne.miage.dto.Quota;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class QuotaDAO {

    public void onApplicationStarted(@Observes StartupEvent event) {
        this.getQuota().add(new Quota(1, 20, 20, 1));
    }

    public Set<Quota> getQuota() {
        return quota;
    }

    public void setQuota(Set<Quota> quota) {
        this.quota = quota;
    }

    Set<Quota> quota = new HashSet<>();


}
