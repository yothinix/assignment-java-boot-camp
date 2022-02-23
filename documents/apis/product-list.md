# Product List API
A product list API that return all product or filter product by name

## Specification
* **path**: /products
* **method**: GET

### Query Params
* **name (str)**: a product name can be user to search for example: Adidas NMD

### Request body
```json
-
```

### Response body
```json
{
    products: [
        {
            "id": 1,
            "name": "POCA SHOE NMD Sneaker Fasion",
            "brand": "Poca Shoes",
            "category": "สุภาพบุรุษ",
            "description": "* สวมใส่สบาย",
            "price": 399.0
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
    user->>frontend: 1. User enter the website and search product by name
    frontend->>backend: GET products/?name=<query>
    backend->>database: select * from product where name like '%<query>%'
    database->>backend: List<Product>
    backend->>frontend: Return list of product contain the query name
    frontend->>user: Display the list of product matched the query name
```