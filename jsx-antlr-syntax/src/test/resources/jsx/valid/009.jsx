import "js/web.jsx";
import "view.jsx";

// check for throwStatement, tryStatement
class _Main {
  static function main(args: string[]): void {
    try {
      throw hoge;
    }catch(e: HogeException) {
    }catch(e: FugaException) {
    }finally{
    }
    
    try{
    }finally{
    }
  }
}
