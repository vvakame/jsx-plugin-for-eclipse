import "js/web.jsx";
import "view.jsx";

// check for variableDeclarations
class _Main {
  static function main(args: string[]): void {
    var foo;
    var foo: string;
    var foo: string = fizz;
    var foo: string = fizz || buzz;
    var foo: boolean = true ?: false;
    var foo = true ? true : false;
    var foo = bar || bar && bar | bar ^ bar & bar == bar != bar >= bar + bar ? true : false;
    // FIXME variableDeclarations is invalid syntax?
    // var foo, bar;
  }
}
