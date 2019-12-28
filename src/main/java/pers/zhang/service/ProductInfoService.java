package pers.zhang.service;

import pers.zhang.pojo.Pager;
import pers.zhang.pojo.ProductInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 16:06
 */
public interface ProductInfoService {
    //分页显示商品
    public List<ProductInfo> findProductInfo(ProductInfo productInfo, Pager pager);

    //商品计数
    public Integer count(Map<String, Object> params);

    //添加商品
    public void addProductInfo(ProductInfo productInfo);

    //修改商品
    public void modifyProductInfo(ProductInfo productInfo);

    //更新商品状态
    public void modifyStatus(String ids, int flag);

    //获取在售商品列表
    public List<ProductInfo> getOnSaleProduct();

    //根据商品id获取商品对象
    public ProductInfo getProductInfoById(int id);
}
