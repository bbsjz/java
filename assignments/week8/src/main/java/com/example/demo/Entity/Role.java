package com.example.demo.Entity;

import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    String name;

    @Convert(converter = RoleConverter.class)
    List<String> authorities;
}
