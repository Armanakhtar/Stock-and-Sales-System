package com.wipro.sales.service;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;

import java.util.ArrayList;
import java.util.Date;

public class Administrator {
    private StockDao stockDao=new StockDao();
    private SalesDao salesDao=new SalesDao();
    public String insertStock(Product productobj){
        if((productobj!=null) && (productobj.getProductName().length()>=2)){
            String productID=stockDao.generateProductId(productobj.getProductName());
            productobj.setProductID(productID);
            if(stockDao.insertStock(productobj)==1)
                return productID;
            else
                return "data not valid for insertion";
        }
        else
            return "Data not valid for insertion";

    }
    public String deleteStock(String ProductID){
        if(stockDao.deleteStock(ProductID)==1)
            return "Deleted";
        else
            return "Record can not be deleted";
    }
    public String insertSales(Sales salesObj){
        if(salesObj==null)
            return "Object not valid for insertion";
        if(stockDao.getStock(salesObj.getProductId())==null)
            return "Unknown product for sales";
        if(stockDao.getStock(salesObj.getProductId()).getQuantityonHand() < salesObj.getQuantitySold())
            return "Not Enough Stock For Sales";

        if(salesObj.getSalesDate().after(new Date()))
            return "Invalid Date";
        String SalesId=salesDao.generateSalesID(salesObj.getSalesDate());
        salesObj.setSalesID(SalesId);

        if(salesDao.insertSales(salesObj)==1){
            if(stockDao.updateStock(salesObj.getProductId(),salesObj.getQuantitySold())==1)
                return "sales record inserted";
            else{
                return "Error";
            }
        }
        else
            return "Error";

    }
    public ArrayList<SalesReport> getSalesReport(){
        return salesDao.getSalesReport();

    }
}
