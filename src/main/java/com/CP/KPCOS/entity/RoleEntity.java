package com.CP.KPCOS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "role")
public class RoleEntity extends BaseEntity {
    String name;
    String description;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List <UserEntity> users;
}
