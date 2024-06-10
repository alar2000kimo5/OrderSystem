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
- [Class UML](#CLASS UML)
- [Redis](#Redis)

# 設計架構
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/1f46f856-9547-4c5c-8076-3524e1eeae67)

# 資料流程
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/69f8cdcf-1fa8-4abc-baae-eba3675834c5)

# CLASS UML
![OrderSystemUML](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/aceb8f19-4df4-4fc6-95ed-4cdb68aa0e66)

# Redis
使用dockerDesktop 安裝redis
docker exec -it redis redis-cli
Zrange orderQueue30 0 -1 withscores







