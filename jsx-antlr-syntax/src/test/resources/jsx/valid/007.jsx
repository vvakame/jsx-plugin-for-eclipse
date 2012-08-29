import "js/web.jsx";
import "view.jsx";

// check for doWhileStatement, whileStatement
class _Main {
  static function main(args: string[]): void {
    do {
      break;
    }while(true)
    
    while(false){
      continue;
    }
    
    HOGE: while(true){
      break HOGE;
      continue HOGE;
    }
  }
}
