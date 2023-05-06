package ru.tinkoff.edu.java.scrapper.entity.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatEntity {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubscriptionEntity> subscription;

}