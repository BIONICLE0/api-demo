resource "aws_cognito_user_pool" "todo" {
  name = "todo-api-user-pool"

  username_attributes = ["email"]

  auto_verified_attributes = ["email"]

  password_policy {
    minimum_length                   = 8
    require_lowercase                = true
    require_uppercase                = true
    require_numbers                  = true
    require_symbols                  = false
    temporary_password_validity_days = 7
  }
}


resource "aws_cognito_user_pool_client" "todo" {
  name         = "todo-api-client"
  user_pool_id = aws_cognito_user_pool.todo.id

  generate_secret = false

  explicit_auth_flows = [
    "ALLOW_USER_PASSWORD_AUTH",
    "ALLOW_REFRESH_TOKEN_AUTH"
  ]
}