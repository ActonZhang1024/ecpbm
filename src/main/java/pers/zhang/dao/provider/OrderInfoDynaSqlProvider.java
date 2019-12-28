package pers.zhang.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import pers.zhang.pojo.OrderInfo;

import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 12:13
 */
public class OrderInfoDynaSqlProvider {
    //分页动态查询
    public String selectWithParam(Map<String, Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("order_info");
                if(params.get("orderInfo") != null){
                    OrderInfo orderInfo = (OrderInfo) params.get("orderInfo");
                    if(orderInfo.getId() != null && orderInfo.getId() > 0){
                        WHERE(" id = #{orderInfo.id} ");
                    }else{
                        if(orderInfo.getStatus() != null && !"请选择".equals(orderInfo.getStatus())){
                            WHERE(" status = #{orderInfo.status} ");
                        }
                        if(orderInfo.getOrderTimeFrom() != null && !"".equals(orderInfo.getOrderTimeFrom())){
                            WHERE(" ordertime >= #{orderInfo.orderTimeFrom} ");
                        }
                        if(orderInfo.getOrderTimeTo() != null && !"".equals(orderInfo.getOrderTimeTo())){
                            WHERE(" ordertime < #{orderInfo.orderTimeTo} ");
                        }
                        if(orderInfo.getUid() > 0){
                            WHERE(" uid = #{orderInfo.uid}");
                        }
                    }
                }
            }
        }.toString();
        if(params.get("pager") != null){
            sql += " limit #{pager.firstLimitParam}, #{pager.perPageRows} ";
        }
        return sql;
    }

    //根据条件动态查询订单总记录数
    public String count(Map<String, Object> params){
        String sql = new SQL(){
            {
                SELECT("count(*)");
                FROM("order_info");
                if(params.get("orderInfo") != null){
                    OrderInfo orderInfo = (OrderInfo) params.get("orderInfo");
                    if(orderInfo.getId() != null && orderInfo.getId() > 0){
                        WHERE(" id = #{orderInfo.id} ");
                    }else{
                        if(orderInfo.getStatus() != null && !"请选择".equals(orderInfo.getStatus())){
                            WHERE(" status = #{orderInfo.status} ");
                        }
                        if(orderInfo.getOrderTimeFrom() != null && !"".equals(orderInfo.getOrderTimeFrom())){
                            WHERE(" ordertime >= #{orderInfo.orderTimeFrom} ");
                        }
                        if(orderInfo.getOrderTimeTo() != null && !"".equals(orderInfo.getOrderTimeTo())){
                            WHERE(" ordertime < #{orderInfo.orderTimeTo} ");
                        }
                        if(orderInfo.getUid() > 0){
                            WHERE(" uid = #{orderInfo.uid}");
                        }
                    }
                }
            }
        }.toString();
        return sql;
    }
}
