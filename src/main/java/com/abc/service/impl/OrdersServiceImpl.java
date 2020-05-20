package com.abc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abc.mapper.OrdersMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Orders;
import com.abc.pojo.User;
import com.abc.pojo.vo.OrderVo;
import com.abc.service.IOrdersService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-19
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, List<String>> getData() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> dateList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        Date date = DateUtil.date();
        for (int i = 6; i >= 1; i--) {
            Date date2 = DateUtil.offsetDay(date, -i);
            Double totalPrice = getTotalPrice(date2);
            dataList.add(Convert.toStr(totalPrice));

            String date2Str = DateUtil.format(date2, "MM-dd");
            dateList.add(date2Str);
        }
        Double totalPrice = getTotalPrice(date);
        dataList.add(Convert.toStr(totalPrice));

        String dateStr = DateUtil.format(date, "MM-dd");
        dateList.add(dateStr);
        map.put("dateList", dateList);
        map.put("dataList", dataList);
        return map;
    }

    private Double getTotalPrice(Date date) {
        Date beginOfDay = DateUtil.beginOfDay(date);
        Date endOfDay = DateUtil.endOfDay(date);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Orders::getDate, beginOfDay, endOfDay);
        /* wrapper.between(Orders::getDate, beginOfDay, endOfDay).ne(Orders::getType, 4); */
        List<Orders> ordersList = ordersMapper.selectList(wrapper);
        Double totalPrice = 0.0;
        for (Orders orders : ordersList) {
            totalPrice += Convert.toDouble(orders.getTotalPrice());
        }
        return totalPrice;
    }

    @Override
    public PageInfo<Orders> search(Integer pageNum, Integer rows) {
        PageHelper.startPage(pageNum, rows);
        List<Orders> ordersList = ordersMapper.selectList(null);
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        return pageInfo;
    }

    @Override
    public OrderVo findOne(String id) {
        Orders order = ordersMapper.selectById(id);
        String orderStr = JSONUtil.toJsonStr(order);
        OrderVo orderVo = JSONUtil.toBean(orderStr, OrderVo.class);
        User buyer = userMapper.selectById(order.getBuyerId());
        orderVo.setBuyerName(buyer.getName());
        User seller = userMapper.selectById(order.getSellerId());
        orderVo.setSellerName(seller.getName());
        return orderVo;
    }

    @Override
    public void delete(String[] ids) {
        ordersMapper.deleteBatchIds(CollUtil.newArrayList(ids));
    }

}
