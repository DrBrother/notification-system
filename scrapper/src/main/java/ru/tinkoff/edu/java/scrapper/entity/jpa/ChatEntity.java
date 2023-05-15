package ru.tinkoff.edu.java.scrapper.entity.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
