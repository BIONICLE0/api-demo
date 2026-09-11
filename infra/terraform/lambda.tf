resource "aws_iam_role" "lambda" {
  name = "todo-api-lambda-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"

        Principal = {
          Service = "lambda.amazonaws.com"
        }

        Action = "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_basic_execution" {
  role       = aws_iam_role.lambda.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_iam_role_policy" "lambda_dynamodb" {
  name = "todo-api-lambda-dynamodb"
  role = aws_iam_role.lambda.id

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"

        Action = [
          "dynamodb:PutItem",
          "dynamodb:GetItem",
          "dynamodb:Scan",
          "dynamodb:UpdateItem",
          "dynamodb:DeleteItem"
        ]

        Resource = aws_dynamodb_table.todos.arn
      }
    ]
  })
}

resource "aws_lambda_function" "todo" {
  function_name = "todo-api"

  package_type = "Image"

  image_uri = "${aws_ecr_repository.todo.repository_url}:latest"

  role = aws_iam_role.lambda.arn

  timeout     = 30
  memory_size = 512

  environment {
    variables = {
      SPRING_PROFILES_ACTIVE = "aws"
    }
  }

  depends_on = [
    aws_iam_role_policy_attachment.lambda_basic_execution,
    aws_iam_role_policy.lambda_dynamodb
  ]
}