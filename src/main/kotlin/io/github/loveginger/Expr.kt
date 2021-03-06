/* Generate Automatically */
package io.github.loveginger

abstract class Expr {
  interface Visitor<out R> {

    fun visitBinaryExpr(binary: Binary): R

    fun visitGroupingExpr(grouping: Grouping): R

    fun visitLiteralExpr(literal: Literal): R

    fun visitUnaryExpr(unary: Unary): R

  }

  abstract fun <R> accept(visitor: Visitor<R>): R

  class Binary(val left: Expr, val operator: Token, val right: Expr) : Expr() {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visitBinaryExpr(this)
  }

  class Grouping(val expression: Expr) : Expr() {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visitGroupingExpr(this)
  }

  class Literal(val value: Any?) : Expr() {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visitLiteralExpr(this)
  }

  class Unary(val operator: Token, val right: Expr) : Expr() {
    override fun <R> accept(visitor: Visitor<R>) = visitor.visitUnaryExpr(this)
  }

}
