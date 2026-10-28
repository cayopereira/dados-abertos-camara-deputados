package br.com.univesp.camara.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "temas")
public class Tema {

    @Id
    @Column(name = "cod")
    private Integer cod;

    @Column(name = "nome", nullable = false)
    private String nome;
}
