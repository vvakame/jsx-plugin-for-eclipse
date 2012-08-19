import "js/web.jsx";
import "view.jsx";

/*
 multiline comment
*/
class _Main {
  static function main(args: string[]): void {
    var el = dom.id("status");
    new MineSweeperRender().run(el);
  }
}
