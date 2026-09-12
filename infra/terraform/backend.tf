terraform {
  backend "s3" {
    bucket       = "hrtk-tfstate"
    key          = "terraform/terraform.tfstate"
    region       = "ap-northeast-1"
    encrypt      = true
    use_lockfile = true
  }
  #  backend "local" {
  #    path   = "terraform.tfstate"
  #  }
}