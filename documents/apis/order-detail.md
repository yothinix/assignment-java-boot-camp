# Order Detail API
Return current order by id

## Specification
* **path**: /orders/:id
* **method**: GET

### Query Params
- No
### Request body
- No

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
    user->>frontend: 6. User click checkout
    frontend->>backend: GET order/:id
    backend->>database: select * from order where id=<id>
    database->>backend: Order object
    backend->>database: select * from order_item where order_id=Order.id
    database->>backend: List<Order Item>
    backend->>backend: compose response object
    backend->>frontend: Return Order detail and the Order item
    frontend->>user: Redirect to shipping detail screen
```