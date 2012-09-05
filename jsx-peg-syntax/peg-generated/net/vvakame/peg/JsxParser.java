//=========================================================================
//
//  This file was generated by Mouse 1.5 at 2012-09-05 14:11:13 GMT
//  from grammar
//    '/Users/vvakame/Dropbox/work/jsx-plugin-for-eclipse/jsx-peg-syntax/sr
//    c/main/peg/JSX.peg'.
//
//=========================================================================

package net.vvakame.peg;

import mouse.runtime.Source;

public class JsxParser extends mouse.runtime.ParserMemo
{
  final JsxSemantics sem;
  
  //=======================================================================
  //
  //  Initialization
  //
  //=======================================================================
  //-------------------------------------------------------------------
  //  Constructor
  //-------------------------------------------------------------------
  public JsxParser()
    {
      sem = new JsxSemantics();
      sem.rule = this;
      super.sem = sem;
      caches = cacheList;
    }
  
  //-------------------------------------------------------------------
  //  Run the parser
  //-------------------------------------------------------------------
  public boolean parse(Source src)
    {
      super.init(src);
      sem.init();
      if (programFile()) return true;
      return failure();
    }
  
  //-------------------------------------------------------------------
  //  Get semantics
  //-------------------------------------------------------------------
  public JsxSemantics semantics()
    { return sem; }
  
  //=======================================================================
  //
  //  Parsing procedures
  //
  //=======================================================================
  //=====================================================================
  //  programFile = importStatement* classDefinition* {programFile} ;
  //=====================================================================
  private boolean programFile()
    {
      if (saved(programFile)) return reuse();
      while (importStatement());
      while (classDefinition());
      sem.programFile();
      return accept();
    }
  
  //=====================================================================
  //  importStatement = IMPORT (ident (COMMA ident)* FROM)? string (INTO
  //    ident)? SEMI {importStatement} ;
  //=====================================================================
  private boolean importStatement()
    {
      if (saved(importStatement)) return reuse();
      if (!IMPORT()) return reject();
      importStatement_0();
      if (!string()) return reject();
      importStatement_1();
      if (!SEMI()) return reject();
      sem.importStatement();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  importStatement_0 = ident (COMMA ident)* FROM
  //-------------------------------------------------------------------
  private boolean importStatement_0()
    {
      if (savedInner(importStatement_0)) return reuseInner();
      if (!ident()) return rejectInner();
      while (importStatement_2());
      if (!FROM()) return rejectInner();
      return acceptInner();
    }
  
  //-------------------------------------------------------------------
  //  importStatement_1 = INTO ident
  //-------------------------------------------------------------------
  private boolean importStatement_1()
    {
      if (savedInner(importStatement_1)) return reuseInner();
      if (!INTO()) return rejectInner();
      if (!ident()) return rejectInner();
      return acceptInner();
    }
  
  //-------------------------------------------------------------------
  //  importStatement_2 = COMMA ident
  //-------------------------------------------------------------------
  private boolean importStatement_2()
    {
      if (savedInner(importStatement_2)) return reuseInner();
      if (!COMMA()) return rejectInner();
      if (!ident()) return rejectInner();
      return acceptInner();
    }
  
  //=====================================================================
  //  classDefinition = "class" ;
  //=====================================================================
  private boolean classDefinition()
    {
      if (saved(classDefinition)) return reuse();
      if (!next("class")) return reject();
      return accept();
    }
  
  //=====================================================================
  //  ident = ([a-z] / [A-Z] / "_") ([a-z] / [A-Z] / "_" / [0-9])*
  //    spacing ;
  //=====================================================================
  private boolean ident()
    {
      if (saved(ident)) return reuse();
      if (!nextIn('a','z')
       && !nextIn('A','Z')
       && !next('_')
         ) return reject();
      while (ident_0());
      spacing();
      return accept();
    }
  
  //-------------------------------------------------------------------
  //  ident_0 = [a-z] / [A-Z] / "_" / [0-9]
  //-------------------------------------------------------------------
  private boolean ident_0()
    {
      if (savedInner(ident_0)) return reuseInner();
      if (nextIn('a','z')) return acceptInner();
      if (nextIn('A','Z')) return acceptInner();
      if (next('_')) return acceptInner();
      if (nextIn('0','9')) return acceptInner();
      return rejectInner();
    }
  
  //=====================================================================
  //  string = """ [a-z]+ """ spacing ;
  //=====================================================================
  private boolean string()
    {
      if (saved(string)) return reuse();
      if (!next('"')) return reject();
      if (!nextIn('a','z')) return reject();
      while (nextIn('a','z'));
      if (!next('"')) return reject();
      spacing();
      return accept();
    }
  
  //=====================================================================
  //  spacing = space* ;
  //=====================================================================
  private boolean spacing()
    {
      if (saved(spacing)) return reuse();
      while (space());
      return accept();
    }
  
  //=====================================================================
  //  space = " " / "\t" / "\r" / "\n" ;
  //=====================================================================
  private boolean space()
    {
      if (saved(space)) return reuse();
      if (next(' ')) return accept();
      if (next('\t')) return accept();
      if (next('\r')) return accept();
      if (next('\n')) return accept();
      return reject();
    }
  
  //=====================================================================
  //  IMPORT = "import" spacing ;
  //=====================================================================
  private boolean IMPORT()
    {
      if (saved(IMPORT)) return reuse();
      if (!next("import")) return reject();
      spacing();
      return accept();
    }
  
  //=====================================================================
  //  COMMA = "," spacing ;
  //=====================================================================
  private boolean COMMA()
    {
      if (saved(COMMA)) return reuse();
      if (!next(',')) return reject();
      spacing();
      return accept();
    }
  
  //=====================================================================
  //  FROM = "from" spacing ;
  //=====================================================================
  private boolean FROM()
    {
      if (saved(FROM)) return reuse();
      if (!next("from")) return reject();
      spacing();
      return accept();
    }
  
  //=====================================================================
  //  INTO = "into" spacing ;
  //=====================================================================
  private boolean INTO()
    {
      if (saved(INTO)) return reuse();
      if (!next("into")) return reject();
      spacing();
      return accept();
    }
  
  //=====================================================================
  //  SEMI = ";" spacing ;
  //=====================================================================
  private boolean SEMI()
    {
      if (saved(SEMI)) return reuse();
      if (!next(';')) return reject();
      spacing();
      return accept();
    }
  
  //=======================================================================
  //
  //  Cache objects
  //
  //=======================================================================
  
  final Cache programFile = new Cache("programFile","programFile");
  final Cache importStatement = new Cache("importStatement","importStatement");
  final Cache classDefinition = new Cache("classDefinition","classDefinition");
  final Cache ident = new Cache("ident","ident");
  final Cache string = new Cache("string","string");
  final Cache spacing = new Cache("spacing","spacing");
  final Cache space = new Cache("space","space");
  final Cache IMPORT = new Cache("IMPORT","IMPORT");
  final Cache COMMA = new Cache("COMMA","COMMA");
  final Cache FROM = new Cache("FROM","FROM");
  final Cache INTO = new Cache("INTO","INTO");
  final Cache SEMI = new Cache("SEMI","SEMI");
  
  final Cache importStatement_0 = new Cache("importStatement_0"); // ident (COMMA ident)* FROM
  final Cache importStatement_1 = new Cache("importStatement_1"); // INTO ident
  final Cache importStatement_2 = new Cache("importStatement_2"); // COMMA ident
  final Cache ident_0 = new Cache("ident_0"); // [a-z] / [A-Z] / "_" / [0-9]
  
  //-------------------------------------------------------------------
  //  List of Cache objects
  //-------------------------------------------------------------------
  
  Cache[] cacheList =
  {
    programFile,importStatement,classDefinition,ident,string,spacing,
    space,IMPORT,COMMA,FROM,INTO,SEMI,importStatement_0,
    importStatement_1,importStatement_2,ident_0
  };
}
