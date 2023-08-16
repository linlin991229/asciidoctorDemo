package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entity.Cat;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

@ApplicationScoped
public class CatRepository implements PanacheRepository<Cat> {
    @Inject
    Mutiny.SessionFactory sessionFactory;

    public Uni<List<Cat>> findByName(Long id) {
        return sessionFactory.withSession(session -> session.createNativeQuery(
                """
                        SELECT c.*
                        FROM Cat c
                        join person p
                        on c.master_id=p.id
                        WHERE c.id = :id""", Cat.class)
                .setParameter("id", id).getResultList()
        );
    }
}
