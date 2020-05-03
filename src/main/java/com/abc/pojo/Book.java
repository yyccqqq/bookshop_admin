package com.abc.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Book对象", description="")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("cid")
    private Integer cid;

    @ApiModelProperty(value = "0:未上架；1：已上架；2：已售卖")
    @TableField("book_type")
    private Integer bookType;

    @TableField("price")
    private Double price;

    @TableField("original_price")
    private Double originalPrice;

    @TableField("uid")
    private Integer uid;

    @TableField("author")
    private String author;

    @TableField("press")
    private String press;

    @TableField("version")
    private String version;

    @TableField("degree")
    private Double degree;

    @TableField("publish_date")
    private String publishDate;

    @TableField("description")
    @JsonFormat(pattern ="yyyy-MM-dd")
    private String description;

    @TableField("date")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @TableField("status")
    @TableLogic
    private Integer status;


}
