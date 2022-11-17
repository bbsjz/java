package com.example.demo.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @ApiModelProperty("用户名")
    String name;

    @ApiModelProperty("密码")
    String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade= CascadeType.PERSIST)
    List<Role> data;

}
