import "js/web.jsx";
import "view.jsx";

// check for newExpr
class _Main {
  static function main(args: string[]): void {
    new Hoge();
    new Hoge[]();
    new Hoge(hoge);
  }
}
