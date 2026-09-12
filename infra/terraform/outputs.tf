output "ecr_repository_url" {
  value = aws_ecr_repository.todo.repository_url
}

output "lambda_function_name" {
  value = aws_lambda_function.todo.function_name
}

output "dynamodb_table_name" {
  value = aws_dynamodb_table.todos.name
}

output "api_endpoint" {
  value = aws_apigatewayv2_stage.default.invoke_url
}

output "cognito_user_pool_id" {
  value = aws_cognito_user_pool.todo.id
}

output "cognito_client_id" {
  value = aws_cognito_user_pool_client.todo.id
}

output "github_role_arn" {
  value = aws_iam_role.github_actions_role.arn
}

output "terraform_role" {
  value = aws_iam_role.terraform_role.arn
}