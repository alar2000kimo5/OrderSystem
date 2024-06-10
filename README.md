# OrderSystem

Imagine you have a trade engine that accepts orders via the protocol (or triggering) 
you defined. An order request at least has this information (buy or sell, quantity, 
market price or limit price).
The engine matches buy and sell orders that have the same price. Orders have the 
same price determined by their timestamp (FIFO). Pending orders queue up in your 
system until they are filled or killed. Your system will have multiple traders executing 
orders at the same time.
## What is expected?
- SOLID design accommodates requirements to change in the future.
- Testable, compilable and runnable code.
- Concurrent and thread-safe considered.
- Document your design which can be any human-readable form. For example, 
README file

## 目錄

- [設計架檔](#設計架構)
- [資料流程](#資料流程)
- [CLASS UML](#CLASSUML)
- [Test](#Test)
- [Redis](#Redis)

# 設計架構
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/1f46f856-9547-4c5c-8076-3524e1eeae67)

# 資料流程
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/e1eb4c2a-16ed-4a9c-b84c-113f673b8e2a)

# CLASSUML
![OrderSystemUML](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/c84e5212-a186-49cb-8a68-eac03025bdae)

# Test
order
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/f92a5ad3-2338-44ab-bdea-e6c48bb7cce0)
match
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/f23a8217-4448-48fd-b08b-381fe3dac2e8)


# Redis
使用dockerDesktop 安裝redis
docker exec -it redis redis-cli
Zrange orderQueue30 0 -1 withscores







