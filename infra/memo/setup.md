## AWS SSOの設定
```bash
$ aws configure sso
# SSOの初期設定
SSO session name (Recommended): hirotake-sso
SSO start URL [None]: https://identitycenter.amazonaws.com/ssoins-7758ba311761905b
SSO region [None]: ap-northeast-1
SSO registration scopes [sso:account:access]:
Attempting to automatically open the SSO authorization page in your default browser.
If the browser does not open or you wish to use a different device to authorize this request, open the following URL:

https://oidc.ap-northeast-1.amazonaws.com/authorize?response_type=code&client_id=BA7nbgHbL1uydj-3jLTS1GFwLW5vcnRoZWFzdC0x&redirect_uri=http%3A%2F%2F127.0.0.1%3A50105%2Foauth%2Fcallback&state=ed667712-df87-45d5-a31a-024940b94743&code_challenge_method=S256&scopes=sso%3Aaccount%3Aaccess&code_challenge=Cdn2Qeta4PCMM2pV2CILZDwsKaEVcnWhdTt-AMmA5Is

# ブラウザが自動で立ち上がるためログイン
The only AWS account available to you is: 992382546260
Using the account ID 992382546260
The only role available to you is: AdministratorAccess
Using the role name "AdministratorAccess"
# CLIの初期設定
Default client Region [None]: ap-northeast-1
CLI default output format (json if not specified) [None]:
Profile name [AdministratorAccess-992382546260]: hirotake-sso
To use this profile, specify the profile name using --profile, as shown:

aws sts get-caller-identity --profile hirotake-sso

# ログイン
aws sso login --profile hirotake-sso
```

## terraform
```bash
# ディレクトリ移動
cd "C:\Users\shulk\workspace\github.com\BIONICLE0\cicd-demo\infra\terraform"
# セットアップ
terraform init
# 差分確認
terraform plan
# 作成
terraform apply
# 削除
terraform destroy
# 差分確認
terraform state list
```

## デプロイ手順

```bash
# ECR作成
terraform apply -target="aws_ecr_repository.todo"

# コンテナイメージプッシュ
$ecr_repository_url = "992382546260.dkr.ecr.ap-northeast-1.amazonaws.com/todo-api" 
echo $ecr_repository_url

aws ecr get-login-password --region ap-northeast-1 --profile hirotake-sso |
docker login --username AWS --password-stdin $ecr_repository_url

docker tag docker-image:test "${ecr_repository_url}:latest"

docker push "${ecr_repository_url}:latest"
```