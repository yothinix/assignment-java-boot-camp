# Product Search and checkout flow
All screen mention in the API spec refer to [this document](https://github.com/up1/course_microservices-3-days/blob/master/slide/01-DESIGN-MICROSERVICE-WORKSHOP.pdf)

## Description
In this flow, The user will search for product by name. Then the customer will select the product to view detail such as Product image, the review average and the product price. After the user review they will click "ใส่ตระกร้า" and the system will navigate to display the detail of previous add product and the check out button name "ชำระสินค้า"

When the user click on the checkout button the system will prompt user to add a shipping address which contain email, the user full name, address, postal code, district, province and telephone number. In this shipping address screen the user also saw an order summary and a next button label as "ดำเนินการต่อ" which will add the shipping address to the order and navigate to the payment detail page.

In the payment detail page user will have an option to select payment method for order from credit card, debit card, and line pay. when the user select one of the payment method it will display the payment detail form for example credit card will display the card number, card holder name, card expiry date and card secure code. The user also can view the address they add on the previous step and the order summary in this screen too. When they finish add the payment detail the user can click the purchase button label as "สั่งซื้อสินค้า" then the system will call the payment gateway to charge the customer and update the order status to be paid. 

When the backend process complete it will redirect the user to the summary page which will display the invoice number, the payer, transaction date, expired transaction date, payee and other detail. It will also display the total amount of the order as well. In this step the user has finish the purchasing and can wait for the product to ship to their address.
```mermaid
sequenceDiagram
    participant user
    participant frontend
    participant backend
    participant database
    participant paymentGateway
    user->>frontend: 1. User enter the website and search product by name
    frontend->>backend: GET products/?name=<query>
    backend->>database: select * from product where name like '%<query>%'
    database->>backend: List<Product>
    backend->>frontend: Return list of product contain the query name
    frontend->>user: Display the list of product matched the query name
    user->>frontend: 2. Choose a product
    frontend->>backend: GET products/:id/
    backend->>database: select * from product where id=<id>
    database->>backend: List<Product>
    backend->>database: select * from product_image where product_id=<id>
    database->>backend: List<Product Image>
    backend->>database: select * from product_variant where product_id=<id>
    database->>backend: List<Product Variant>
    backend->>database: select * from product_review where product_id=<id>
    database->>backend: List<Product Review>
    backend->>backend: compose response object
    backend->>frontend: Return product detail of specific ID including related Product Image, Product Variant and Product Review
    frontend->>user: 3. Display product detail page
    user->>frontend: 4. Click Add product to basket
    frontend->>backend: POST order/ {"user_id": int, "product_id": int}
    backend->>database: insert Order with status pending
    database->>backend: return Order
    backend->>database: insert Order Item related to Order.id
    database->>backend: return Order Item
    backend->>database: select * from product where id=<id>
    database->>backend: List<Product>
    backend->>database: select * from product_image where product_id=<id>
    database->>backend: List<Product Image>
    backend->>database: select * from product_variant where product_id=<id>
    database->>backend: List<Product Variant>
    backend->>backend: compose response object
    backend->>frontend: Return Order detail including the product detail of the order
    frontend->>user: 5. Show data in basket
    user->>frontend: 6. User click checkout
    frontend->>backend: GET order/:id
    backend->>database: select * from order where id=<id>
    database->>backend: Order object
    backend->>database: select * from order_item where order_id=Order.id
    database->>backend: List<Order Item>
    backend->>backend: compose response object
    backend->>frontend: Return Order detail and the Order item
    frontend->>user: Redirect to shipping detail screen
    user->>frontend: 7. Entering shipping address
    user->>frontend: Click proceed button
    frontend->>backend: POST users/address/
    backend->>database: insert User Address
    database->>backend: return User Address
    backend->>frontend: Return User address
    frontend->>backend: PATCH order/:id {shipping_id: UserAddress.id}
    backend->>database: update Order set shipping_id=UserAddress.id
    database->>backend: return Order
    backend->>frontend: Return update order success
    frontend->>user: Redirect to payment screen
    user->>frontend: 8. Entering Payment detail
    user->>frontend: 9. Click Confirm to order button
    frontend->>backend: POST payments/
    backend->>database: insert Payment
    database->>backend: return Payment
    backend->>frontend: Response insert Payment success
    frontend->>backend: POST checkout/ {order_id: Order.id payment_id: Payment.id}
    backend->>database: update Order set payment_id=<id>
    database->>backend: return Order
    backend->>paymentGateway: POST /change {Payment, payee}
    paymentGateway->>backend: Response charge success
    backend->>database: update Order set status=Paid
    database->>backend: return Order
    backend->>frontend: Return Order with status Paid
    frontend->>user: Redirect to order summary screen
```