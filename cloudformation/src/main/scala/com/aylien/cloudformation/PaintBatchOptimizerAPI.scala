package com.aylien.cloudformation

import com.monsanto.arch.cloudformation.model._

/*
 * This app generates the CloudFormation templates to build the AWS resources
 * required to run the Paint Batch Optimizer API
 */
object PaintBatchOptimizerAPI extends App with VPCWriter {

  val templateDir = "template"
  val templateFile = "Lambda.json"
  val templateJson = Lambda.template

  jsonToFile(templateFile, templateDir, templateJson)

}
