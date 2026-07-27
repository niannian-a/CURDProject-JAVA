package com.example.curd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    @TableId(value = "userid", type = IdType.AUTO)
    private int userid;
    @TableField("username")
    private String username;
    @TableField("name")
    private String name;
    @TableField("userPassword")
    private String userPassword;

}
