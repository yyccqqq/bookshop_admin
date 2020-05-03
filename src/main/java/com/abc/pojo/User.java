package com.abc.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("student_id")
    private String studentId;

    @TableField(value = "name",whereStrategy = FieldStrategy.NOT_EMPTY)
    private String name;

    @TableField("password")
    private String password;

    @TableField("sex")
    private String sex;

    @TableField("tel")
    private String tel;

    @TableField("email")
    private String email;

    @TableField("address")
    private String address;

    @TableField("major")
    private String major;

    @TableField("status")
    @TableLogic
    private Integer status;


}
