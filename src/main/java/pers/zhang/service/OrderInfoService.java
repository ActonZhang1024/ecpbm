package pers.zhang.service;

import org.aspectj.weaver.ast.Or;
import pers.zhang.pojo.OrderDetail;
import pers.zhang.pojo.OrderInfo;
import pers.zhang.pojo.Pager;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 16:15
 */
public interface OrderInfoService {
    //分页显示订单
    public List<OrderInfo> findOrderInfo(OrderInfo orderInfo, Pager pager);

    //订单计数
    public Integer count(Map<String, Object> params);

    //添加订单主表
    public int addOrderInfo(OrderInfo orderInfo);

    //添加订单明细
    public int addOrderDetail(OrderDetail orderDetail);

    //根据订单编号获取订单信息
    public OrderInfo getOrderInfoById(int id);

    //根据订单编号获取订单明细信息
    public List<OrderDetail> getOrderDetailById(int oid);

    //删除订单
    public int deleteOrder(int id);
}
