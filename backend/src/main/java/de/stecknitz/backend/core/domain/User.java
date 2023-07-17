package de.stecknitz.backend.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "public")
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
