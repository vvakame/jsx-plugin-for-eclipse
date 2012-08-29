import "js/web.jsx";
import "view.jsx";

// check for functionStatement
class _Main {
  static function main(args: string[]): void {
    function test (foo: string): string {
      return "fizzbuzz";
    }
  }
}
