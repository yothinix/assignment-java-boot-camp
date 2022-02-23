# Order Add API
Create a new order and order item with selected product or adding product to existing order and create new order item

## Specification
* **path**: /orders/
* **method**: POST

### Query Params
-
request: user_id + product_id ⇒ Order, Order Item

return: Order | Order Item | Product | Product Image | Product Variant
### Request body
```json
{
    "user_id": 1,
    "product_id": 1
}
```

### Response body
```json
{
    "id": 1,
    "user": {
        "id": 1,
        "username": "yothinix"
    },
    "payment": {
        "id": 1,
        "user": {
            "id": 1,
            "username": "yothinix"
        },
        "method": "credit",
        "number": "1111222233334444",
        "name": "Human card",
        "payment_expiry_date": "0122",
        "secure_code": "123"
    },
    "shipping": {
        "id": 1,
        "user": {
            "id": 1,
            "username": "yothinix"
        },
        "email": "human@email.com",
        "name": "Human address",
        "address": "12/34 example road",
        "postal_code": "10110",
        "district": "Chatuchak",
        "province": "Krung Thep Maha Nakhon",
        "telephone": "0812345678"
    },
    "total_amount": 399.0,
    "transaction_date": "2022-02-22",
    "expired_date": "2022-02-22",
    "order_status": "pending",
    "order_items": [
        {
            "id": 1,
            "product": {
                "id": 1,
                "name": "POCA SHOE NMD Sneaker Fasion",
                "brand": "Poca Shoes",
                "category": "สุภาพบุรุษ",
                "description": "* สวมใส่สบาย",
                "price": 399.0,
                "product_image": [
                    {
                        "id": 1,
                        "product_id": 1,
                        "image_path": "/media/poca.jpg",
                        "is_feature_image": true
                    }
                ],
                "product_variant": [
                    {
                        "id": 1,
                        "product_id": 1,
                        "variant_name": "ขนาด",
                        "variant_choice": "S",
                        "is_default_choice": true
                    }
                ]
            },
            "amount": 399.0,
            "payment_price": 399.0
        }
    ]
}
```

## Sequence Diagram
```mermaid
sequenceDiagram
    participant user
    participant frontend
    participant backend
    participant database
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
```