# Product Detail API
Return product detail of selected ID

## Specification
* **path**: /products/:id
* **method**: GET

### Query Params
-

### Request body
-

### Response body
```json
{
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
    ],
    "product_review": [
        {
            "id": 1,
            "user_id": 1,
            "product_id": 1,
            "comment": "Banana is not apple",
            "rating": 5
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
```