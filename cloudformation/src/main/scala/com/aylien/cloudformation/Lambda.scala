package com.aylien.cloudformation

import com.monsanto.arch.cloudformation.model._
import com.monsanto.arch.cloudformation.model.resource._

object Lambda {

  val iamRole = "LambdaExecutionRole"

  def template = Template(
    Description = Some("Lambda Function Template"),
    Resources = Seq(lambdaFunction, lambdaIamRole)
  )

  private def lambdaFunction = `AWS::Lambda::Function`(
    name = "SolverPython",
    Code = Code(
      S3Bucket = Some(StringToken("aylien-lambda-source-code")),
      S3Key = Some(StringToken("solver.zip")),
    ),
    Handler = "lambda_handler.request_handler",
    Runtime = Python36,
    Role = `Fn::GetAtt`(Seq(iamRole, "Arn")),
  )

  private def lambdaIamRole = `AWS::IAM::Role`(
    name = "LambdaExecutionRole",
    AssumeRolePolicyDocument = lambdaAssumeRolePolicyDocument,
    Path = Some(StringToken("/")),
    Policies = Some(lambdaPolicies)
  )

  private def lambdaAssumeRolePolicyDocument = PolicyDocument(
    Statement = Seq(PolicyStatement(
      Effect = "Allow",
      Principal = Some(DefinedPrincipal(
        targets = Map("Service" -> Seq(StringToken("lambda.amazonaws.com")))
      )),
      Action = Seq("sts:AssumeRole"),
    ))
  )

  private def lambdaPolicies = Seq(
    Policy(
      PolicyName = "LambdaWriteCWLogs",
      PolicyDocument = PolicyDocument(
        Statement = Seq(PolicyStatement(
          Effect = "Allow",
          Action = Seq(
            "logs:CreateLogGroup",
            "logs:CreateLogStream",
            "logs:PutLogEvents"
          ),
          Resource = Some(Seq(StringToken("*")))
        ))
      )
    )
  )

}
