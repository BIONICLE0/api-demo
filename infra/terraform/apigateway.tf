resource "aws_apigatewayv2_api" "todo" {
  name          = "todo-api"
  protocol_type = "HTTP"
}

resource "aws_apigatewayv2_authorizer" "cognito" {
  api_id           = aws_apigatewayv2_api.todo.id
  authorizer_type  = "JWT"
  name             = "cognito-authorizer"
  identity_sources = ["$request.header.Authorization"]

  jwt_configuration {
    audience = [
      aws_cognito_user_pool_client.todo.id
    ]

    issuer = "https://cognito-idp.ap-northeast-1.amazonaws.com/${aws_cognito_user_pool.todo.id}"
  }
}


resource "aws_apigatewayv2_integration" "lambda" {
  api_id                 = aws_apigatewayv2_api.todo.id
  integration_type       = "AWS_PROXY"
  integration_uri        = aws_lambda_function.todo.invoke_arn
  payload_format_version = "2.0"
}

resource "aws_apigatewayv2_route" "get_todos" {
  api_id    = aws_apigatewayv2_api.todo.id
  route_key = "GET /todos"
  target    = "integrations/${aws_apigatewayv2_integration.lambda.id}"

  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.cognito.id

  authorization_scopes = [
    "todo-api/read"
  ]
}

resource "aws_apigatewayv2_route" "post_todos" {
  api_id    = aws_apigatewayv2_api.todo.id
  route_key = "POST /todos"
  target    = "integrations/${aws_apigatewayv2_integration.lambda.id}"

  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.cognito.id

  authorization_scopes = [
    "todo-api/write"
  ]
}

resource "aws_apigatewayv2_route" "get_todo" {
  api_id    = aws_apigatewayv2_api.todo.id
  route_key = "GET /todos/{id}"
  target    = "integrations/${aws_apigatewayv2_integration.lambda.id}"

  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.cognito.id

  authorization_scopes = [
    "todo-api/read"
  ]
}

resource "aws_apigatewayv2_route" "put_todo" {
  api_id    = aws_apigatewayv2_api.todo.id
  route_key = "PUT /todos/{id}"
  target    = "integrations/${aws_apigatewayv2_integration.lambda.id}"

  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.cognito.id

  authorization_scopes = [
    "todo-api/write"
  ]
}

resource "aws_apigatewayv2_route" "delete_todo" {
  api_id    = aws_apigatewayv2_api.todo.id
  route_key = "DELETE /todos/{id}"
  target    = "integrations/${aws_apigatewayv2_integration.lambda.id}"

  authorization_type = "JWT"
  authorizer_id      = aws_apigatewayv2_authorizer.cognito.id
  
  authorization_scopes = [
    "todo-api/delete"
  ]
}

resource "aws_lambda_permission" "api_gateway" {
  statement_id  = "AllowApiGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.todo.function_name
  principal     = "apigateway.amazonaws.com"

  source_arn = "${aws_apigatewayv2_api.todo.execution_arn}/*/*"
}

resource "aws_apigatewayv2_stage" "default" {
  api_id      = aws_apigatewayv2_api.todo.id
  name        = "$default"
  auto_deploy = true
}