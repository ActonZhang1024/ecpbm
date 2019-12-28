package pers.zhang.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import pers.zhang.pojo.ProductInfo;

import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/20 - 22:05
 */
public class ProductInfoDynaSqlProvider {
    //分页动态查询
    public String selectWithParam(Map<String, Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("product_info");
                if(params.get("productInfo") != null) {
                    ProductInfo productInfo = (ProductInfo) params.get("productInfo");
                    if(productInfo.getCode() != null && !"".equals(productInfo.getCode())){
                        WHERE(" code = #{productInfo.code} ");
                    }
                    if(productInfo.getName() != null && !"".equals(productInfo.getName())){
                        WHERE(" name LIKE CONCAT('%',#{productInfo.name},'%') ");
                    }
                    if(productInfo.getBrand() != null && !"".equals(productInfo.getBrand())){
                        WHERE(" brand LIKE CONCAT('%',#{productInfo.brand},'%') ");
                    }
                    if(productInfo.getType() != null && productInfo.getType().getId() > 0){
                        WHERE(" tid = #{productInfo.type.id} ");
                    }
                    if(productInfo.getPriceFrom() > 0){
                        WHERE(" price > #{productInfo.priceFrom} ");
                    }
                    if(productInfo.getPriceTo() > 0){
                        WHERE(" price <= #{productInfo.priceTo}");
                    }
                }
            }
        }.toString();
        if(params.get("pager") != null){
            sql += " limit #{pager.firstLimitParam}, #{pager.perPageRows} ";
        }
        return sql;
    }


    //根据条件动态查询商品总记录数
    public String count(Map<String, Object> params){
        String sql = new SQL(){
            {
                SELECT("count(*)");
                FROM("product_info");
                if(params.get("productInfo") != null) {
                    ProductInfo productInfo = (ProductInfo) params.get("productInfo");
                    if (productInfo.getCode() != null && !"".equals(productInfo.getCode())) {
                        WHERE(" code = #{productInfo.code} ");
                    }
                    if (productInfo.getName() != null && !"".equals(productInfo.getName())) {
                        WHERE(" name LIKE CONCAT('%',#{productInfo.name},'%') ");
                    }
                    if (productInfo.getBrand() != null && !"".equals(productInfo.getBrand())) {
                        WHERE(" brand LIKE CONCAT('%',#{productInfo.brand},'%') ");
                    }
                    if (productInfo.getType() != null && productInfo.getType().getId() > 0) {
                        WHERE(" tid = #{productInfo.type.id} ");
                    }
                    if (productInfo.getPriceFrom() > 0) {
                        WHERE(" price > #{productInfo.priceFrom} ");
                    }
                    if (productInfo.getPriceTo() > 0) {
                        WHERE(" price <= #{productInfo.priceTo}");
                    }
                }
            }
        }.toString();
        return sql;
    }

}
