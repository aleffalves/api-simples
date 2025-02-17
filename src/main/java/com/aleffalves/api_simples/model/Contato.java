package com.aleffalves.api_simples.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "contato")
    private String contato;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @PrePersist
    private void saveDate(){
        createdDate = new Date();
    }
}
