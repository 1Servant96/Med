package com.medcheck.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @SequenceGenerator(name = "chat_gen", sequenceName = "chat_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_gen")
    Long id;


}
