package small_class_parser_test;

import small_class_parser.RubyClassParser

class RubyClassParserTest extends ParserTest with RubyClassParser {
 "Simple class parser" - {
   "should parse classes" - {
     "should parse an empty class" in {
       "class simpleClass {}" should beParsedTo(Program(List(Class("simpleClass",Nil))))(program)
     }
     
     "should parse a class with  an instance variable initialized" in {
       """class simpleClass {
               a=3
               }""" should beParsedTo(Program(List(Class("simpleClass",List(Entero("a",Numero(3)))))))(program)
     }
     
     "should parse a class with an instance variable not initialized" in {
       """class simpleClass {
               a
               }""" should beParsedTo(Program(List(Class("simpleClass",List(Entero("a",Numero(0)))))))(program)
     }
     
     "should parse a class with an empty method, with no arguments" in {
       """
          class simpleClass {
            def metodo() {
            }
          }""" should beParsedTo(Program(List(Class("simpleClass",List(Method("metodo",List(Empty),Nil))))))(program)
     }
     
     "should parse a class with an empty method, with arguments" in {
         """
          class simpleClass {
            def metodo(a: int) {
            }
          }""" should beParsedTo(Program(List(Class("simpleClass",List(Method("metodo",List(Entero("a",Numero(0))),Nil))))))(program)
     }
     
     "should parse a class with a method, no arguments, one operation" in { 
       """class simpleClass {
           def metodo() {
             1*2
             }
             }""" should beParsedTo(Program(List(Class("simpleClass",List(Method("metodo",List(Empty),List(Multiply(Numero(1),Numero(2)))))))))(program)
     }
     
     "should parse a class, with two methods" in {
       """class simpleClass { 
         def metodo1(){}
         def metodo2(){}}""" should beParsedTo(Program(List(Class("simpleClass",List(Method("metodo1",List(Empty),Nil),Method("metodo2",List(Empty),Nil))))))(program)
     }
   }
   
   "should parse methods" - {
     "should parse a method with various definitions" in {
       """def metodo() { 
         1*2
         2+1
         a=3}""" should beParsedTo(Method("metodo",List(Empty),List(Multiply(Numero(1),Numero(2)),Add(Numero(2),Numero(1)),Define(Entero("a",Numero(3))))))(method)
     }
     
     "should parse a method with a long parameter list" in {
       """def metodo(a: int,b: int, c: int){}""" should beParsedTo(Method("metodo",List(Entero("a",Numero(0)),Entero("b",Numero(0)),Entero("c",Numero(0))),Nil))(method)
     }
   }
 }
}