package com.course.users.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column( nullable = false)
    private long id;
    private String name;
    private String lastName;
    private String documentNumber;
    private String phone;
    private Date birthdate;
    private String email;
    private String password;


    @ManyToOne
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "idRole", nullable = true)
    private RoleEntity idRole;

}
