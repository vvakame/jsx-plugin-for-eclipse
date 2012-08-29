import "js/web.jsx";
import "view.jsx";

// check for assertStatement, logStatement, deleteStatement, debuggerStatement
class _Main {
  static function main(args: string[]): void {
    assert 1 == 2;
    log 1;
    log 1, 2;
    
    var a = {
      x: 1
    };
    delete a["x"];
    debugger;
  }
}
