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

- [安裝](#安裝)
- [使用](#使用)
- [功能](#功能)
- [聯繫信息](#聯繫信息)

## 設計
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/9af33b46-51c4-4303-99cc-4db2a20a186e)


## Redis
使用dockerDesktop 安裝redis
docker exec -it redis redis-cli
LRANGE orderQueue 0 -1







