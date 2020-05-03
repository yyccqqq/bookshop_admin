package com.abc.pojo;

import java.math.BigDecimal;
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
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("order_id")
    private String orderId;

    @TableField("total_price")
    private BigDecimal totalPrice;

    @TableField("buyer_id")
    private Integer buyerId;

    @TableField("seller_id")
    private Integer sellerId;

    @ApiModelProperty(value = "0：未付款；1：待发货；2：已发货；3：交易成功；4：交易取消")
    @TableField("type")
    private Integer type;

    @TableField("date")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @TableField("status")
    @TableLogic
    private Integer status;


}
