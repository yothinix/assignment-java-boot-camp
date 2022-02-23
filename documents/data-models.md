# Data models

## Products

### Product

- id: int
- name: str
- brand: str
- category: str
- description: str
- price: double

### Product Image

- id: int
- product_id: int
- image_path: str
- is_feature_image: Optional[Bool]

### Product Variant

- id: int
- product_id: int
- variant_name: str
- variant_choice: str
- is_default_choice: Optional[Bool]

### Product Review

- id: int
- user_id: int
- product_id: int
- comment: str
- rating: int

## Users

### User

- id: int
- username: str

### User Address

- id: int
- user_id: int
- email: str
- name: str
- address: str
- postal_code: str
- district: str
- province: str
- telephone: str

## Payments

### Payment

- id: int
- user_id: int
- method str: (credit, debit, line pay)
- number: str
- name: str
- payment_expiry_date: str
- secure_code: str

## Orders

### Order

- id: int
- user_id: int
- payment_id: int
- shipping_id: int
- total_amount: double
- transaction_date: date
- expired_date: date
- order_status str: (pending, paid, prepare, shipped)

### Order Item

- id: int
- order_id: int
- product_id: int
- amount: double
- payment_price: double