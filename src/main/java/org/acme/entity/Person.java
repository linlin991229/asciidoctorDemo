package org.acme.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@NamedQueries({
        @NamedQuery(name = "Person.getByName", query = "from Person where name = ?1"),

})
public class Person extends PanacheEntity {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // 指定生成数据库类型
    @Column(columnDefinition = "timestamp(6)")
    private LocalDateTime birthday;


    public Person() {
    }

    public Person(String name, LocalDateTime birthday) {
        this.birthday = birthday;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    // 在实体本身内对实体添加自定义查询。
    public static Uni<Person> findByName(String name) {
        return find("name", name).firstResult();
    }

    public static Uni<Person> findByNameWayNameQuery(String name){
        return find("#Person.getByName",name).firstResult();
    }

    public static Uni<Long> deleteStefs() {
        return delete("name", "Stef");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", id=" + id +
                '}';
    }
}
