package small_class_parser

import scala.util.parsing.combinator._

object RubyClassParser extends RubyClassParser
trait RubyClassParser extends RegexParsers {
  case class Program(classes: List[Class])
  case class Class(id: String,definitions: List[Definition])
  trait Definition
  case class Method(id:String,parameters: List[Variable],sentencias: List[Sentencia]) extends Definition
  trait Variable extends Definition
  trait Valores
  case class Entero(id: String, value: Numero) extends Variable
  case class Numero(value: Int) extends Valores
  trait Sentencia
  case class Define(variable: Variable) extends Sentencia
  case class Add(op1: Numero, op2: Numero) extends Sentencia {
       def apply()= op1.value + op2.value
  }
  case class Multiply(op1: Numero, op2: Numero) extends Sentencia {
       def apply() = op1.value * op2.value
  }
  case object Comment //ble
  

  
  lazy protected val program = comment.* ~ classes.+ ~ comment.* ^^ { case commBefore ~ classes ~ commentsAfter => Program(classes)} 
  lazy protected val comment = "##.*$".r ^^ {_ => Comment}
  lazy protected val classes = ("class" ~> identifier <~ "{") ~ (content.* <~ "}") ^^ {case id ~ content => Class(id,content)}
  
  lazy protected val identifier = "[a-zA-Z0-9_]+".r
  lazy protected val number = "[0-9]+".r ^^ {case numero => Numero(numero.toInt)}
  lazy protected val content = method | instanceVariable
  lazy protected val method = ("def" ~> identifier <~ "(") ~ (repsep(parameter,",") <~ "){") ~ (sentences.* <~ "}")  ^^ {case id ~ parameters ~ sentences => Method(id,parameters,sentences)} 
  lazy protected val instanceVariable = (identifier <~ "=") ~ number ^^ {case id ~ number => Entero(id,number) }
  lazy protected val parameter = identifier <~ ": int" ^^ {case id => Entero(id,???)}
  lazy protected val sentences = suma | mult | definirVariable
  lazy protected val suma = (number <~ "+") ~ number ^^ {case op1 ~ op2 => Add(op1,op2) }
  lazy protected val mult = (number <~ "*") ~ number ^^ {case op1 ~ op2 => Multiply(op1,op2)}
  lazy protected val definirVariable = instanceVariable ^^ {case entero => Define(entero)}
  
  
  def apply(input: String) = parseAll(program, input) match {
    case Success(result, _) => result
    case NoSuccess(msg, _)  => throw ParseException(msg)
  }

}