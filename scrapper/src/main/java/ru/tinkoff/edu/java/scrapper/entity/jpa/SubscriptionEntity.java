package ru.tinkoff.edu.java.scrapper.entity.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription")
@Data
@IdClass(SubscriptionId.class)
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionEntity {

    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Id
    @Column(name = "link_id")
    private Long linkId;

}

class SubscriptionId implements Serializable {

    private Long chatId;
    private Long linkId;

}
