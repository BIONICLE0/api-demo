resource "aws_ecr_repository" "todo" {
  name                 = "todo-api"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}