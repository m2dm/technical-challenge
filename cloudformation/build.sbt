import sbt.Keys._
import sys.process._

name := "cloudformation"
scalaVersion := "2.12.4"

resolvers ++= Seq(Resolver.jcenterRepo)

libraryDependencies ++= Seq(
  "com.monsanto.arch" %% "cloud-formation-template-generator" % "3.9.1"
).map(_.force())

val cloudformationStack = "PaintBatchOptimizerAPI"
val cloudformationTemplate = "lambda.json"
val lambdaSourceCode = "solver"
val lambdaAppPath = "../app"
val lambdaFunctionName = "SolverPython"
val s3Bucket = "aylien-lambda-source-code"

val deploy = taskKey[Unit]("Deploy CloudFormation template.")
deploy := {
  s"""aws cloudformation create-stack --stack-name $cloudformationStack --capabilities CAPABILITY_IAM
     | --template-body file://target/template/$cloudformationTemplate""".stripMargin !
}

val delete_stack = taskKey[Unit]("Delete CloudFormation stack.")
delete_stack := {
  s"aws cloudformation delete-stack --stack-name $cloudformationStack" !
}

val lambda_zip = taskKey[Unit]("Create a zip file containing the lambda function.")
lambda_zip := {
  s"zip -r -j target/$lambdaSourceCode.zip $lambdaAppPath/$lambdaSourceCode" !
}

val lambda_upload = taskKey[Unit]("Upload to S3 the zip file containing the lambda function.")
lambda_upload := {
  s"aws s3 cp target/$lambdaSourceCode.zip s3://$s3Bucket/" !
}
