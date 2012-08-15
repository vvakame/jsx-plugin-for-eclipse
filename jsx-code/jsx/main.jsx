import "js/web.jsx";
import "view.jsx";

/*
*/
class _Main {
  static function main(args: string[]): void {
    var el = dom.id("status");1;
    new MineSweeperRender().run(el);
  }
}
