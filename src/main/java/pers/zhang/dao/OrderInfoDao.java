package pers.zhang.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import pers.zhang.dao.provider.OrderInfoDynaSqlProvider;
import pers.zhang.pojo.OrderDetail;
import pers.zhang.pojo.OrderInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 11:15
 */
public interface OrderInfoDao {
    //分页获取订单信息
    @SelectProvider(type = OrderInfoDynaSqlProvider.class, method = "selectWithParam")
    @Results({
            @Result(column = "uid", property = "ui", one = @One(select = "pers.zhang.dao.UserInfoDao.getUserInfoById", fetchType = FetchType.EAGER))
    })
    public List<OrderInfo> selectByPage(Map<String, Object> params);

    //根据条件查询订总数
    @SelectProvider(type = OrderInfoDynaSqlProvider.class, method = "count")
    public Integer count(Map<String, Object> params);

    //保存订单主表信息
    @Insert("insert into order_info(uid, status, ordertime, orderprice) " +
            "values(#{uid},#{status},#{ordertime},#{orderprice})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int saveOrderInfo(OrderInfo orderInfo);

    //保存订单明细
    @Insert("insert into order_detail(oid, pid, num) values(#{oid},#{pid},#{num})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int saveOrderDetail(OrderDetail orderDetail);

    //根据订单编号获取订单对象
    @Select("select * from order_info where id = #{id}")
    @Results({
            @Result(column = "uid", property = "ui", one = @One(select = "pers.zhang.dao.UserInfoDao.getUserInfoById", fetchType = FetchType.EAGER))
    })
    public OrderInfo getOrderInfoById(int id);

    //根据订单编号获取订单明细
    @Select("select * from order_detail where oid = #{oid}")
    @Results({
            @Result(column = "pid", property = "pi", one = @One(select = "pers.zhang.dao.ProductInfoDao.getProductInfoById", fetchType = FetchType.EAGER))
    })
    public List<OrderDetail> getOrderDetailByOid(int oid);

    //根据订单编号删除订单主表记录
    @Delete("delete from order_info where id = #{id}")
    public int deleteOrderInfo(int id);

    //根据订单编号删除订单明细记录
    @Delete("delete from order_detail where oid = #{id}")
    public int deleteOrderDetail(int id);

}
