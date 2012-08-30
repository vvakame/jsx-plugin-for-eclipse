import "js/web.jsx";
import "view.jsx";

// check for lambdaExpr, lambdaBody
class _Main {
  static function main(args: string[]): void {
    var func0 = (y)-> x + y;
    var func1 = (y : number) : number -> x + y;
    var func2 = (y : number) : number -> { return x + y; };

    (y : number) : number -> x + y;
    (y : number) : number -> { return x + y; };
  }
}
