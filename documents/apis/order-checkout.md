# Order Checkout API
Process order by charging the payment to payment gateway and update the order status when charge success

## Specification
* **path**: /checkout
* **method**: POST

### Query Params
- No
### Request body
```json
{
    "order_id": 1,
    "payment_id": 1
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
    participant frontend
    participant backend
    participant database
    participant paymentGateway
    frontend->>backend: POST checkout/ {order_id: Order.id payment_id: Payment.id}
    backend->>database: update Order set payment_id=<id>
    database->>backend: return Order
    backend->>paymentGateway: POST /change {Payment, payee}
    paymentGateway->>backend: Response charge success
    backend->>database: update Order set status=Paid
    database->>backend: return Order
    backend->>frontend: Return Order with status Paid
```