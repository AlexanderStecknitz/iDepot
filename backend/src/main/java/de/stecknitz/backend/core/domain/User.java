package de.stecknitz.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user", schema = "public")
@EqualsAndHashCode
public class User {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @Column(name = "salt")
    private String salt;

    @OneToMany(mappedBy = "user")
    private List<Depot> depots;

}
