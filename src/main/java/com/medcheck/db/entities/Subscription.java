package com.medcheck.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_gen")
    @SequenceGenerator(name = "subscription_gen", sequenceName = "subscription_seq", allocationSize = 1)
    private Long id;
    private String email;
}
