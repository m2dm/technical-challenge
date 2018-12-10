# CloudFormation - AWS resources for Paint Batch Optimizer API

This application builds the AWS resources required to run the Paint Batch Optimizer API.

This is a Scala application that leverages [cloudformation-template-generator](https://github.com/MonsantoCo/cloudformation-template-generator) as a type-safe DSL for generating CloudFormation templates.

## Usage

### Running
You can run the app to generate the templates with:

```
$ sbt run
```

The generated template files are located in the directory `target/template`.

### Deploying
You can deploy the templates, i.e. create the CloudFormation stack, by executing:

```
$ sbt deploy
```

### Helper tasks
You can create the zip file containing the lambda source code and upload it to an S3 bucket by executing:

```
$ sbt lambda_zip
$ sbt lamda_upload
```
