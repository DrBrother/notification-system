package ru.tinkoff.edu.java.scrapper.entity.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "link")
@Data
@NoArgsConstructor
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_id_seq")
    @SequenceGenerator(name = "link_id_seq", sequenceName = "link_id_seq", allocationSize = 1)
    private Long id;
    private String url;
    @Column(name = "checktime")
    private OffsetDateTime checkTime;
    @Column(name = "updatetime")
    private OffsetDateTime updateTime;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "linkId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubscriptionEntity> subscription;

    public LinkEntity(String url, OffsetDateTime checkTime, OffsetDateTime updateTime) {
        this.url = url;
        this.checkTime = checkTime;
        this.updateTime = updateTime;
    }

}