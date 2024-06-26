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
- [設計理念](#設計理念)
- [CLASS UML](#CLASSUML)
- [Test](#Test)
- [Redis](#Redis)

# 設計架構
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/13980d42-0cf0-4f69-b9fe-e1d742d0ac3a)

# 資料流程
![image](https://github.com/alar2000kimo5/OrderSystem/assets/79575202/e1eb4c2a-16ed-4a9c-b84c-113f673b8e2a)

# 設計理念
- 由外部reqeust進入usecase，在usecase會分依照屬性分配到某個redis 上的queue。
- 屬性可以是以價錢的範圍切分，這樣先進來的匹配條件就會落在同一個價錢範圍中
- matchEngine會依照usecase介面中提供的實作方法得到總共有幾個queue要拿下來做匹配處理
- metchEngine每秒會發送多執行緒至redis取得資料
- 假如有三台server，redis有三個queue、設定每個執行緒取得鎖的等待時間不同來避免同一台一直處理事情的問題

# 程式結構說明

| 層級            | 路徑                                                        | 功能描述                                                                                                                                                  |
|-----------------|-------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| 介面層          | `src/test/java/com/order/OrderSystem/adapter/`              | clean 架構中隔離效果 adapter/in 主要為controller 進行request/response資料轉換，adapter/out 為 application/out 隔離第三方行為實作                         |                                   
| 應用層          | `src/main/java/com/order/OrderSystem/application/`           |應用邏輯，包括訂單匹配引擎、訂單實作用例。                                                                                                             |
| 引擎包          | `src/main/java/com/order/OrderSystem/application/engine/`   | 實作，`OrderMatchEngine.java`，`Order.java`，訂單匹配引擎邏輯和訂單。                                                                                |
| 用例包        | `src/main/java/com/order/OrderSystem/application/in/`          |實作，`OrderUseCase.java` 實現應用用例。                                                                                          |
| 應用介面包          | `src/main/java/com/order/OrderSystem/application/out/`   | 定義，此包內部只有介面提供給應用層使用 `OrderRepository.java`、`RedisLockService.java`、`RedisQueueZSetService.java` 和 `RedisService.java`，處理數據存取和Redis服務。                 |
| 域層            | `src/main/java/com/order/OrderSystem/domain/`               | 定義，包括抽象匹配引擎類 `MatchEngine.java`、應用介面類 `UseCase.java`、訂單抽象類 `BaseOrder.java` 和實現資料類 `OrderMatchEntity.java`。  |                                                      
| 類型定義包      | `src/main/java/com/order/OrderSystem/domain/type/`          | 定義 `InComeType.java` 和 `PriceType.java`，定義買賣、市價或限價類。                                                                                               |
| 配置包          | `src/main/java/com/order/OrderSystem/springconfig/`         | 實作 `RedisConfig.java`，配置 Spring 和 Redis 的相關設置。                                                                                               |
| 資源目錄        | `src/main/resources/`                                       | `application.properties`，應用程序的配置文件。                                                                                                      |
| 測試目錄        | `src/test/java/com/order/OrderSystem/`                      | 實作測試代碼，如 `BaseOrderSystemApplicationTests.java`，`RedisLockServiceImplTest.java`，`RedisQueueZSetServiceImplTest.java`，`OrderMatchEngineTest.java`。 |


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

# 延伸問題
- 能做到分批匹配的狀況嗎？例如：A買家買10單位、B賣家賣6單位、C賣家賣4單位 ，怎麼做到？
- 另一個狀況如果A買家10單位、B賣家6單位，匹配完成功，B賣家還有4單位，需要更新回redis
- 在orderMatchEngine isMatch 匹配的效率，foreach 時匹配到就可以中斷迴圈，並可開始通知寫入db匹配資料動作，與刪除redis資料動作
- 寫入db匹配與刪除或更新redis資料動作可由另一個服務處理，並在中間使用queue來做介接
- 如果一開始送到usecase的資料在送往redis前就掉資料，有什麼辦法處理呢？
- 嘗試使用k8s建立image並部署
- 嘗試使用 golang
  
  





