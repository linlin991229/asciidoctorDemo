package org.acme.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Account extends PanacheEntity {

    public Account() {
    }

    public Account(String name, BigDecimal money) {
        this.name = name;
        this.money = money;
    }

    private String name;
    private BigDecimal money;


    public static Uni<Boolean> transactionMoney(Account source, Account target) {

        Uni<Boolean> sourceSelect = findById(source.id)
                .map(Objects::nonNull);
        Uni<Boolean> targetSelect = findById(target.id)
                .map(Objects::nonNull);
        return sourceSelect.onItem().transformToUni(x ->
                x ? targetSelect : Uni.createFrom().item(false)
        ).onItem().transformToUni(flag -> {
            if (flag) {
                return update("set money=money-50 where id=?1", source.id).onItem().transformToUni(n -> {
                    if (n > 0) {
                        return update("set money=money+50 where id=?1", target.id).map(result -> result > 0);
                    }
                    return Uni.createFrom().item(false);
                });
            }
            return Uni.createFrom().item(() -> false);
        });

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
               "name='" + name + '\'' +
               ", money=" + money +
               ", id=" + id +
               '}';
    }
}
