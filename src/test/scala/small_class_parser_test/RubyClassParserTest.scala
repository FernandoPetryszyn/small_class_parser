package small_class_parser_test;

import small_class_parser.RubyClassParser

class RubyClassParserTest extends ParserTest with RubyClassParser {
 "Simple class parser" - {
   "should parse" - {
     "should parse an empty class" in {
       "class simpleClass {}" should beParsedTo(Program(List(Class("simpleClass",Nil))))(program)
     }
     
     "should parse a class with  an instance variable initialized" in {
       """class simpleClass {
               a=3
               }""" should beParsedTo(Program(List(Class("simpleClass",List(Entero("a",Numero(3)))))))(program)
     }
   }
 }
}