package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.entity.Account;
import org.acme.entity.Cat;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class CatRepository implements PanacheRepository<Cat> {
    @Inject
    Mutiny.SessionFactory sessionFactory;

    /**
     * 编写原生SQL语句
     *
     * @param id id
     * @return Uni<List < Cat>>
     */
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

    public Multi<Boolean> update(Account source, Account target) {
        Multi<Boolean> multi = sessionFactory.withSession((session) -> session.find(Account.class, source.id)).map(Objects::nonNull).toMulti();
        Multi<Boolean> multi1 = sessionFactory.withSession((session) -> session.find(Account.class, target.id)).map(Objects::nonNull).toMulti();


        return Multi.createBy().combining().streams(multi1,multi).asTuple().map(tuple->tuple.getItem1()&&tuple.getItem2());

    }
}
