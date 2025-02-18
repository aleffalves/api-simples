package com.aleffalves.api_simples.model;

import com.aleffalves.api_simples.enumeration.CargoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profissional")
@Where(clause = "excluido = false")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cargo")
    @Enumerated(EnumType.STRING)
    private CargoEnum cargo;

    @Column(name = "nascimento")
    private Date nascimento;

    @Column(name = "excluido", nullable = false)
    private Boolean excluido = false;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @OneToMany(mappedBy = "profissional", cascade = CascadeType.ALL)
    private List<Contato> contatos;

    @PrePersist
    private void saveDate(){
        createdDate = new Date();
        excluido = false;
    }
}
