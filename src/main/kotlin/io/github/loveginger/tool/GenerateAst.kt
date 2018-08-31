package io.github.loveginger.tool

import java.io.PrintWriter


fun main(args: Array<String>) {
  if (args.size != 1) {
    System.err.println("Usage: generate_ast <output directory>")
    System.exit(1)
  }
  val outputDir = args[0]
  defineAst(
    outputDir, "Expr", arrayListOf(
      "Binary: Expr left, Token operator, Expr right",
      "Grouping: Expr expression",
      "Literal: Any value",
      "Unary: Token operator, Expr right"
    )
  )
}

fun defineAst(outputDir: String, baseName: String, types: ArrayList<String>) {
  val path = "$outputDir/$baseName.kt"
  val writer = PrintWriter(path, "UTF-8")

  writer.println("/* Generate Automatically */")
  writer.println("package io.github.loveginger")
  writer.println()
  writer.println("abstract class $baseName {")
  for (type in types) {
    val className = type.split(":")[0].trim()
    val fields = type.split(":")[1].trim()
    defineType(writer, baseName, className, fields)
  }
  writer.println("}")
  writer.close()
}

fun defineType(writer: PrintWriter, baseName: String, className: String, fieldList: String) {
  val fieldStringBuilder = StringBuilder()
  val fields = fieldList.split(",")
  for (field in fields) {
    val type = field.trim().split(" ")[0]
    val name = field.trim().split(" ")[1]
    fieldStringBuilder.append("val $name: $type").append(", ")
  }
  val fieldInConstructor = fieldStringBuilder.toString().removeSuffix(", ")
  writer.println("  class $className($fieldInConstructor) : $baseName()")
  writer.println()
}