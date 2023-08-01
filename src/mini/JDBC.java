package mini;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer {
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobile;
    
    

    public Customer(String username, String password, String name, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

}

class Seller {
    private String username;
    private String password;
    private String name;
    private String email;
    private String mobile;

    public Seller(String username, String password, String name, String email, String mobile) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    // Getters and other methods as before
}

class Product {
    private int productId;
    private String productName;
    private double productPrice;
    private int productAvailability;

    public Product(String productName, double productPrice, int productAvailability) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAvailability = productAvailability;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductAvailability() {
        return productAvailability;
    }
}



class CartItem {
    private int cartId;
    private String productName;
    private int quantity;
    private double price;

    public CartItem(String productName, int quantity, double price) {
        
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Order{
   
	private int orderId;
    private String productName;
    private int quantity;
    private double price;

    public Order(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
   
    
    public Order(String productName, int quantity, double price, String productName2, int quantity2, double price2) {
		
		productName = productName2;
		quantity = quantity2;
		price = price2;
	}

///encapsulation
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}



public class JDBC {
	
//	GetAllOrders ord = new GetAllOrders();
	
    private static final String DB_URL = "jdbc:mysql://localhost:3306/miniconsole";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "subash@123";
    static String displayname;
    static String username;

    public JDBC() {
        // Constructor
    }

    private void insertOrderIntoDatabase(List<CartItem> cartItems, double totalPrice) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      
            String insertItemSql = "INSERT INTO orders (product_name, quantity, price) VALUES (?, ?, ?)";
            PreparedStatement itemPstmt = conn.prepareStatement(insertItemSql);
            for (CartItem cartItem : cartItems) {
               
                itemPstmt.setString(1, cartItem.getProductName());
                itemPstmt.setInt(2, cartItem.getQuantity());
                itemPstmt.setDouble(3, cartItem.getPrice());
                itemPstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> GetCartFromDatabase() {
        List<CartItem> cartItems = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectSql = "SELECT * FROM cart_items";
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
     
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                CartItem cartItem = new CartItem(productName, quantity, price);
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
 
    public void updateproducts() {
    	Scanner scanner=new Scanner(System.in);
    	JDBC shoppingSystem = new JDBC();
    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
 	        String updatevalue = "Update products \n set product_price=?,product_availbility=? \n where product_name=?";
 	        PreparedStatement pstmt = conn.prepareStatement(updatevalue);
 	       System.out.println("");
 	        System.out.print("Enter product name: ");
 	       String product_name = scanner.nextLine();
 	        pstmt.setString(3, product_name);
 	        System.out.print("Enter product price: ");
 	        double productPrice = scanner.nextDouble();
 	         pstmt.setDouble(1, productPrice);
 	        System.out.print("Enter product availability: ");
 	        int productAvailability = scanner.nextInt();
 	       pstmt.setDouble(2, productAvailability);
 	        
 	        pstmt.executeUpdate(); 
 	        System.out.println("product update sucessfully");
 	       System.out.println("");
 	        System.out.println("What you like to do?");
 	        System.out.println("1. Add Product");
 	        System.out.println("2. Update Product");
 	        System.out.println("3. Exit....");
 	        System.out.print("Enter your choice: ");
 	        int choice = scanner.nextInt();
 	        System.out.println("-------------------------------------------------");
 	        scanner.nextLine();

 	         switch (choice) {
 	             case 1:
 	                 shoppingSystem.products();
 	                 break;
 	             case 2:

 	                 shoppingSystem.updateproducts();
 	                 break;
 	             case 3:
 	            	 System.exit(0);
 	            	 scanner.close();
 	            	 break;
 	             default:
 	                 System.out.println("Invalid choice. Exiting...");
 	         }
 	        
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
    }
   
    public void order() {
        List<CartItem> cartItems = GetCartFromDatabase(); // Assuming this method retrieves cart items for the given customer
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome,"+username+"! Your order:");

        System.out.println("-------------------------------------------------");
        System.out.println("Cart Items:");
        for (CartItem cartItem : cartItems) {
            System.out.println(cartItem.getProductName() + " - Quantity: " + cartItem.getQuantity() + " - Price: $" + cartItem.getPrice());
        }
        System.out.println("-------------------------------------------------");

        // Calculate total price
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        System.out.println("Total Price: $" + totalPrice);

        System.out.print("Confirm your order (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            // Insert the cart items into the database as an order
            insertOrderIntoDatabase(cartItems, totalPrice);

            System.out.println("Order placed successfully. Thank you for shopping with us!");
            System.out.println("-------------------------------------------------");
            deletecartitems();
        } else {
            System.out.println("Order not placed. Your cart items have not been processed.");
            System.out.println("-------------------------------------------------");
        }

        
    }
    
    public void products() {
    	JDBC shoppingSystem=new JDBC();
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("Adding products as a seller: " + username);
        System.out.println("");
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter product price: ");
        double productPrice = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter product availability: ");
        int productAvailability = scanner.nextInt();
        scanner.nextLine(); 

        Product product = new Product(productName, productPrice, productAvailability);
        insertProductIntoDatabase(product);
        System.out.println("");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.println("3. Exit...");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

         switch (choice) {
             case 1:
                 shoppingSystem.products();
                 break;
             case 2:
                 shoppingSystem.updateproducts();
                 break;
             case 3:
            	 System.exit(0);
             default:
                 System.out.println("Invalid choice. Exiting...");
         }
        scanner.close();
    }
    
    public void insertProductIntoDatabase(Product product) {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSql = "INSERT INTO products (product_name, product_price, product_availbility) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getProductPrice());
            pstmt.setInt(3, product.getProductAvailability());
            pstmt.executeUpdate();
            System.out.println("product added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
   
    public List<Product> GetProductFromDatabase() {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectSql = "SELECT * FROM products";
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("product_name");
                double productPrice = rs.getDouble("product_price");
                int productAvailability = rs.getInt("product_availbility");

                Product product = new Product(productName, productPrice, productAvailability);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    public void deletecartitems() {
    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    	        String deleteSql = "truncate table cart_items";
    	        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
    	        pstmt.executeUpdate(); 
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
}


    public void insertCartItemIntoDatabase( CartItem cartItem) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSql = "INSERT INTO cart_items (product_name, quantity, price) VALUES ( ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
     
            pstmt.setString(1, cartItem.getProductName());
            pstmt.setInt(2, cartItem.getQuantity());
            pstmt.setDouble(3, cartItem.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cart() {
    	  JDBC shoppingSystem = new JDBC();
        List<Product> productList = GetProductFromDatabase();
        List<CartItem> cartItems = new ArrayList<>();
        
        Scanner scanner = new Scanner(System.in);
        boolean yogi=true;
        while (yogi) {
        	System.out.println("");
            System.out.println("-------------------------------------------------");
            System.out.println("Available Products:");
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                System.out.println((i + 1) + ". " + product.getProductName() + " - Price: $" + product.getProductPrice());
            }
            
            System.out.println("-------------------------------------------------");
            System.out.println("");
             System.out.print("Enter the product number to add to the cart: ");
             int productNumber = scanner.nextInt();
            scanner.nextLine();
            if (productNumber >= 1 && productNumber <= productList.size()) {
             Product selectedProduct = productList.get(productNumber - 1);
             
             
             System.out.print("Enter the count: ");
             int productcount = scanner.nextInt();
             double total=selectedProduct.getProductPrice()*productcount;
             CartItem cartItem = new CartItem(selectedProduct.getProductName(),productcount,total);
             cartItems.add(cartItem);
             System.out.println("Product added to cart Sucessfully");
             System.out.println("-------------------------------------------------");
             insertCartItemIntoDatabase(cartItem);
            yogi=false;
               }
           }
 
        System.out.println("What you like to do?");
        System.out.println("1. Add Another Product");
        System.out.println("2. View  Cart");
        System.out.println("3. View  Orders");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

         switch (choice) {
             case 1:
                 shoppingSystem.cart();
                 break;
             case 2:
                  
                 shoppingSystem.order();
                 break;
             case 3:

                 
                 break;
             default:
                 System.out.println("Invalid choice. Exiting...");
         }
   
        
        
        }
    
    private void sellerchoice() {
    	Scanner scanner = new Scanner(System.in);
    	JDBC shoppingSystem = new JDBC();
        System.out.println("");
    System.out.println("Welecome ! "+username);
    System.out.println("");
        System.out.println("What you like to do?");
        System.out.println("1. Add Product");
        System.out.println("2. Update Product");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        System.out.println("-------------------------------------------------");
        scanner.nextLine();

         switch (choice) {
             case 1:
                 shoppingSystem.products();
                 break;
             case 2:

                 shoppingSystem.updateproducts();
                 break;
             default:
                 System.out.println("Invalid choice. Exiting...");
         }
    }
    
    private void customerchoice() {
    	Scanner scanner = new Scanner(System.in);
    	JDBC shoppingSystem = new JDBC();
    	
    	System.out.println("");
    	System.out.println("hello customer "+username);
    	System.out.println("");
        System.out.println("What you like to do?");
        System.out.println("1. View Products");
        System.out.println("2. View  Cart");
        System.out.println("3. View  Orders");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

         switch (choice) {
             case 1:
                 shoppingSystem.cart();
                 break;
             case 2:
                  
                 shoppingSystem.order();
                 break;
             case 3:
            	getorders get = new getorders();

                 get.getOrders();
                 break;
             default:
                 System.out.println("Invalid choice. Exiting...");
         }
    }
public void connectToDatabase() {

    	  try {
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//              System.out.println("Connected to the database successfully.");
          } catch (ClassNotFoundException | SQLException e) {
              e.printStackTrace();
          }
    }
    private void insertCustomerIntoDatabase(Customer customer) {
    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(
                         "INSERT INTO customers (username, password, name, email, mobile) VALUES (?, ?, ?, ?, ?)")) {
                pstmt.setString(1, customer.getUsername());
                pstmt.setString(2, customer.getPassword());
                pstmt.setString(3, customer.getName());
                
                displayname=customer.getName();
                pstmt.setString(4, customer.getEmail());
                pstmt.setString(5, customer.getMobile());
                pstmt.executeUpdate();
                System.out.println("Customer Registered successfully.");
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
    	 
    }

    
    
    public void insertSellerIntoDatabase(Seller seller) {
    	try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO sellers (username, password, name, email, mobile) VALUES (?, ?, ?, ?, ?)")) {
               pstmt.setString(1, seller.getUsername());
               pstmt.setString(2, seller.getPassword());
               pstmt.setString(3, seller.getName());
               
               username=seller.getName();
               
               
               pstmt.setString(4, seller.getEmail());
               pstmt.setString(5, seller.getMobile());
               pstmt.executeUpdate();
               System.out.println("Seller Registered successfully.");
               System.out.println("");
               
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }

    public void newseller() {
    	 JDBC shoppingSystem = new JDBC();
         shoppingSystem.connectToDatabase();

         Scanner scanner = new Scanner(System.in);
         System.out.println("");
    	 System.out.println("");
    	 System.out.println("---------Register to continue---------");
    	 System.out.println("");
    	System.out.print("Enter your username: ");
        String sellerUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String sellerPassword = scanner.nextLine();
        System.out.print("Enter your name: ");
        String sellerName = scanner.nextLine();
        displayname=sellerName;
        System.out.print("Enter your email: ");
        String sellerEmail = scanner.nextLine();
        System.out.print("Enter your mobile: ");
        String sellerMobile = scanner.nextLine();

        Seller seller = new Seller(sellerUsername, sellerPassword, sellerName, sellerEmail, sellerMobile);
        shoppingSystem.insertSellerIntoDatabase(seller);
        System.out.println("-------------------------------------------------");
        shoppingSystem.sellerchoice();
     }
    
    public void newcustomer() {
    	 JDBC shoppingSystem = new JDBC();
         shoppingSystem.connectToDatabase();

         Scanner scanner = new Scanner(System.in);
         
    	System.out.println("");
    	System.out.println("--------Register to continue---------");

    	System.out.print("Enter your username: ");
        String CustomerUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String CustomerPassword = scanner.nextLine();
        System.out.print("Enter your name: ");
        String CustomerName = scanner.nextLine();
        username=CustomerName;
        System.out.print("Enter your email: ");
        String CustomerEmail = scanner.nextLine();
        System.out.print("Enter your mobile: ");
        String CustomerMobile = scanner.nextLine();
        Customer customer = new Customer(CustomerUsername, CustomerPassword,CustomerName,CustomerEmail,CustomerMobile);
        shoppingSystem.insertCustomerIntoDatabase(customer);
        System.out.println("----------------------------------------");
        shoppingSystem.customerchoice();
    }
    
    
    public static void main(String[] args) {
        JDBC shoppingSystem = new JDBC();
        shoppingSystem.connectToDatabase();

        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        
        System.out.println("Are you a Seller or Customer?");
        System.out.println("1. Seller");
        System.out.println("2. Customer");
        System.out.print("Enter your choice: ");
      
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
            	
            	System.out.println("");
            	System.out.println("Already a seller pls login or register");
            	System.out.println("1. Login");
            	System.out.println("2. Register");
            	System.out.print("Enter your choice: ");
            	 int choice1 = scanner.nextInt();
                 scanner.nextLine();
                 
                 switch(choice1) {
                 case 1:
                	 System.out.println("");
                	 
                	 System.out.print("Enter your username: ");
                     String name = scanner.nextLine();
                     username=name;
                     System.out.print("Enter your password: ");
                     String Password = scanner.nextLine();
                     
                     LoginValidation login=new LoginValidation();
                     if(login.validateSellerLogin(name,Password)) {
                    	 System.out.println("Login Successfull..");
                    	 shoppingSystem.sellerchoice();
                     }else {
                    	 System.out.println("Invalid username or password..");
                    	 System.out.println("-------------------------------------------------");
                     }
                    
                	 
                	 break;
                	 
                 case 2:
                	 shoppingSystem.newseller();
                	 break;
                 default:
                     System.out.println("Invalid choice. Exiting...");
                 }
                 
                break;
            case 2:
            	System.out.println("");
            	System.out.println("Already a customer pls login or register");
            	System.out.println("1. Login");
            	System.out.println("2. Register");
            	 int choice2 = scanner.nextInt();
                 scanner.nextLine();
                 
                 switch(choice2) {
                 case 1:
                     System.out.println("");
                     
                	 System.out.print("Enter your username: ");
                     String name = scanner.nextLine();
                     username=name;
                     System.out.print("Enter your password: ");
                     String Password = scanner.nextLine();
                     
                     LoginValidation login=new LoginValidation();
                     if(login.validateCustomerLogin(name,Password)) {
                    	 System.out.println("Login Successfull..");
                    	 shoppingSystem.customerchoice();
                     }else {
                    	 System.out.println("Invalid username or password..");
                     }
                	 
                	 
                	 break;
                	 
                 case 2:
                	 shoppingSystem.newcustomer();
                	 break;
                 default:
                     System.out.println("Invalid choice. Exiting...");
                 }
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
        }

        shoppingSystem.closeConnection();
        scanner.close();
    }
    
    public void closeConnection() {
      
    }
}



