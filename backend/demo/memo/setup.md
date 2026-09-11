### 1. プロジェクト作成

```bash
# コマンドパレットを開く  
Ctrl + Shift + P

# Spring Initializr起動  
Create a Maven Project

# Specify Spring Boot version
4.1.1

# Specify project language
Java

# Input Group Id
com.exmaple

# Artifact Id
demo

# Package Name
com.example.demo

# Specify packaging type
Jar

# Specify Java version
17

# Search for dependencies
```

### 2. DynamoDBテーブル作成

```bash
# 作成
aws dynamodb create-table `
  --table-name Todos `
  --attribute-definitions AttributeName=id,AttributeType=S `
  --key-schema AttributeName=id,KeyType=HASH `
  --billing-mode PAY_PER_REQUEST `
  --endpoint-url http://dynamodb:8000 `
  --region ap-northeast-1 `
  --profile local
# 確認
aws dynamodb list-tables `
  --endpoint-url http://localhost:8000 `
  --region ap-northeast-1 `
  --profile local
```

### 3. DBクライアント作成
```bash
# クライアントコード
C:\Users\shulk\workspace\github.com\BIONICLE0\api-demo\backend\demo\src\main\java\com\example\demo\config\DynamoDbConfig.java
# 接続テストコード
C:\Users\shulk\workspace\github.com\BIONICLE0\api-demo\backend\demo\src\main\java\com\example\demo\DynamoDbConnectionTest.java
```

### 4. model,controller,repository,service作成
```bash
C:\Users\shulk\workspace\github.com\BIONICLE0\api-demo\backend\demo\src\main\java\com\example\demo
```

### 5. テストコード作成
```bash
# 単体
C:\Users\shulk\workspace\github.com\BIONICLE0\api-demo\backend\demo\src\test\java\com\example\demo\controller\TodoControllerTest.java
C:\Users\shulk\workspace\github.com\BIONICLE0\api-demo\backend\demo\src\test\java\com\example\demo\service\TodoServiceTest.java
# 結合
C:\Users\shulk\workspace\github.com\BIONICLE0\api-demo\backend\demo\src\test\java\com\example\demo\integration\TodoIntegrationTest.java
```

### 5. 起動
```bash
# 前回ビルド結果の削除
mvn clean

# アプリ起動
mvn spring-boot:run

# テスト
 ./mvnw clean test

# テストなしでビルド
 ./mvnw clean package -DskipTests
```

### 6. ローカルテスト

```bash
# 作成
curl -X POST http://localhost:8080/todos `
  -H "Content-Type: application/json" `
  -d '{"title":"AWSを勉強する"}'

# 全件取得
curl http://localhost:8080/todos

# ID=1取得
$ID = "f837352e-1f81-4498-bc77-1dc22c674a37"
curl http://localhost:8080/todos/$ID

# 更新
curl -X PUT http://localhost:8080/todos/$ID `
  -H "Content-Type: application/json" `
  -d '{"title":"AWSとSpring Bootを勉強する"}'

# 更新確認
curl http://localhost:8080/todos/$ID

# 削除
curl -X DELETE http://localhost:8080/todos/$ID

# 削除確認
curl http://localhost:8080/todos/$ID
```

### 7. コンテナイメージ作成
```bash
# 作成
docker buildx build --platform linux/amd64 --provenance=false -t docker-image:test .

# 起動
docker run --rm `
  -p 8888:8080 `
  -e SPRING_PROFILES_ACTIVE=aws `
  todo-lambda:latest
```