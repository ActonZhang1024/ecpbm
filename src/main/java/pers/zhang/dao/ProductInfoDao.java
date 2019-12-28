package pers.zhang.dao;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import pers.zhang.dao.provider.ProductInfoDynaSqlProvider;
import pers.zhang.pojo.ProductInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/20 - 21:37
 */
public interface ProductInfoDao {

    //分页获取商品
    @SelectProvider(type = ProductInfoDynaSqlProvider.class, method = "selectWithParam")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "code", property = "code"),
            @Result(column = "name", property = "name"),
            @Result(column = "brand", property = "brand"),
            @Result(column = "pic", property = "pic"),
            @Result(column = "num", property = "num"),
            @Result(column = "price", property = "price"),
            @Result(column = "intro", property = "intro"),
            @Result(column = "status", property = "status"),
            @Result(column = "tid", property = "type", one = @One(select = "pers.zhang.dao.TypeDao.selectById", fetchType = FetchType.EAGER))
    })
    public List<ProductInfo> selectByPage(Map<String, Object> params);

    //根据条件查询商品总数
    @SelectProvider(type = ProductInfoDynaSqlProvider.class, method = "count")
    public Integer count(Map<String, Object> params);

    //添加商品
    @Insert("insert into product_info(code, name, tid, brand, pic, num, price, intro, status) " +
            "values(#{code},#{name},#{type.id},#{brand},#{pic},#{num},#{price},#{intro},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void save(ProductInfo productInfo);

    //修改商品
    @Update("update product_info set code=#{code}, name=#{name}, tid=#{type.id}, brand=#{brand}, pic=#{pic}," +
            "num=#{num}, price=#{price}, intro=#{intro}, status=#{status} where id=#{id}")
    public void edit(ProductInfo productInfo);

    //更新商品状态
    @Update("update product_info set status=#{flag} where id in (${ids})")
    public void updateState(@Param("ids") String ids, @Param("flag") int flag);

    //获取在售商品列表
    @Select("select * from product_info where status = 1")
    public List<ProductInfo> getOnSaleProduct();

    //根据商品id获取商品对象
    @Select("select * from product_info where id = #{id}")
    public ProductInfo getProductInfoById(int id);
}
