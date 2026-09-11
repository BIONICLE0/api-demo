### 1. ユーザー作成

```bash
# 変数定義
$api_endpoint = "https://bdqfvpeugk.execute-api.ap-northeast-1.amazonaws.com/"
$cognito_client_id = "6hmueq840j72bjsdn7klod3dav"
$cognito_user_pool_id = "ap-northeast-1_9Fv35D7Ab"

# ユーザー作成
aws cognito-idp sign-up `
  --client-id  $cognito_client_id `
  --username test@example.com `
  --password "Test1234!" `
  --profile hirotake-sso

aws cognito-idp admin-confirm-sign-up `
  --user-pool-id $cognito_user_pool_id `
  --username test@example.com `
  --profile hirotake-sso

aws cognito-idp initiate-auth `
  --client-id $cognito_client_id `
  --auth-flow USER_PASSWORD_AUTH `
  --auth-parameters USERNAME=test@example.com,PASSWORD="Test1234!" `
  --profile hirotake-sso

curl "{$api_endpoint}/todos"  

$access_token = "eyJraWQiOiJBNkIycmhQUEE0TDBtRGZTandxSXNNRTg4Tk1Yb3FKbkt6b1dSUnJRVXZRPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIzNzI0ZWE2OC03MGIxLTcwNTEtYmY3Zi02ZDA5OTgyOGYxZjIiLCJpc3MiOiJodHRwczovL2NvZ25pdG8taWRwLmFwLW5vcnRoZWFzdC0xLmFtYXpvbmF3cy5jb20vYXAtbm9ydGhlYXN0LTFfOUZ2MzVEN0FiIiwiY2xpZW50X2lkIjoiNmhtdWVxODQwajcyYmpzZG43a2xvZDNkYXYiLCJvcmlnaW5fanRpIjoiZWEzN2JjOGUtYWU0YS00YzZlLWE3ZTMtMWFiNjZhZjBhNTE1IiwiZXZlbnRfaWQiOiJjYmEyMGVjZC00MTZkLTQyZjctODFlZS00ODMxZmNjZjAxYTIiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNzg5MTE2NDA0LCJleHAiOjE3ODkxMjAwMDQsImlhdCI6MTc4OTExNjQwNCwianRpIjoiMGFiZjJlNDgtNWUyNi00ZWE1LTlkNzMtOWIyMjhiYmVjZWM3IiwidXNlcm5hbWUiOiIzNzI0ZWE2OC03MGIxLTcwNTEtYmY3Zi02ZDA5OTgyOGYxZjIifQ.WnCSUOcX7Uv8BrddqpF4G8Lk6wVhVm0y8QJhvNr_ZUf1jtNDHj8lbQYU3SGfP7dpDe1nXCfla3hcpfOqV5qDOnrQVUTnJypgjpgzYhFLlQxXAo6zRaaB2ZdQgPgN4LysxDCxbiALUJQMdmdSpzasu_vJhtQPTXNaiX6HQRGwhu5q9FARtFeCc02LtKwu-1_D_o2Q0z7vTEd4-zLCObvpnazwn5Gg9SOUuOQJAv6UeXrshMDPy90VoHLRt8u7056WhGtCnL66HR0dgBMMB3gv12k3DAS9vUckDzmETqIIqWwiVpDHLWvnAE7dAoKvb3umbJAB6TXT70Hd-18prFXmrw"

curl "{$api_endpoint}/todos" `
  -H "Authorization: Bearer $access_token"

curl.exe -X POST "${api_endpoint}/todos" `
  -H "Authorization: Bearer $access_token" `
  -H "Content-Type: application/json" `
  -d '{"title":"Cognitoを勉強する"}'
```


