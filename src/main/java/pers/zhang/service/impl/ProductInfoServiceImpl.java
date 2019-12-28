package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pers.zhang.dao.ProductInfoDao;
import pers.zhang.pojo.Pager;
import pers.zhang.pojo.ProductInfo;
import pers.zhang.service.ProductInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2019/12/21 - 16:27
 */
@Service("productInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public List<ProductInfo> findProductInfo(ProductInfo productInfo, Pager pager) {
        Map<String, Object> params = new HashMap<String, Object>();
        //讲封装有查询条件的productInfo对象放入params
        params.put("productInfo", productInfo);
        //根据条件计算商品总数
        int recordCount = productInfoDao.count(params);
        //设置分页参数
        pager.setRowCount(recordCount);
        if(recordCount > 0){
            params.put("pager", pager);
        }
        return productInfoDao.selectByPage(params);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return productInfoDao.count(params);
    }

    @Override
    public void addProductInfo(ProductInfo productInfo) {
        productInfoDao.save(productInfo);
    }

    @Override
    public void modifyProductInfo(ProductInfo productInfo) {
        productInfoDao.edit(productInfo);
    }

    @Override
    public void modifyStatus(String ids, int flag) {
        productInfoDao.updateState(ids, flag);
    }

    @Override
    public List<ProductInfo> getOnSaleProduct() {
        return productInfoDao.getOnSaleProduct();
    }

    @Override
    public ProductInfo getProductInfoById(int id) {
        return productInfoDao.getProductInfoById(id);
    }
}
