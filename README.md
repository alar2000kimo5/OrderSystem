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
- [功能](#功能)
- [Redis](#Redis)

# 設計架構
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/1f46f856-9547-4c5c-8076-3524e1eeae67)

# 資料流程
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/0657742b-841d-4b15-b949-4e19141eeb8a)


[Uploading o<mxfile host="Electron" modified="2024-06-08T07:06:34.410Z" agent="5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) draw.io/20.8.16 Chrome/106.0.5249.199 Electron/21.4.0 Safari/537.36" etag="Tu6d10AO1obzBTAF2se-" version="20.8.16" type="device"><diagram name="Page-1" id="13e1069c-82ec-6db2-03f1-153e76fe0fe0">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</diagram></mxfile>rderFlow.drawio…]()



# Redis
使用dockerDesktop 安裝redis
docker exec -it redis redis-cli
LRANGE orderQueue 0 -1







