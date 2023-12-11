package com.wipro.sales.main;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.service.Administrator;
import com.wipro.sales.bean.Product;

public class SalesApplication {
    public static void main(String args[]){
        Scanner scan=new Scanner(System.in);
        Administrator administrator=new Administrator();
        int a=0;
        do{
            System.out.println("1. Insert Stock");
        System.out.println("2. Delete Stock");
        System.out.println("3. Insert Sales");
        System.out.println("4. View Sales Report");
        System.out.println("Enter Your Choice:");
        a = scan.nextInt();
        switch (a) {
            case 1:
                Product stock = new Product();
                System.out.println("Enter the product name:");
                stock.setProductName(scan.next());
                scan.nextLine();
                System.out.println("Enter the Quantity On Hamd:");
                stock.setQuantityonHand(scan.nextInt());
                scan.nextLine();
                System.out.println("Enter Product Unit Price:");
                stock.setProductUnitPrice(scan.nextDouble());
                System.out.println("Enter Reorder Level:");
                stock.setReorderLevel(scan.nextInt());
                scan.nextLine();
                System.out.println("Product_ID: " + administrator.insertStock(stock) + " Inserted");
                break;
            case 2:
                System.out.println("Enter ProductId To Be Deleted:");
                String str = scan.next();
                String str1 = administrator.deleteStock(str);
                if (str1 != null)
                    System.out.println(str1);
                break;
            case 3:
                try {
                    Sales sales = new Sales();
                    System.out.println("Enter date (DD-MM-YYYY):");
                    String date1 = scan.next();
                    scan.nextLine();
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                    sales.setSalesDate(date);
                    System.out.println("Enter Product Id:");
                    sales.setProductId(scan.nextLine());
                    System.out.println("Enter Quantity sold:");
                    sales.setQuantitySold(scan.nextInt());
                    scan.nextLine();
                    System.out.println("Enter Sales price per unit:");
                    sales.setSalesPriceperUnit(scan.nextDouble());
                    System.out.println(administrator.insertSales(sales));
                    break;
                } catch (Exception E) {
                    E.printStackTrace();

                }
            case 4:
                System.out.println("|SalesId|SalesDate|ProductID|ProductName|QuantitySold|ProductUnitPrice|SalesPricePerUnit|ProfitAmount|");
                for (int i = 0; i < administrator.getSalesReport().size(); i++) {
                    System.out.println("| " + administrator.getSalesReport().get(i).getSalesID() + " | " + administrator.getSalesReport().get(i).getSalesDate() + " | " + administrator.getSalesReport().get(i).getProductID() + " | " + administrator.getSalesReport().get(i).getProductName() + " | " + administrator.getSalesReport().get(i).getQuantitySold() + " | " + administrator.getSalesReport().get(i).getProductUnitPrice() + " | " + administrator.getSalesReport().get(i).getSalesPricePerUnit() + " | " + administrator.getSalesReport().get(i).getProfitAmount() + " |");

                }

                break;
            default:
                System.out.println("Enter valid Input");
                a = 0;
                break;
        }
    }while ((a>=0) && (a<=4));
        scan.close();
    }
}
