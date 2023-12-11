package com.wipro.sales.dao;
import com.wipro.sales.bean.Product;
import com.wipro.sales.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StockDao {
    public int insertStock(Product product){
        try{
            PreparedStatement pt= DBUtil.getDBConnection().prepareStatement("insert into tbl_stock values(?,?,?,?,?)");
            pt.setString(1,product.getProductID());
            pt.setString(2,product.getProductName());
            pt.setInt(3,product.getQuantityonHand());
            pt.setDouble(4,product.getProductUnitPrice());
            pt.setInt(5,product.getReorderLevel());
            if(pt.executeUpdate()==1)
                return 1;
            else
                return 0;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
    public String generateProductId(String productName){
        try{
            Statement stmt=DBUtil.getDBConnection().createStatement();
             ResultSet rs=stmt.executeQuery("select SEQ_PRODUCT_Id.NEXTVAL from dual");
             rs.next();
             int SEQ_PRODUCT_ID=rs.getInt(1);
             String str=productName.substring(0,2);
             str=str+SEQ_PRODUCT_ID;
             return str;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public int updateStock(String productID,int soldQuantity){
        try{
            PreparedStatement ps=DBUtil.getDBConnection().prepareStatement("update tbl_stock set quantity_on_hand=quantity_on_hand - ? where Product_ID=?");
            ps.setInt(1,soldQuantity);
            ps.setString(2,productID);
            if(ps.executeUpdate()==1)
                return 1;
            else
                return 0;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public Product getStock(String productID){
        try{
            PreparedStatement ps=DBUtil.getDBConnection().prepareStatement("select * from tbl_stock where Product_ID=?");
            ps.setString(1,productID);
            ResultSet rs=ps.executeQuery();
            rs.next();
            Product pro=new Product();
            pro.setProductID(rs.getString(1));
            pro.setProductName(rs.getString(2));
            pro.setQuantityonHand(rs.getInt(3));
            pro.setProductUnitPrice(rs.getDouble(4));
            pro.setReorderLevel(rs.getInt(5));
            return pro;
            }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public int deleteStock(String productID){
        try{
            PreparedStatement ps=DBUtil.getDBConnection().prepareStatement("delete from tbl_stock where Product_ID = ?");
            PreparedStatement ps1=DBUtil.getDBConnection().prepareStatement("delete from tbl_sales where Product_ID = ?");
            ps.setString(1,productID);
            ps1.setString(1,productID);
            ps1.executeUpdate();
            if( ps.executeUpdate()==1)
                return 1;
            else
                return 0;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
